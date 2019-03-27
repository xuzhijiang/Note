package SelectionSort;

import java.io.FileReader;
import java.util.Objects;
import com.google.gson.Gson;

public class SelectionSort {

	public static void sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			// swap the com.java.algorithm.array[i] with min(com.java.algorithm.array[i+1:])
			int min = i;
			for (int j = i + 1; j < array.length; j++) {
				// for循环遍历完成后，找到了剩余元素中最小元素的索引
				if (array[min] > array[j]) {
					min = j;
				}
			}
			// swap a[i] and a[min]:
			// 找到最小元素索引后，然后将最小元素和已经排序的子列中的最后一个元素交换位置.
			int tmp = array[i];
			array[i] = array[min];
			array[min] = tmp;
		}
	}

	public static void main(String[] args) {
		try {
			ClassLoader classLoader = SelectionSort.class.getClassLoader();
			String filePath = classLoader.getResource("selection-sort/input.json").getFile();
			System.out.println("filePath: " + filePath);
			SelectionSortInput[] inputs = new Gson().fromJson(new FileReader(filePath),
					SelectionSortInput[].class);
			for (SelectionSortInput input : inputs) {
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

class SelectionSortInput {
	int[] array;
	int[] result;
}
