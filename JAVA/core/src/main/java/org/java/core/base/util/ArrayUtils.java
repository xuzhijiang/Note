package org.java.core.base.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

	public static void main(String[] args) {
		int[] arr1 = {1,2,3,4,5};
		int[] arr2 = {10,11,12,13,14};
		//Java System类提供了一种将数据从一个数组复制到另一个数组的native method,这是一种native实现，
		//比其他方法更快
		System.arraycopy(arr1, 0, arr2, 2, 2);
		System.out.println(Arrays.toString(arr2));
	}

	@Test
	public void testArraysCopy() {
		int[] arr1 = {1,2,3,4,5};
		// 直接返回一个新数组.ArrayList中的 扩容用到了这个方法.
		int[] ints = Arrays.copyOf(arr1, 3);
		System.out.println(Arrays.toString(ints));
	}

	/**
	 * Array转成List
	 */
	@Test
	public void arrayToList() {
		String[] vowels = { "a", "e", "i", "o", "u" };

		// 返回的是一个ArrayList，可以参照内部实现
		List<String> vowelsList = Arrays.asList(vowels);

		/**
		 * List is backed by array, we can't do structural modification
		 * List是由数组支持的，我们不能做结构修改 Both of the below statements will throw
		 * java.lang.UnsupportedOperationException
		 */
		 try {
			 vowelsList.remove("e");
			 vowelsList.clear();
		 } catch (Exception e) {
		 	e.printStackTrace();
		 }

		// 最好的将array变成List的方法:
		List<String> myList = new ArrayList<String>();
		for (String s : vowels) {
			myList.add(s);
		}
		System.out.println(myList);
		myList.clear();
		myList.add(null);
		System.out.println(myList);
	}

}
