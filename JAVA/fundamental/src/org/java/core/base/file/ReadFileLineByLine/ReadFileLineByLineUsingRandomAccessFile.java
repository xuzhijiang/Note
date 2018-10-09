package org.java.core.base.file.ReadFileLineByLine;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadFileLineByLineUsingRandomAccessFile {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";

	public static void main(String[] args) {
		try {
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			String str;
			while ((str = file.readLine()) != null) {
				System.out.println(str);
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
