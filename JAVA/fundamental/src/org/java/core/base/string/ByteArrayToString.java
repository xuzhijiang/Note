package org.java.core.base.string;

import java.nio.charset.StandardCharsets;

public class ByteArrayToString {
	public static void main(String[] args) {
		byte[] byteArray = {'P', 'A', 'N', 'K', 'A', 'J'};
		//Did you noticed that I am providing char while creating the byte array. 
		//It works because of autoboxing and char ‘P’ is being converted to 80 in byte array.
//		因为自动装箱和char'P'在字节数组中被转换为80。
		//That’s why the output is same for both the byte array to string conversion.
		byte[] byteArray1 = { 80, 65, 78, 75, 65, 74 };
		
		String str = new String(byteArray);
		String str2 = new String(byteArray1);
		
		System.out.println(str);
		System.out.println(str2);
		
//		String还有构造函数，我们可以在其中提供字节数组和Charset作为参数
//		The charset to be used to decode the bytes
		str = new String(byteArray, StandardCharsets.UTF_8);
		System.out.println(str);
		
		byte[] byteArray2 = { 80, 65, 78, 75, 65, 74 };
		String str3 = new String(byteArray2, 0, 3, StandardCharsets.UTF_8);
		System.out.println(str3);
	}
}
