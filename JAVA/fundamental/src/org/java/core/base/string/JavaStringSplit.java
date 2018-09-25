package org.java.core.base.string;

import java.util.Arrays;

/**
 * split()用于将字符串基于提供的正则表达式分割为数组，
 * 有时候需要分割CSV文件的数据以获得所有的不同的值,这里有2个有用的方法分割:<br><p>
 * 
 * 1. public String[] split(String regex):基于提供的正则表达式分割为数组
 * <p>
 * Notice that the trailing empty strings are not included in 
 * the returned string array	末尾的空字符串不包含在返回的字符串数组内.
 *
 * 2. public String[] split(String regex, int limit): 
 * <p>
 * 请注意，上面的第一种方法实际上通过将limit传递为0来使用第二种方法。
 */
public class JavaStringSplit {
	public static void main(String[] args) {
		String line = "I am a java developer";

		String[] words = line.split(" ");

		String[] twoWords = line.split(" ", 2);
		
		System.out.println("String split with delimiter: " + Arrays.toString(words));
		
		System.out.println("String split into two: " + Arrays.toString(twoWords));

		// split string delimited with special characters
		String wordsWithNumbers = "I|am|a|java|developer";

		String[] numbers = wordsWithNumbers.split("\\|ss");

		System.out.println("String split with special character: " + Arrays.toString(numbers));

		//We can use backslash to use java regular expression 
		//special characters as normal characters, like I have used 
		//pipe (|) special character in above program.
		
		String s = "abcaada";
		System.out.println(Arrays.toString(s.split("a")));
		
		s = "XZJ,New York,USA";
		String[] data = s.split(",", 2);
		System.out.println("Name = "+data[0]); //XZJ
		System.out.println("Address = "+data[1]); //New York,USA
	}
}

//如果正则表达式与输入字符串的任何部分都不匹配，那么结果数组只有一个元素，即该字符串。

//