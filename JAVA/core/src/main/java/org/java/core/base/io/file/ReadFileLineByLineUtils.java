package org.java.core.base.io.file;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * 从一个文本文件中一行一行的读取
 */
public class ReadFileLineByLineUtils {

	public static final String FILE_PATH = "d:" + File.separator;

	/**
	 * FileReader不支持编码并使用系统默认编码，
	 * 因此在java中读取文本文件的效率不高。可能乱码,
	 */
	@Test
	public void UsingBufferedReader() throws IOException {
		BufferedReader reader = null;
		String line;
		reader = new BufferedReader(new FileReader(FILE_PATH + "source.txt"));
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}

	/**
	 * 如果你想逐行读取文件并对它们进行处理，那么BufferedReader是很好的。
	 * 它适用于处理大文件，也支持编码。
	 *
	 * BufferedReader是同步的，因此可以安全地从多个线程完成对BufferedReader
	 * 的读取操作。
	 * BufferedReader的默认缓冲区大小为8KB。
	 *
	 * 使用InputStream来读取非基于字符的文件，如图像，视频等
	 */
	@Test
	public void UsingBufferedReader2() throws IOException {
		File file = new File(FILE_PATH + "source.txt");
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8.name());
		BufferedReader br = new BufferedReader(isr);

		String line;
		while((line = br.readLine()) != null){
			//process the line
			System.out.println(line);
		}
		br.close();
	}

	@Test
	public void UsingRandomAccessFile() throws IOException {
		RandomAccessFile file = new RandomAccessFile(FILE_PATH + "source.txt", "r");
		String str;
		while ((str = file.readLine()) != null) {
			System.out.println(str);
		}
		file.close();
	}

	@Test
	public void UsingScanner() throws IOException {
		Scanner scanner = new Scanner(Paths.get(FILE_PATH + "source.txt"), StandardCharsets.UTF_8.name());
		while (scanner.hasNextLine()) {
			System.out.println(scanner.nextLine());
		}
		scanner.close();
	}

	/**
	 * 使用1.7的Files
	 * 因为是读到内存的，所以大小有限制
	 */
	@Test
	public void UsingFiles() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(FILE_PATH + "source.txt"));
		for (String line : lines) {
			System.out.println(line);
		}
	}

}
