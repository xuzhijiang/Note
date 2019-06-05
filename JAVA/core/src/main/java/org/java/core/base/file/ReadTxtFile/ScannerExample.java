package org.java.core.base.file.ReadTxtFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * 如果要逐行读取文件或基于某些java正则表达式读取文件，则Scanner是要使用的类。
 * 
 * Scanner中断它的输入基于分隔符模式，默认是空格分隔符中断，
 * 
 * Scanner is not synchronized and hence not thread safe.
 * Scanner不是同步的，因此不是线程安全的.
 */
public class ScannerExample {

	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	
	public static void main(String[] args) throws IOException {
		Path path = Paths.get(fileName);
		Scanner scanner = new Scanner(path, "UTF-8");
		System.out.println("Read text file using Scanner");
		//read line by line
		while(scanner.hasNextLine()){
		    //process each line
		    String line = scanner.nextLine();
		    System.out.println(line);
		}
		scanner.close();
	}
}
