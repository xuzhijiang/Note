package org.java.core.base.file.ReadTxtFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * using Files class
 * Files 7是在Java 7中引入的，如果你想加载所有文件内容到内存，就可以使用,
 * 只有在处理小文件并且需要内存加载文件的所有内容时才能使用，其他场景不适用.
 */
public class NioReadFileExample {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	
	public static void main(String[] args) throws IOException {
		Path path = Paths.get(fileName);
		byte[] bytes = Files.readAllBytes(path);
		List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		for(String s : allLines) {
			System.out.println("--------");
			System.out.println(s);
		}
	}
}
