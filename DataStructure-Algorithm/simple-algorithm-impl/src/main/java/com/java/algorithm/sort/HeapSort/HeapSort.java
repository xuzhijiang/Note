package com.java.algorithm.sort.HeapSort;

import com.java.datastructure.heap.Heap;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        System.out.println("排序前: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后: " + Arrays.toString(arr));
    }

    static void sort(int[] arr) {
        // 构造成最大堆
        Heap heap = Heap.buildMaxHeap(arr);
        for (int i = heap.arr.length - 1; i >= 1; i--) {
            // 先交换堆顶元素和无序区最后一个元素
            swap(heap.arr, i, 0);
            heap.size--;
            heap.maxHeapify(0);
        }
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
