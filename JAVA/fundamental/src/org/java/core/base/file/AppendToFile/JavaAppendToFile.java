package org.java.core.base.file.AppendToFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 今天我们将研究如何在java中附加到文件。 Java附加到文件是一种常见的Java IO操作。
 *  例如，每当我们将某些内容打印到服务器日志时，它都会附加到现有文件中。
 * 
 * Java使用FileWriter附加到文件
 * Java使用BufferedWriter将内容附加到现有文件
 * 使用PrintWriter将文本附加到java中的文件
 * 使用FileOutputStream追加到java中的文件
 * 
 * 如果您正在处理文本数据并且写入操作的数量较少，
 * 请使用FileWriter并使用其构造函数，并将append标记值设置为true。 
 * 如果写入操作的数量很大，则应使用BufferedWriter。
 * 
 * 要将二进制或原始流数据附加到现有文件，您应该使用FileOutputStream。
 * 
 * 
 *
 */
public class JavaAppendToFile {
	
	static String fileName = "C:\\Users\\a\\Desktop\\test\\append.txt";

	/**
	 * Java append to file example
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String appendText = "This String will be appended to the file, Byte=0x0A 0xFF";

		appendUsingFileWriter(fileName, appendText);

		appendUsingBufferedWriter(fileName, appendText, 2);

		appendUsingPrintWriter(fileName, appendText);

		appendUsingFileOutputStream(fileName, appendText);
	}

	private static void appendUsingPrintWriter(String filePath, String text) {
		File file = new File(filePath);
		FileWriter fr = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		try {
			// to append to file, you need to initialize FileWriter using below constructor
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br);
			pr.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Use Stream for java append to file when you are dealing with raw data, binary
	 * data
	 * 
	 * @param data
	 */
	private static void appendUsingFileOutputStream(String fileName, String data) {
		OutputStream os = null;
		try {
			// below true flag tells OutputStream to append
			os = new FileOutputStream(new File(fileName), true);
			os.write(data.getBytes(), 0, data.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Use BufferedWriter when number of write operations are more
	 * 
	 * @param filePath
	 * @param text
	 * @param noOfLines
	 */
	private static void appendUsingBufferedWriter(String filePath, String text, int noOfLines) {
		File file = new File(filePath);
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			// to append to file, you need to initialize FileWriter using below constructor
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			for (int i = 0; i < noOfLines; i++) {
				br.newLine();
				// you can use write or append method
				br.write(text);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Use FileWriter when number of write operations are less
	 * 
	 * @param filePath
	 * @param text
	 * @param noOfLines
	 */
	private static void appendUsingFileWriter(String filePath, String text) {
		File file = new File(filePath);
		FileWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new FileWriter(file, true);
			fr.write(text);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
