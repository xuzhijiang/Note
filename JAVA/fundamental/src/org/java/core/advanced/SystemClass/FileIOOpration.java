package org.java.core.advanced.SystemClass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileIOOpration {
	public static void main(String[] args) {
//		System class contains three fields ¨C in, out and err. 
//		They are used to read data from InputStream and to write data to OutputStream.

//		System class provide methods to set different types of 
//		InputStream and OutputStream to be used for logging purposes.

//		For example, we can set FileOutputStream to out and err fields 
//		so that console output is written to file.
		
		
		//Notice the use of Java 7 try with resources feature in above try-block.
		try (FileInputStream fis = new FileInputStream("C:\\Users\\a\\Desktop\\a\\a.txt");
				FileOutputStream fos = new FileOutputStream("C:\\Users\\a\\Desktop\\a\\server.log");) {

			// set input stream
			System.setIn(fis);
			char c = (char) System.in.read();
			System.out.print(c); // prints the first character from input stream

			// set output stream
			System.setOut(new PrintStream(fos));
			System.out.write("Hi xzj\n".getBytes());

			// set error stream
			System.setErr(new PrintStream(fos));
			System.err.write("Exception message\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
