package org.java.core.base.string;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringToByteArray {
	
	public static void main(String[] args) {
		String str = "xzj";
		byte[] byteArr = str.getBytes();//using the platform's default charset
		//getBytes方法将string编码为一个bytes序列(也就是一个字节数组),用的是平台默认的字符集编码
		//print the byte[] elements
		System.out.println("String to byte array: " + Arrays.toString(byteArr));
		
		try {
			byte[] byteArr1 = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//However if we provide Charset name, then we will have to either 
		//catch UnsupportedEncodingException exception or throw it. Better 
		//approach is to use StandardCharsets class introduced in Java 1.7 as shown below.
		
		byte[] byteArr2 = str.getBytes(StandardCharsets.UTF_8);//用给定的字符集
		System.out.println(Arrays.toString(byteArr2));
		
		String ss = "P";//得到某一个char的byte值
		System.out.print(Arrays.toString(ss.getBytes()));
	}
}
