package org.java.core.base.io.file;

import org.junit.Test;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * File 类可以用于表示文件和目录的信息，但是它不表示文件的内容
 *
 * 从 Java7 开始，可以使用 Paths 和 Files 代替 File。
 */
public class FileUtils {

    public static final String FILE_BASE_PATH = "C:" + File.separator + "Users" + File.separator + "xu" + File.separator + "Desktop" + File.separator;

	public static void main(String[] args) throws IOException{
	    // 删除项目根目录下的bin文件夹
        DeleteFolderRecursively(new File("bin"));
    }

    /**
     * 使用 Canonical路径 最适合避免因相对路径而导致的任何问题。
     */
    @Test
    public void  getFilePath() throws IOException{
        String fileAbsolute = FILE_BASE_PATH + "source";
        String fileReleate = "pom.xml";

        File f1 = new File(fileAbsolute);

        File f2 = new File(fileReleate);

        System.out.println("Absolute Path: " + f1.getAbsolutePath());
        System.out.println("Canonical Path: " + f1.getCanonicalPath());// 推荐使用
        System.out.println("Path: " + f1.getPath());
        System.out.println("-------------");

        System.out.println("Absolute Path: " + f2.getAbsolutePath());
        System.out.println("Canonical Path: " + f2.getCanonicalPath());
        System.out.println("Path: " + f2.getPath());
    }

    /**
     * 创建新文件
     *
     * createNewFile()只尝试去创建文件，
     * 任何相对目录或者绝对目录都应该已经存在，如果不存在，就会抛异常.
     *
     * 当我们创建文件对象给它传入file name的时候，它可以是绝对路径，或者我们可以只提供文件名.
     */
    @Test
    public void createNewFile() throws IOException {
        // 使用绝对路径创建文件
        String absoluteFilePath = FILE_BASE_PATH + "new_file";
        File file = new File(absoluteFilePath);
        if(file.createNewFile()) {
            System.out.println(absoluteFilePath + " File.Created");
        }else {
            System.out.println("File " + absoluteFilePath + " already exists");
        }

        // 只提供文件名创建文件(只提供文件名会把文件创建到项目根目录下)
        file = new File("file.txt");
        if(file.createNewFile()){
            System.out.println("file.txt File Created in Project root directory");
        }else {
            System.out.println("File file.txt already exists in project root directory");
        }

        // 如果要创建新文件并同时向其中写入一些数据，可以使用FileOutputStream写入。
        String fileData = "file content";
        FileOutputStream fos = new FileOutputStream("name1.txt");
        fos.write(fileData.getBytes());
        fos.flush();
        fos.close();

        // 我们可以使用Java NIO的Files类来创建新文件，并且向其中写入数据。
        // 这个是好的一个选择，因为我们不必担心关闭IO资源。
        fileData = "nio file content";
        Files.write(Paths.get("name2.txt"), fileData.getBytes());
    }

    /**
     * delete（）方法可用于删除java中的文件或空目录/文件夹。
     * 如果文件被删除，Java文件删除方法返回true，如果文件不存在则返回false
     */
    @Test
    public void deleteFile() {
        // 绝对路径的删除
        String absoluteFilePath = FILE_BASE_PATH + "dest";
        File file = new File(absoluteFilePath);
        if (file.delete()) {
            System.out.println("dest has been deleted");
        } else {
            System.out.println("dest doesn't exists");
        }

        // 相对路径文件的删除
        file = new File("tmp/file.txt");
        if(file.delete()){
            System.out.println("tmp/file.txt File deleted from Project root directory");
        }else {
            System.out.println("File tmp/file.txt doesn't exists in project root directory");
        }

        // 删除空目录(项目根目录下的),空目录可以删除成功
        file = new File("tmp1");
        if (file.delete()) {
            System.out.println("tmp directory deleted from Project root directory");
        } else {
            System.out.println("tmp directory doesn't exists or not empty in project root directory");
        }

        // 尝试去删除有文件的目录,会删除失败.
        file = new File("bin");
        if (file.delete()) {
            System.out.println("bin directory deleted from Project root directory");
        } else {
            System.out.println("bin删除失败");
        }
    }

    /**
     * 以递归方式删除java中的非空文件夹
     *
     * 如果您尝试删除目录，它会检查它是否为空。 如果directory为空，则会被删除，
     * 否则delete（）方法不会执行任何操作并返回false。
     * 所以在这种情况下，我们必须递归删除所有文件，然后删除空目录
     */
    public static void DeleteFolderRecursively(File file) throws IOException{
        if (!file.exists()) return;

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // call recursively
                DeleteFolderRecursively(f);
            }
        }

        // 删除文件或空目录
        file.delete();
    }

    /**
     * @since 1.7
     */
    @Test
    public void deleteDirectoryUsingFiles() throws IOException {
        Path directory =  Paths.get(FILE_BASE_PATH);

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.delete(file); // this will work because it's always a File
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir); // this will work because Files in the directory are already deleted
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    public void getFileLength() {
        String dirName = FILE_BASE_PATH;
        File dir = new File(dirName);
        // 注意这个文件夹的大小不包含里面的内容的大小，只是单纯的文件夹的大小.
        System.out.println(dirName + "文件夹大小为: " + dir.length() + " bytes");

        String fileName = FILE_BASE_PATH + "source";
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            System.out.println(file + "文件大小为: " + file.length() + " bytes");
        }

        // 通过1.4之后的FileChannel的size()获取
        Path filePath = Paths.get(fileName);
        FileChannel fileChannel;
        try {
            fileChannel = FileChannel.open(filePath);
            long fileSize = fileChannel.size();
            System.out.println(file + "大小为" + fileSize + " bytes");
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过文件名过滤想要的文件，可以根据指定的扩展名过滤文件
     */
    @Test
    public void fileFilter() {
        String dir = FILE_BASE_PATH;
        String ext = ".txt";

        File file = new File(dir);
        if(!file.exists()){
            System.out.println(dir + " Directory doesn't exist");
            return;
        }

        File[] listFiles = file.listFiles(new MyFileNameFilter(ext));

        // 因为FileNameFilter是一个功能性接口(FunctionalInterface),
        // 所以我们可以使用lambda表达式缩减上面的代码
        // File[] listFiles = file.listFiles((dirName, fileName) -> fileName.toLowerCase().endsWith(ext));

        if(listFiles.length == 0){
            System.out.println(dir + " doesn't have any file with extension " + ext);
        }else{
            for(File f : listFiles){
                System.out.println("File: " + dir + f.getName());
            }
        }
    }

    /**
     * 我们应该始终检查renameTo返回值以确保重命名文件成功，因为它依赖于平台，
     * 并且如果重命名失败则不会抛出IO异常。
     */
    private static void renameFile() {
        String fileName = FILE_BASE_PATH + "source";
        String newFileName = FILE_BASE_PATH + "source_new_name";

        // absolute path rename file
        File file = new File(fileName);
        File newFile = new File(newFileName);
        if(file.renameTo(newFile)){
            System.out.println("File rename success");;
        }else{
            System.out.println("File rename failed");
        }
    }

    /**
     * 在文件末尾追加内容
     */
    @Test
    public void appendToFile() throws IOException{
        appendUsingFileOutputStream();
    }

    @Test
    public void copyFileTest() throws IOException{
        // testInputStreamOutputStreamCopy();

        //testFileChannelCopy();

        // copyFileUsingJava7Files();
    }

    /**
     * 可以使用Files类copy()方法在java中复制文件
     *
     * @since 1.7
     */
    private static void copyFileUsingJava7Files() throws IOException {
        String source = FILE_BASE_PATH + "source";
        String dest = FILE_BASE_PATH + "dest";

        Files.copy(new File(source).toPath(), new File(dest).toPath());
    }

    /**
     * 通过InputStream和OutputStream拷贝文件
     *
     * 可以使用Java中的Reader或Stream读取文件。 Reader最适合用于文本数据,
     * 但要对于二进制数据，应该使用Stream
     */
    @Test
    public void testInputStreamOutputStreamCopy() throws IOException{
        InputStream is;
        OutputStream os;
        String source = FILE_BASE_PATH + "source";
        String dest = FILE_BASE_PATH + "dest";

        is = new FileInputStream(source);
        os = new FileOutputStream(dest);

        byte[] byteArray = new byte[1024];
        int cnt;

        // is.read(byteArray, 0, buffer.length),
        // read() 最多读取 buffer.length 个字节
        // 返回的是实际读取的个数
        // 返回 -1 的时候表示读到 eof，即文件尾
        while ((cnt = is.read(byteArray)) > 0) {
            // 把byteArray从0到cnt位置的数据写到os中
            os.write(byteArray, 0, cnt);
        }
        // flush OutputStream to write any buffered data to file
        os.flush();

        is.close();
        os.close();
    }

    /**
     * java.nio.channels.FileChannel
     *
     * Java NIO类在Java 1.4中引入，FileChannel可用于在java中复制文件。
     *
     * 这种复制文件的方式应该比使用Streamscopy文件更快。
     */
    private static void testFileChannelCopy() throws IOException{
        String source = FILE_BASE_PATH + "source";
        String dest = FILE_BASE_PATH + "dest";
        FileChannel sourceFileChannel;
        FileChannel destFileChannel;

        sourceFileChannel = new FileInputStream(source).getChannel();
        destFileChannel = new FileOutputStream(dest).getChannel();

        destFileChannel.transferFrom(sourceFileChannel, 0, sourceFileChannel.size());

        sourceFileChannel.close();
        destFileChannel.close();
    }

    /**
     *  java.io.File.lastModified()返回最新的修改日期
     *
     *  如果文件不存在，lastModified()返回0L
     */
    @Test
    public void lastModifiedTest() {
        File file = new File("bin");
        long timestamp = file.lastModified();
        System.out.println("文件的最后修改日期为: " + new Date(timestamp));
    }

    @Test
    public void appendUsingFileOutputStream() throws IOException{
        String data = "append data";
        String fileName = FILE_BASE_PATH + "source";
        OutputStream os;
        // true: 追加文件的标记
        os = new FileOutputStream(fileName, true);
        os.write(data.getBytes(), 0, data.length());
        os.close();
    }

    /**
     * 使用Stream下载内容
     */
    @Test
    public void downloadUsingStream() throws IOException{
        String outputFile = FILE_BASE_PATH + "baidu.txt";
        String urlStr = "http://www.baidu.com";
        URL url = new URL(urlStr);

        // 使用URL.openStream方法来创建输入流(InputStream)
        BufferedInputStream bis = new BufferedInputStream(url.openStream());

        // 然后我们使用文件输出流(FileOutputStream)从输入流中读取数据并写入文件。
        FileOutputStream fis = new FileOutputStream(outputFile);

        byte[] byteArray = new byte[1024];
        int count;
        while((count = bis.read(byteArray)) != -1)
        {
            fis.write(byteArray, 0, count);
        }
        fis.flush();
        fis.close();
        bis.close();
    }

    /**
     * 使用nio下载
     */
    @Test
    public void downloadUsingNIO() throws IOException {
        String outputFile = FILE_BASE_PATH + "baidu-nio.txt";
        String urlStr = "http://www.baidu.com";
        URL url = new URL(urlStr);

        // 从URL打开的流数据创建字节通道。 然后使用文件输出流将其写入文件。
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(outputFile);

        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    /**
     * 把file压缩成gzipFile
     *
     * 我们可以压缩GZIP格式的单个文件，但是我们不能使用
     * GZIP来压缩和存档目录像ZIP文件这样。
     */
    @Test
    public void compressGzipFile() {
        String file = FILE_BASE_PATH + "source";
        String gzipFile = FILE_BASE_PATH + "source.gz";
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 把gzip文件解压
     * 在解压缩GZIP文件时，如果它不是GZIP格式，则会抛出以下异常
     */
    @Test
    public void decompressGzipFile() {
        String gzipFile = FILE_BASE_PATH + "source.gz";
        String newFile = FILE_BASE_PATH + "source_decompress";

        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIsFileOrDir() {
        File file = new File(FILE_BASE_PATH + "source");

        System.out.println(FILE_BASE_PATH + "source" + "is a file?" + file.isFile());
        System.out.println(FILE_BASE_PATH + "source" + "is a dir?" + file.isDirectory());
    }

    @Test
    public void testExist() {
        File file = new File(FILE_BASE_PATH);
        File notExist = new File(FILE_BASE_PATH + "nofile.txt");

        try {
            System.out.println(file.getCanonicalPath() + " exists? " + file.exists());
            System.out.println(notExist.getCanonicalPath() + " exists? "+notExist.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeFilePermission() {
        File file = new File("d:" + File.separator + "source.txt");

        // 判断当前应用用户是否有权限
        System.out.println("File is readable? "+file.canRead());
        System.out.println("File is writable? "+file.canWrite());
        System.out.println("File is executable? "+file.canExecute());

        // 只为当前应用的用户改变文件权限
        System.out.println(file.setReadable(false));
        System.out.println(file.setWritable(false));
        System.out.println(file.setExecutable(false));

        // 也为其他用户改变文件权限
        System.out.println(file.setReadable(true, false));
        System.out.println(file.setWritable(true, false));
        System.out.println(file.setExecutable(true, true));
    }

    @Test
    public void createTmpFile() throws IOException {
        // 在临时文件目录下创建临时文件
        File tmpFile = File.createTempFile("data", null);
        File specificPathTmpFile = File.createTempFile("text", ".tmp", new File("d:"));

        System.out.println(tmpFile.getCanonicalPath());
        System.out.println(specificPathTmpFile.getCanonicalPath());

        // 当应用退出的时候删除.
        tmpFile.deleteOnExit();
        specificPathTmpFile.deleteOnExit();
    }

    /**
     * 通过文件后缀名来过滤文件的文件名过滤器
     */
    private static class MyFileNameFilter implements FilenameFilter {

        private String ext;

        public MyFileNameFilter(String ext){
            this.ext = ext.toLowerCase();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }

    }
}
