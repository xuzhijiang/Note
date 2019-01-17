package org.java.core.base.file.ReadFileLineByLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadFileLineByLineUsingScanner {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";

	public static void main(String[] args) throws IOException {
		try {
			Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
			System.out.println(scanner.hasNextLine());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
