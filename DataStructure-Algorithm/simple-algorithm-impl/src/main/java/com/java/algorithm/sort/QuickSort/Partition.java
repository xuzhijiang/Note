package com.java.algorithm.sort.QuickSort;

import java.util.Arrays;

public class Partition {

    public static void main(String[] args) {
        int[] arr = {3,1,9,0,4,7,2,6,5,8};
        System.out.println("划分前的数组: " + Arrays.toString(arr));
        int partitionIndex = partition(arr, 0, arr.length-1);
        // 注意Arrays.copyOfRange是左闭右开
        System.out.println("划分后左半部分: "
                + Arrays.toString(Arrays.copyOfRange(arr, 0, partitionIndex+1)));
        System.out.println("划分后右半部分: " + Arrays.toString(Arrays.copyOfRange(arr, partitionIndex+1, arr.length)));
    }

    /**
     * 返回划分后，左指针和右指针相遇的下标
     */
    static int partition(int[] arr,int start,int end) {
        int pivot =getPivot(arr, start, end);
        int left_pointer = start - 1;
        int right_pointer = end + 1;
        while(true) {
            // 当left_pointer遇到比基准值大的元素，停下来,停下来的时候，left_pointer指向的
            // 元素是>=pivot的
            while(arr[++left_pointer] < pivot);
            // 当right_pointer遇到比基准值小的元素，停下来
            // 停下来的时候，right_pointer指向的元素是<=pivot的
            while(arr[--right_pointer] > pivot);
            // 要跳出while循环的时候，如果arr中存在pivot，那么left_pointer等于right_pointer,
            // 如果arr中不存在pivot，那么left_pointer大于right_pointer
            if(left_pointer >= right_pointer) {break;}
            swap(arr, left_pointer, right_pointer);
        }

        return right_pointer;
    }

    /**
     * 获得基准值
     */
    static int getPivot(int[] arr,int start,int end) {
        return 5;
        // 基准值对划分结果的影响:
        // 在本例中，getPivot方法返回一个固定值5，作为基准值，基准值的选择对划分的结果有着指直接的影响，
        // 可能会导致划分后的子数组不平均，例如，现在我们将基准值改为数组中
        // 的第一个元素(取消注释后运行下，会发现子数组不均匀)：
//         return arr[start];
    }

    static void swap(int[] arr,int i,int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
