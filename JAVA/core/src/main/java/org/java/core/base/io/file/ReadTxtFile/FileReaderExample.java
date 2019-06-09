package org.java.core.base.io.file.ReadTxtFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *  FileReader不支持编码并使用系统默认编码，因此在java中读取文本文件的效率不高。
 *  可能乱码,
 * @author a
 *
 */
public class FileReaderExample {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	
	public static void main(String[] args) throws IOException {
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = null;//方法内部使用之前一定要初始化，否则不能使用，编译器会报错，默认值也不是null.
		while((line = br.readLine()) != null){
		    //process the line
		    System.out.println(line);
		}
	}
}
