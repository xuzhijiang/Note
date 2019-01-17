package org.java.core.base.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToList {

	public static void main(String[] args) {
		String[] vowels = { "a", "e", "i", "o", "u" };

		List<String> vowelsList = Arrays.asList(vowels);// 返回的是一个ArrayList，可以参照内部实现
		// List是一个interface，不可以实例化一个List实例.
		System.out.println(vowelsList);

		/**
		 * List is backed by array, we can't do structural modification
		 * List是由数组支持的，我们不能做结构修改 Both of the below statements will throw
		 * java.lang.UnsupportedOperationException
		 */
		// vowelsList.remove("e");
		// vowelsList.clear();

		// using for loop to copy elements from array to list, safe for modification of
		// list
		//最好的将array变成List的方法:
		List<String> myList = new ArrayList<String>();
		for (String s : vowels) {
			myList.add(s);
		}
		System.out.println(myList);
		myList.clear();
		System.out.println(myList);
		
		myList.add(null);
		System.out.println(myList);
	}
}
