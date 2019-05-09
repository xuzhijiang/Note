package com.java.algorithm.sort.RadixSort;

import com.java.algorithm.sort.CountingSort.CountingSort;

import java.util.Arrays;

public class RadixSort {

    public static void main(String[] args) {
        // 进制数
        int k = 10;
        // 位数
        int d = 3;
        int[] arr = {329, 457, 839, 355};
        System.out.println("排序前: " + Arrays.toString(arr));
        sort(arr, d, k);
        System.out.println("排序后: " + Arrays.toString(arr));
    }

    /**
     * @param d 数组中元素的位数
     * @param k 数组中元素的进制数，即每一位有多少种取值
     */
    static void sort(int[] arr, int d, int k) {
        int[] digitArr = new int[arr.length];
        for (int i=0; i<d; i++) {
            for (int j=0; j<arr.length; j++) {
                digitArr[j] = arr[j];
                for (int l=0; l<i; l++) {
                    digitArr[j] /= k;
                }
                digitArr[j] %= k;
            }
            int[] order = CountingSort.countingSortReturnOrder(digitArr, k);
            int[] temp = new int[arr.length];
            for (int j=0; j<arr.length; j++) {
                temp[j] = arr[order[j]];
            }
            for (int j=0; j<arr.length; j++) {
                arr[j] = temp[j];
            }

        }
    }
}
