package com.java.algorithm.sort.MergeSort;

import java.util.Arrays;

// 归并排序递归实现
public class RecMergeSort {

    /**
     *
     * @param data
     * @param left 左索引
     * @param right 右索引
     */
    public static void mergeSort(int[] data, int left, int right) {
        // left > right是递归终止的临界点
        if (left < right) {
            // 如果元素总数为偶数，half为左边数组的最后一个元素的索引
            // 如果元素总数为奇数，half正好为数组正中间元素的索引
            int half = (left + right) / 2;
            mergeSort(data, left, half);
            mergeSort(data, half + 1, right);
            merge(data, left, right);
        }
    }

    public static void merge(int[] array, int startIndex, int endIndex) {
        int mid = (startIndex + endIndex) / 2;
        // 左边数组的起始索引
        int leftStartIndex = startIndex;
        // 右边数组的起始索引
        int rightStartIndex = mid + 1;
        int count = 0;
        // 合并后的临时数组，数组大小为endIndex - startIndex + 1
        int temp[] = new int[endIndex - startIndex + 1];

        // 对左数组和右数组同时迭代，在都没有迭代完的情况下，继续，否则一旦有一个子数组迭代完，就退出while循环
        // 迭代的过程就是排序的过程，把排序后的元素保存在了临时数组里面
        while (leftStartIndex <= mid && rightStartIndex <= endIndex) {
            if (array[leftStartIndex] < array[rightStartIndex]) {
                temp[count++] = array[leftStartIndex++];
            } else {
                temp[count++] = array[rightStartIndex++];
            }
        }

        // 右数组迭代完了，左数组还没有迭代完
        while (leftStartIndex <= mid) {
            temp[count++] = array[leftStartIndex++];
        }

        // 左数组迭代完了，右数组还没有迭代完
        while (rightStartIndex <= endIndex) {
            temp[count++] = array[rightStartIndex++];
        }

        count = 0;

        // 把临时数组temp中的排好序的元素覆盖原数组startIndex-endIndex之间的元素.
        while (startIndex <= endIndex) {
            array[startIndex++] = temp[count++];
        }
    }

    public static void main(String args[]) {
        int[] a = {543, 23, 45, 65, 76, 1, 456, 7, 77, 88, 3, 9};
        System.out.println("数组排序前：" + Arrays.toString(a));
        mergeSort(a, 0, a.length - 1);
        System.out.println("归并排序后：" + Arrays.toString(a));
    }

}
