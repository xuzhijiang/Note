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

    static void recSort(int[] arr,int start,int end) {
        if(start >= end) { return; }

        int partitionIndex = partition(arr, start, end);
        recSort(arr, start, partitionIndex);
        recSort(arr, partitionIndex + 1, end);
    }

    /**
     * 返回划分后，左指针和右指针相遇的下标
     */
    static int partition(int[] arr,int start,int end) {
        // int pivot = getPivot(arr, start, end);
        int pivot = getPivotMedianOfThree(arr, start, end);
        int left_pointer = start - 1;
        int right_pointer = end + 1;
        while (true) {
            // 当left_pointer遇到比基准值大的元素停下来
            while (arr[++left_pointer] > pivot);
            // 当right_pointer遇到比基准值小的元素停下来
            while (arr[--right_pointer] < pivot);
            if (left_pointer >= right_pointer) { break; }
            swap(arr, left_pointer, right_pointer);
        }
        return right_pointer;
    }

    /**
     * 获取基准值
     */
    static int getPivot(int[] arr,int start,int end) {
        return arr[start];
    }

    /**
     * 三项数据取中(median-of-three)
     */
    private static int getPivotMedianOfThree(int[] arr,int start,int end) {
        int median = arr[start];
        // 保证有3个数据项,如果不够3项，直接返回子数组的第一个元素
        if(end - start >= 2){
            int left = arr[start];
            int right = arr[end];
            int middle = arr[(start+end)/2];
            // middle大小居中，所以作为基准值
            if (left < middle && middle < right) {
                median = middle;
                // right大小居中，所以right作为基准值
            } else if (left < right && right < middle) {
                median = right;
                // left大小居中，所以left作为基准值
            } else {
                median = left;
            }
        }
        return median;
    }

    static void swap(int[] arr, int i,int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
