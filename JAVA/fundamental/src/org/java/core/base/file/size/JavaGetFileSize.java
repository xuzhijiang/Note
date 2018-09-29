package org.java.core.base.file.size;

import java.io.File;

/**
 * 如果此文件表示目录，则返回值未指定。 因此，在调用此方法以获取java中的文件大小之前，请确保文件存在并且它不是目录。
 */
public class JavaGetFileSize {
	
	static final String FILE_NAME = "C:\\Users\\a\\Desktop\\test\\a.txt";
	
	public static void main(String[] args) {
		File file = new File(FILE_NAME);
		if(!file.exists() || !file.isFile()) return;
		
		System.out.println(getFileSizeBytes(file));
		System.out.println(getFileSizeKiloBytes(file));
		System.out.println(getFileSizeMegaBytes(file));
	}
	
	private static String getFileSizeMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024) + " mb";
	}
	
	private static String getFileSizeKiloBytes(File file) {
		return (double)file.length() / 1024 + " kb";
	}
	
	//Java File length() method returns the file size in bytes
	private static String getFileSizeBytes(File file) {
		return file.length() + " bytes";
	}
}
