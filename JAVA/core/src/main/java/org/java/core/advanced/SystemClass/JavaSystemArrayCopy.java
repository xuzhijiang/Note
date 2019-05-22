package org.java.core.advanced.SystemClass;

import java.util.Arrays;

public class JavaSystemArrayCopy {
	
	public static void main(String[] args) {
		int[] arr1 = {1,2,3,4,5};
		int[] arr2 = {10,11,12,13,14};
		
		//Java System class provides a native method for 
		//copying data from one array to another. This is a native 
		//implementation and supposed to be faster than other ways to copy array data.
		//Java System类提供了一种将数据从一个数组复制到另一个数组的native method,这是一种native实现，
		//比其他方法更快
		
		//copying first two elements from array1 to array2 starting from index 2 of array2
		System.arraycopy(arr1, 0, arr2, 2, 2);
		
		System.out.println(Arrays.toString(arr2));
	}
}
