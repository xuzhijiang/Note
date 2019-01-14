package BinarySearch;

import java.io.FileReader;
import com.google.gson.Gson;

/**
 *  二分查找
 *  排序前提是: 所有元素是有序的，本例中是从小到大排序的
 */
public class BinarySearch {
	
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
		try {
			ClassLoader classLoader = BinarySearch.class.getClassLoader();
			// ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			String filePath = classLoader.getResource("binary-search/input.json").getFile();
			System.out.println("filePath: " + filePath);
			BinarySearchInput[] inputs = new Gson().fromJson(new FileReader(filePath), 
					BinarySearchInput[].class);
			for(BinarySearchInput input : inputs) {
				int r = binarySearch(input.key, input.array);
				System.out.println("binarySearch(" + input.key + ", " + new Gson().toJson(input.array) + ") => " + r);
				if (input.result != r) {
					throw new Exception("failed. expected = " + input.result + ", actual = " + r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class BinarySearchInput {
	int key;
	int[] array;
	int result;
}