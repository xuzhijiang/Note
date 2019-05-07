package com.java.algorithm.sort.PigeonholeSort;

import java.util.Arrays;

public class PigeonholeSortCaseOne {

    public static void main(String[] args) {
        int[] arr = {11, 13, 56, 23, 63, 98 ,87};
        System.out.println("数组排序前: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("数组排序后: " + Arrays.toString(arr));
    }

    /**
     * 第一种情况:所有元素都>0
     */
    static void sort(int[] arr) {
        // 第一步：确定数组中最大的元素
        int max = arr[0];
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }

        // 第二步: 创建辅助数组,大小为max+1
        // (思考下数组大小为什么是max+1，因为max是存放在index为max位置，所以大小为max+1),
        // 注意这里是Integer,所以每个元素默认值为null
        Integer[] bucketArray = new Integer[max + 1];

        // 第三步: 将数组中的元素分配到bucket数组中
        for (int i =0;i < arr.length;i++){
            bucketArray[arr[i]] = arr[i];
        }

        // 第四步: 迭代辅助数组，将不是null的元素依次拷贝到待排序数组中
        int index = -1;
        for (int i=0;i<bucketArray.length;i++) {
            if (bucketArray[i] != null) {
               arr[++index] = bucketArray[i];
            }
        }

    }
}
