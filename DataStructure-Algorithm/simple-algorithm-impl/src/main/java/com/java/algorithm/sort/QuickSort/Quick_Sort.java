package com.java.algorithm.sort.QuickSort;

import java.util.Arrays;

public class Quick_Sort {

    public static void main(String[] args) {
        int[] arr = {3,1,9,0,4,7,2,6,5,8};
        System.out.println("排序前数组: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后数组: " + Arrays.toString(arr));
    }

    static void sort(int[] arr) {
        recSort(arr, 0, arr.length - 1);

    }
}
