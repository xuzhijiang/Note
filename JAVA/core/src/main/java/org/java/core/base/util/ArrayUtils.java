package org.java.core.base.util;

import java.util.Arrays;

public class ArrayUtils {
	
	public static void main(String[] args) {
		int[] arr1 = {1,2,3,4,5};
		int[] arr2 = {10,11,12,13,14};
		//Java System类提供了一种将数据从一个数组复制到另一个数组的native method,这是一种native实现，
		//比其他方法更快
		System.arraycopy(arr1, 0, arr2, 2, 2);
		System.out.println(Arrays.toString(arr2));
	}
}
