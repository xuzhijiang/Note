package org.java.core.base.array;

/**
 * An array in java is a container that can hold fixed number of values of same type.
 * The values can be of primitive type like int, short, byte or 
 * it can be an object like String, Integer etc
 */
public class JavaArrayInitialization {
	public static void main(String[] args) {
		// initialize primitive one dimensional array
		int[] arrInt = new int[5];
		for(int i=0;i<arrInt.length;i++) {
			System.out.println(arrInt[i]);
		}
		
		// initialized Object one dimensional array
		String[] strArr; //declaration
		
		strArr = new String[4]; //initialization
		for(int i=0;i<strArr.length;i++) {
			System.out.println(strArr[i]);
		}
		
		//When we invoke length of an array, it returns the number of rows 
		//in array or the value of left most dimension
		

		//initialize multidimensional array
		int[][] twoArrInt = new int[4][5];

		//multidimensional array initialization with only leftmost dimension
		int[][] twoIntArr = new int[2][];
		twoIntArr[0] = new int[2];
		twoIntArr[1] = new int[3]; //complete initialization is required before we use the array
		
		//array initialization using shortcut syntax
		int[] arrI = {1,2,3};
		int[][] arrI2 = {{1,2}, {1,2,3}};
		
		//If you notice above, the two dimensional array 
		//arrI2 is not a symmetric matrix(非对称矩阵). It’s because a 
		//multidimensional array in java is actually an array of array. 
		
		//Invalid ways to initialize an array in java初始化数组的无效方式
		
		//invalid because dimension is not provided
		//int[] a = new int[];
		
		//invalid because leftmost dimension value is not provided
		//int[][] aa = new int[][5];
		
		//Here are some other variations of declaring arrays in java 
		//but they are strongly discouraged to avoid confusion.
		int[] twoArrInt1[] = new int[4][5];

		int twoIntArr2[][] = new int[5][];
	}
}
