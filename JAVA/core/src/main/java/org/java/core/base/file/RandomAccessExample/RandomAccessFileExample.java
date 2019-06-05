package org.java.core.base.file.RandomAccessExample;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * Java RandomAccessFile提供了读取和写入文件数据的工具。 
 * RandomAccessFile使用文件作为存储在文件系统中的大字节数组，
 * 以及用于移动文件指针位置的游标。
 * 
 * 
 * RandomAccessFile类是Java IO的一部分。 在java中创建RandomAccessFile实例时，
 * 我们需要提供打开文件的模式。 例如，要打开只读模式的文件，我们必须使用“r”，
 * 对于读写操作，我们必须使用“rw”。
 * 
 * 使用文件指针，我们可以在任何位置从随机访问文件中读取或写入数据。 
 * 要获取当前文件指针，可以调用getFilePointer（）方法
 * ,设置文件指针索引，可以调用seek（int i）方法。
 * 
 * 如果我们在已经存在数据的任何索引处写入数据，它将覆盖它。
 * 
 * 我们可以使用java中的RandomAccessFile从文件中读取字节数组。
 * 
 * 由于RandomAccessFile将文件视为字节数组，因此写操作可以覆盖数据，
 * 也可以附加到文件。 这一切都取决于文件指针的位置。 如果指针移动超出文件长
 * 度然后调用写操作，则会在文件中写入垃圾数据。 因此，在使用写入操作时应该注意这一点。
 * 
 * 我们所需要的只是确保文件指针位于文件的末尾以附加到文件 
 */
public class RandomAccessFileExample {
	
	public static final String fileName = "C:\\Users\\a\\Desktop\\test\\os.txt";

	public static void main(String[] args) {
		try {
			System.out.println(new String(readCharsFromFile(fileName, 1, 5), StandardCharsets.UTF_8));

			writeData(fileName, "Data", 5);
			
			appendData(fileName, " xzj");
			//now file content is "ABCDEDatapankaj"
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void appendData(String filePath, String data) throws IOException {
		RandomAccessFile raFile = new RandomAccessFile(filePath, "rw");
		System.out.println("length: " + raFile.length());
		raFile.seek(raFile.length());
		System.out.println("current pointer = "+raFile.getFilePointer());
		raFile.write(data.getBytes());
		raFile.close();
		
	}

	private static void writeData(String filePath, String data, int seek) throws IOException {
		RandomAccessFile file = new RandomAccessFile(filePath, "rw");
		file.seek(seek);
		file.write(data.getBytes());
		file.close();
	}

	private static byte[] readCharsFromFile(String filePath, int seek, int chars) throws IOException {
		RandomAccessFile file = new RandomAccessFile(filePath, "r");
		// 移动到index为seek的位置然后从这个位置开始读取，读取chars个字符到byte array中
		file.seek(seek);
		byte[] bytes = new byte[chars];
		file.read(bytes);
		file.close();
		return bytes;
	}

}
