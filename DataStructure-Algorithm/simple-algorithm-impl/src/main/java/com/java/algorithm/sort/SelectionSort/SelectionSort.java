package com.java.algorithm.sort.SelectionSort;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.Objects;

// 效率更高的插入排序,减少了交换次数
public class SelectionSort {

    public static void sort(int[] arr, boolean asc) {
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (asc) {
                    // 内层for循环遍历完成后，找到了剩余元素中最小元素的索引
                    if (arr[index] > arr[j]) {
                        index = j;
                    }
                } else {
                    if (arr[index] < arr[j]) {
                        index = j;
                    }
                }
            }
            // swap a[i] and a[index]:
            // 内层for循环遍历完成后，找到最大/最小元素索引后，然后将最大/小元素和已经排序的子列中的最后一个元素交换位置.
            if (index != i) {
                swap(arr, index, i);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
                sort(array, true);
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
