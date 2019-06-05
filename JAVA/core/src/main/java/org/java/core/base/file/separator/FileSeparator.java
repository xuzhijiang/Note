package org.java.core.base.file.separator;

import java.io.File;

/**
 * 为了使我们的程序平台独立，我们应该始终使用这些分隔符来创建文件路径或读取任何系统变量，如PATH，CLASSPATH。
 *
 */
public class FileSeparator {
	public static void main(String[] args) {
        System.out.println("File.separator = "+File.separator);
        System.out.println("File.separatorChar = "+File.separatorChar);
        System.out.println("File.pathSeparator = "+File.pathSeparator);
        System.out.println("File.pathSeparatorChar = "+File.pathSeparatorChar);
        
        //no platform independence, good for Unix systems
        File fileUnsafe = new File("tmp/abc.txt");

        //platform independent and safe to use across Unix and Windows
        File fileSafe = new File("tmp"+File.separator+"abc.txt");
    }
}
