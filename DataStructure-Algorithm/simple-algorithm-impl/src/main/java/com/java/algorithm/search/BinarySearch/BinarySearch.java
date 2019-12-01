package com.java.algorithm.search.BinarySearch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;

/**
 *  二分查找: 时间复杂度为 logN
 *  排序前提是: 所有元素是有序的，本例中是从小到大排序的
 */
public class BinarySearch {

	/**
	 *
	 * @param key
	 * @param arr
	 * @return 返回key对应的索引下标, 如果没有找到,返回 -1
	 */
	public static int binarySearch(int key, int[] arr) {
		// 第一个元素的索引
		int lo = 0;
		// 最后一个元素的索引
		int hi = arr.length - 1;

		// 如果第一个元素的索引小于等于最后一个元素的索引
		while (lo <= hi) {
			// 中间元素位置的索引
			int mid = lo + (hi - lo) / 2;

			if (key  < arr[mid]) {
				hi = mid - 1;
			} else if (key > arr[mid]) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		ClassLoader classLoader = BinarySearch.class.getClassLoader();
		String filename = classLoader.getResource("binary-search/input.json").getFile();
		try {
			BinarySearchInput[] inputs = new Gson().fromJson(new FileReader(filename), BinarySearchInput[].class);
			for (BinarySearchInput input : inputs) {
				int r = binarySearch(input.key, input.array);
				System.out.println("binarySearch(" + input.key + ", " + new Gson().toJson(input.array) + ") => " + input.result);
				System.out.println("算法查找结果: " + r);
				if (r != input.result) {
					new RuntimeException("二分查找算法有问题");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class BinarySearchInput {
	int key;
	int[] array;
	int result;
}