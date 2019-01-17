package org.java.core.base.string;

public class StringPalindromeTest {
	public static void main(String[] args) {
		System.out.println(checkPalindrome("abcba"));
		System.out.println(checkPalindrome("XYyx"));
		System.out.println(checkPalindrome("871232178"));
		System.out.println(checkPalindrome("CCCCC"));
	}

	private static boolean checkPalindrome(String str) {
		if (str == null)
			return false;
		if (str.length() <= 1) {
			return true;
		}
		String first = str.substring(0, 1);
		String last = str.substring(str.length() - 1);
		if (!first.equals(last))
			return false;
		else
			return checkPalindrome(str.substring(1, str.length() - 1));
	}
}