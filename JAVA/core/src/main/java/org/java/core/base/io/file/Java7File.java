package org.java.core.base.io.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * Java7中文件IO发生了很大的变化，专门引入了很多新的类来取代原来的基于java.io.File的文件IO操作方式：
 */
public class Java7File {

    /**
     * Paths和Path
     */
    @Test
    public void testPath() {
        // 创建Path实例
        Path path = Paths.get("pom.xml");
        // 下面这种创建方式和上面等效：
        Path path2 = FileSystems.getDefault().getPath("pom.xml");

        // File和Path之间的转换
        File file = new File("pom.xml");
        // jdk1.7提供的方法
        Path path3 = file.toPath();

        // 获取Path的相关信息
        System.out.println("文件名: " + path.getFileName());
        System.out.println("父路径: " + path.getParent());
        System.out.println("根路径: " + path.getRoot());
        System.out.println("是否是绝对路径: " + path.isAbsolute());
        System.out.println("是否是以给定的路径D:开头: " + path.startsWith("D:"));
        System.out.println("该路径的字符串形式: " + path.toString());
    }

    /**
     * 拥抱Files类
     *
     * java.nio.file.Files类是和java.nio.file.Path相结合使用的
     */
    @Test
    public void testFiles() throws IOException {
        Path path = Paths.get("pom.xml");
        // 注意Files.exists()的的第二个参数。它是一个数组，
        // 这个参数直接影响到Files.exists()如何确定一个路径是否存在。
        // 在本例中，这个数组内包含了LinkOptions.NOFOLLOW_LINKS，表示检测时不包含符号链接文件。
        System.out.println("文件是否存在: " + Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS}));

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        // Files.createDirectory()方法只是创建目录，如果它的上级目录不存在就会报错。
        // Files.createDirectories()会首先创建所有不存在的父目录来创建目录

        // Files.delete()方法 可以删除一个文件或目录：

        // 通过Files.copy()方法可以吧一个文件从一个地址复制到另一个位置
        // Files.copy(sourcePath, destPath);

        // copy操作还可可以强制覆盖已经存在的目标文件，只需要将上面的copy()方法改为如下格式：
        // Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);

        // 获取文件属性
        System.out.println(Files.getLastModifiedTime(path));
        System.out.println(Files.size(path));
        // 是否是符号链接文件
        System.out.println(Files.isSymbolicLink(path));
        System.out.println(Files.isDirectory(path));
        System.out.println(Files.readAttributes(path, "*"));
        System.out.println(Files.readAllLines(path, StandardCharsets.UTF_8));

        // 遍历一个文件夹
        Path dir = Paths.get("D:" + File.separator);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (Exception e) {

        }

        // 上面是遍历单个目录，它不会遍历整个目录。
        // 遍历整个目录需要使用：Files.walkFileTree().Files.walkFileTree()方法具有递归遍历目录的功能。
        Path startingDir = Paths.get("d:" + File.separator + "java" + File.separator + "Note");
        List<Path> result = new LinkedList<>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println("size: " + result.size());
    }

    private class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List<Path> result;

        public FindJavaVisitor(List<Path> result) {
            this.result = result;
        }

        /**
         * FileVistor则会在每次遍历中被调用。
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(".java")) {
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
