package org.java.core.base.string;

public class StringCompareToExample {
	public static void main(String[] args) {
		String str = "ABC";
		
		//用传递进来的参数和String对象以字典顺序比较,如果传递进来的参数靠前，就返回负值
		//如果传递进来的参数靠后，返回正integer，如果值相等，就返回0
		//The comparison is based on the Unicode value of each character in the strings. 
		//You should check String class source code to check how this method works.
		//comparision是基于每个字符的Unicode值比较的
		System.out.println(str.compareTo("DEF"));
		System.out.println(str.compareToIgnoreCase("abc"));
		
		//Above negative output is because “ABC” is lexicographically 
		//less than the “DEF”. Output is -3 be
		//cause it compares the character values one by one. You can also confirm this 
		//with below test program.
		char a = 'A';
		char d = 'D';
		System.out.println(a-d);
		
//		So when “ABC” is compared to “DEF”, the character at first index is compared.
//		Since they are different 
//		and ‘A’ comes before ‘D’ lexicographically, it returns a negative 
//		integer with the difference between them, hence output is -3.

//		So if you compare “AABC” with “ADBC”, then also you will get 
//		the same output as -3. That’s all for java String compareTo 
//		example. Note that this method is not same as String equals method.
	}
}
