package com.java.algorithm.sort.SelectionSort;

import java.util.Arrays;

// 第一版插入排序
public class SelectionSortVersionOne {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 1, 7, 9, 28, 23, 17, 100, 203};
        System.out.println("排序之前: " + Arrays.toString(arr));
        sort(arr, true);
        System.out.println("由小到大结果: " + Arrays.toString(arr));
        sort(arr, false);
        System.out.println("由大到小结果: " + Arrays.toString(arr));
    }

    /**
     * @param arr the array to sort
     * @param asc 是否是升序(由小到大)
     */
    private static void sort(int[] arr, boolean asc) {
        for (int i = 0; i < arr.length-1; i++) {//外层循环要遍历n-1轮
            for (int j = i + 1; j < arr.length; j++) {
                if (asc) {//由小到大排列
                    if (arr[i] > arr[j]) {//如果是大在前，小在后，所以要交换成小在前，大在后
                        swap(arr, i, j);//第一轮排序完后的结果是，最小的在0的位置
                    }
                } else {//由大到小
                    if (arr[i] < arr[j]) {
                        swap(arr, i, j);
                    }
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
