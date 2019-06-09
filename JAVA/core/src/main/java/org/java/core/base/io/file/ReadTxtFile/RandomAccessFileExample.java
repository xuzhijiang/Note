package org.java.core.base.io.file.ReadTxtFile;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileExample {

	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	
	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile(fileName, "r");
		String str;
		
		while ((str = file.readLine()) != null) {
			System.out.println(str);
		}
		file.close();
	}
}
