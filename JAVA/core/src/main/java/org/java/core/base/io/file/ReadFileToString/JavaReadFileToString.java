package org.java.core.base.io.file.ReadFileToString;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 读取文件中的字符，然后返回，然后打印
 * 
 * 您可以使用下述任何方法将文件内容读取到java中的字符串。
 *  但是，如果文件大小很大，则不建议使用，因为您可能会遇到内存不足错误。
 *  因为以下方法都是把读到的字符串放到内存中的，也就是StringBuiler中，所以大小受限制.
 */
public class JavaReadFileToString {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";

	/**
	 * This class shows different ways to read complete file contents to String
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		String contents = readUsingScanner(fileName);
		System.out.println("*****Read File to String Using Scanner*****\n" + contents);

		contents = readUsingFiles(fileName);
		System.out.println("*****Read File to String Using Files Class*****\n" + contents);

		contents = readUsingBufferedReader(fileName);
		System.out.println("*****Read File to String Using BufferedReader*****\n" + contents);

		contents = readUsingBufferedReaderCharArray(fileName);
		System.out.println("*****Read File to String Using BufferedReader and char array*****\n" + contents);

		contents = readUsingFileInputStream(fileName);
		System.out.println("*****Read File to String Using FileInputStream*****\n" + contents);

	}

	/**
	 * There is another efficient way to read file to String 
	 * using BufferedReader and char array.
	 * @param fileName
	 * @return
	 */
	private static String readUsingBufferedReaderCharArray(String fileName) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		char[] buffer = new char[10];
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
			while (reader.read(buffer) != -1) {
				stringBuilder.append(new String(buffer));
				buffer = new char[10];
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return stringBuilder.toString();
	}

	/**
	 * 我们可以使用FileInputStream和字节数组将文件读取到String。 
	 * 您应该使用此方法来读取非基于字符的文件，如图像，视频等。
	 * @param fileName
	 * @return
	 */
	private static String readUsingFileInputStream(String fileName) {
		FileInputStream fis = null;
		byte[] buffer = new byte[10];
		StringBuilder sb = new StringBuilder();
		try {
			fis = new FileInputStream(fileName);

			while (fis.read(buffer) != -1) {
				sb.append(new String(buffer));
				buffer = new byte[10];
			}
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sb.toString();
	}

	private static String readUsingBufferedReader(String fileName) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			String ls = System.getProperty("line.separator");
			// 注意只是读取一行, not including any line-termination characters
			// 所以需要手动添加line separator
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last ls
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return stringBuilder.toString();
	}

	private static String readUsingFiles(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String readUsingScanner(String fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
			// we can use Delimiter regex as "\\A", "\\Z" or "\\z"
			String data = scanner.useDelimiter("\\A").next();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (scanner != null)
				scanner.close();
		}

	}

}
