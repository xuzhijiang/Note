package InsertionSort;

import java.io.FileReader;
import java.util.Objects;
import com.google.gson.Gson;

public class InsertionSort {

	/**
     * 插入排序:  
     * 原理：每次将数组最后一个元素作为插入元素，与它前面有序(已排好序）的
     * 数组元素进行比较后，插入正确的位置，排序完成。
     * 升序为例
     */
    static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {// i: 代表即将插入的元素角标,作为每一组比较数据的最后一个元素    
            for (int j = i; j > 0; j--) {   //j:代表数组角标
                if (arr[j] < arr[j - 1]) { //符合条件，插入元素(交换位置）
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }
	
	public static void main(String[] args) {
		ClassLoader classLoader = InsertionSort.class.getClassLoader();
		String filePath = classLoader.getResource("insertion-sort/input.json").getFile();
		try {
			InsertionSortInput[] inputs = new Gson().fromJson(new FileReader(filePath), InsertionSortInput[].class);
			for (InsertionSortInput input : inputs) {
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

class InsertionSortInput {
	int[] array;
	int[] result;
}