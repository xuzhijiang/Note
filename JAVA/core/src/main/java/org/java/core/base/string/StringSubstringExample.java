package org.java.core.base.string;

/**
 * Java String substring method is used to get the part of String as a new String
 * This method always return a new String and the original 
 * string value is not affected because String is immutable in java.
 * <p><br>
 * Java String substring method is overloaded and has two variants.
 * <p><br>
 * substring(int beginIndex): The substring begins with the character at 
 * the specified index and extends to the end of this string.
 * <p><br>
 * substring(int beginIndex, int endIndex):The substring begins at 
 * the specified beginIndex and extends to the character at index 
 * endIndex ¨C 1. Thus the length of the substring is (endIndex ¨C beginIndex).
 * <p><br>
 * beginIndex is inclusive and endIndex is exclusive in both substring methods.
 */
public class StringSubstringExample {
	public static void main(String[] args){
		String str = "www.journaldev.com";
		System.out.println("Last 4 char String: " + str.substring(str.length() - 4));
		System.out.println("First 4 char String: " + str.substring(0, 4));
		System.out.println("website name: " + str.substring(4, 14));
	}
}
