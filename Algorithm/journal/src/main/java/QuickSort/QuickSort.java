package QuickSort;

import java.io.FileReader;
import java.util.Objects;
import com.google.gson.Gson;

public class QuickSort {

	static int partition(int arr[], int low, int high) {
		// 最后的元素作为哨兵
		int pivot = arr[high];
		// index of smaller element(更小元素的索引)
		int i = (low - 1); // i是记录着小于等于哨兵元素的索引
		for (int j = low; j < high; j++) {
			// If current element is smaller than or equal to pivot
			// 如果当前的元素小于等于哨兵
			if (arr[j] <= pivot) {
				i++;

				// swap arr[i] and arr[j]
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		// 将哨兵放到所有元素的中间位置，使哨兵左边的元素都小于等于哨兵，哨兵右边的
		// 元素大于等于哨兵
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;
	}

	/*
	 * The main function that implements QuickSort() arr[] --> Array to be sorted,
	 * low --> Starting index, high --> Ending index
	 */
	static void sort(int arr[], int low, int high) {
		if (low < high) {
			/*
			 * pi is partitioning index, arr[pi] is now at right place
			 */
			int pi = partition(arr, low, high);

			// Recursively sort elements before
			// partition and after partition
			sort(arr, low, pi - 1);
			sort(arr, pi + 1, high);
		}
	}

	static void sort(int[] array) {
		sort(array, 0, array.length - 1);
	}

	public static void main(String[] args) {
		try {
			ClassLoader classLoader = QuickSort.class.getClassLoader();
			String filePath = classLoader.getResource("quick-sort/input.json").getFile();
			System.out.println("filePath: " + filePath);
			QuickSortInput[] inputs = new Gson().fromJson(new FileReader(filePath), QuickSortInput[].class);
			for (QuickSortInput input : inputs) {
				int[] array = new int[input.array.length];
				System.arraycopy(input.array, 0, array, 0, array.length);
				System.out.println("original => " + new Gson().toJson(array));
				sort(array);
				System.out.println("sorted => " + new Gson().toJson(array));
				if (!Objects.deepEquals(array, input.result)) {
					throw new Exception("failed. expected = " + new Gson().toJson(input.result) + ", actual = "
							+ new Gson().toJson(array));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class QuickSortInput {
	int[] array;
	int[] result;
}