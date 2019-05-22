package org.java.core.base.string;

import java.util.Arrays;

/**
 * convert String to character array or convert String to char 
 * from specific index.
 * <p>
 * String class has three methods related to char:
 * <p>
 * char[] toCharArray():
 * <br><p>
 * char charAt(int index)
 * <br><p>
 * getChars(int srcBegin, int srcEnd, char dst[], int dstBegin):
 * This is a very useful method when you want to 
 * convert part of string to character array.
 */
public class StringToCharJava {
	
	public static void main(String[] args){
		String str = "journaldev";
		
		//String to char array
		char[] charArr = str.toCharArray();
		System.out.println(charArr.length);
		System.out.println("String converted to char array: " + Arrays.toString(charArr));
		
		//char at specific index某个具体的索引
		char c = str.charAt(2);
		System.out.println(c);
		
		//copy String characters to char array
		char[] chars1 = new char[7];
		str.getChars(0, 7, chars1, 0);
		System.out.println(chars1);
	}
	
}
