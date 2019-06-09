package org.java.core.base.io.file.TempFile;

import java.io.File;
import java.io.IOException;

/**
 * createTempFile（String prefix，String suffix，File directory）：
 * 此方法在directory参数中创建具有给定后缀和前缀的临时文件。
 * 该目录应该已经存在并且应该是一个目录，否则会引发异常。
 * 
 * 文件名使用随机长的数字创建，
 * 因此文件名变为prefix + random_long_no + suffix。
 * 
 * 这样做是为了使应用程序安全，因为无法猜测文件名.
 * 
 * 前缀String应至少为三个字符长。 如果后缀为null，则使用“.tmp”后缀。
 * 
 * createTempFile（String prefix，String suffix）：在操作系统临时目录中创建临时文件的简便方法。
 */
public class JavaTempFile {

	public static void main(String[] args) {
		try {
			File tmpFile = File.createTempFile("data", null);//在系统的临时文件目录下创建临时文件
			File newFile = File.createTempFile("text", ".temp", new File("C:\\Users\\a\\Desktop\\test"));
			System.out.println(tmpFile.getCanonicalPath());
			System.out.println(newFile.getCanonicalPath());
			// write,read data to temporary file like any normal file

			// delete when application terminates
			tmpFile.deleteOnExit();
			newFile.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
