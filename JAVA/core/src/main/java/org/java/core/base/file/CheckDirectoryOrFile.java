package org.java.core.base.file;

import java.io.File;

/**
 * 检查是文件还是目录
 * isFile(): note that if file doesn’t exist then it returns false.
 * isDirectory(): f path doesn’t exist then it returns false.
 * 在检查是否是文件还是目录之前，我门应该先检查文件或目录是否存在，否则如果不存在就直接返回false了.
 */
public class CheckDirectoryOrFile {
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\a\\Desktop\\test\\a.txt");
        File dir = new File("C:\\Users\\a\\Desktop\\test");
        File notExists = new File("C:\\Users\\a\\Desktop\\test\\b.txt");
        
        System.out.println("a.txt is file?"+file.isFile());
        System.out.println("a.txt is file?"+file.isDirectory());
        
        System.out.println("test is directory?"+dir.isFile());
        System.out.println("test is directory?"+dir.isDirectory());
        
        System.out.println("/Users/pankaj/notafile is file?"+notExists.isFile());
        System.out.println("/Users/pankaj/notafile is directory?"+notExists.isDirectory());
	}
}
