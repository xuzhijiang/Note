package org.java.core.base.string;

/**
 * So we can clearly see that System arraycopy is 
 * the method being used in both String to char array 
 * and char array to String operations.
 */
public class CharArrayToString {
	
	public static void main(String[] args) {
		char[] charArray = {'X', 'Z', 'J'};
		String str = new String(charArray);
		System.out.println(str);
	}
}
