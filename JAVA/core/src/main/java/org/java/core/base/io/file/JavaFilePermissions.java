package org.java.core.base.io.file;

import java.io.File;

/**
 * Java File类包含检查用户的文件权限的方法，
 * 它们还有一些方法可以为用户和其他所有人设置文件权限。
 * 
 *  我们将首先检查用户的文件权限。 
 *  稍后，我们将更改应用程序用户的文件权限，
 *  然后更改所有其他用户的文件权限。
 * 
 * 请注意，设置文件权限不是通用的，如果您正在使用Java 7，
 * 则应使用Java PosixFilePermission来设置文件权限。
 *
 * 如果这些文件集权限方法无法设置文件权限，则它们也会返回false。 
 * 这可能由于用户权限而发生。 例如，如果我将示例文件的所有者更改为root，
 * 则所有set file permission方法调用都将返回false。
 */
public class JavaFilePermissions {
	public static void main(String[] args) {
		
        File file = new File("C:\\Users\\a\\Desktop\\test\\a.pdf");
        
        //check file permissions for application user
        System.out.println("File is readable? "+file.canRead());
        System.out.println("File is writable? "+file.canWrite());
        System.out.println("File is executable? "+file.canExecute());
        
        //change file permissions for application user only
        System.out.println(file.setReadable(false));
        System.out.println(file.setWritable(false));
        System.out.println(file.setExecutable(false));
        
        //change file permissions for other users also
        System.out.println(file.setReadable(true, false));
        System.out.println(file.setWritable(true, false));
        System.out.println(file.setExecutable(true, true));
        
    }
}
