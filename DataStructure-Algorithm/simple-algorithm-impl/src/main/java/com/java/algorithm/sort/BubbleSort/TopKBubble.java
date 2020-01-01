package com.java.algorithm.sort.BubbleSort;

import java.util.Arrays;

// TopK算法: 冒泡算法实现, 不需要将数据全部排序，只用排k轮即可
public class TopKBubble {
    public static int[] topK(int[] arr, int k) {
        // 外层循环0~k论,对于外层的每一轮,内存会循环n~n-k,所以时间复杂度为O(k * N)
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
        return Arrays.copyOfRange(arr, arr.length - k, arr.length);
    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[j] ^ arr[i];
        arr[j] = arr[j] ^ arr[i];
        arr[i] = arr[j] ^ arr[i];
    }

    public static void main(String[] args) {
        int[] array = new int[]{100, 101, 89, 90, 300, 20, 1000};
        int[] result = TopKBubble.topK(array, 5);
        System.out.println(Arrays.toString(result));
    }
}
