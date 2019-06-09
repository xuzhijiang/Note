package org.java.core.base.io.file.ReadFileLineByLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFileLineByLineUsingFiles {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";

	public static void main(String[] args) {
		try {
			List<String> allLines = Files.readAllLines(Paths.get(fileName));
			for (String line : allLines) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
