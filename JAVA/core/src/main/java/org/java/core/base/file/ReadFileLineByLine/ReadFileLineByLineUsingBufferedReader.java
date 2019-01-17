package org.java.core.base.file.ReadFileLineByLine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileLineByLineUsingBufferedReader {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";

	public static void main(String[] args) {
		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
