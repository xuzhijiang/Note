package org.java.core.base.io.file;

import java.io.File;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class RandomAccessFileExample {
	
	/**
	 * 添加数据
	 * 要打开只读模式的文件，我们必须使用“r”
	 * 对于读写操作，我们必须使用“rw”
	 */
	@Test
	public void appendData() throws IOException {
		String fileName = "d:" + File.separator + "source.txt";
		String data = "append data";
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		System.out.println("length: " + file.length());
		file.seek(file.length());
		// 要获取当前文件指针，可以调用getFilePointer()方法
		System.out.println("current pointer = "+file.getFilePointer());
		file.write(data.getBytes());
		file.close();
	}

	/**
	 * 向文件指定的位置写数据
	 */
	@Test
	public void writeData() throws IOException {
		int seek = 2;
		String data = "new data";
		String fileName = "d:" + File.separator + "source.txt";
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		// 设置文件指针索引，可以调用seek（int i）方法
		file.seek(seek);
		// 在已经存在数据的位置写数据，将覆盖它
		file.write(data.getBytes());
		file.close();
	}

	/**
	 * 从文件读取数据
	 */
	@Test
	public void readCharsFromFile() throws IOException {
		String fileName = "d:" + File.separator + "source.txt";
		int chars = 3;
		int seek = 2;

		RandomAccessFile file = new RandomAccessFile(fileName, "r");
		// 移动到index为seek的位置然后从这个位置开始读取，读取chars个字符到byte array中
		file.seek(seek);
		byte[] bytes = new byte[chars];
		file.read(bytes);
		file.close();
		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	}

}
