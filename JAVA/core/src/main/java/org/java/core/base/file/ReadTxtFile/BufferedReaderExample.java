package org.java.core.base.file.ReadTxtFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 如果你想逐行读取文件并对它们进行处理，那么BufferedReader是很好的。 它适用于处理大文件，也支持编码。
 * 
 * BufferedReader是同步的，因此可以安全地从多个线程完成对BufferedReader
 * 的读取操作。 BufferedReader的默认缓冲区大小为8KB。
 */
public class BufferedReaderExample {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	
	public static void main(String[] args) throws IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		String line;
		while((line = br.readLine()) != null){
		     //process the line
		     System.out.println(line);
		}
		br.close();
	}
}
