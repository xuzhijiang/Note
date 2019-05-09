package com.java.algorithm.sort.CountingSort;

import java.util.Arrays;

public class CountingSort {

    public static void main(String[] args) {
        //使用计数排序的数组的元素大小必须位于[0,k)区间内
        int k = 17;

        int[] arr = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        int[] resultArray = new int[arr.length];
        System.out.println("排序前: " + Arrays.toString(arr));
        countingSort(arr, resultArray, k);
        System.out.println("排序后: " + Arrays.toString(resultArray));
    }

    /**
     * 使用计数排序算法对给定的数组排序
     * @param sourceArray 需要排序的数组
     * @param resultArray 存放排序结果的数组
     * @param k 数组中元素必须在[0,k)区间上
     */
    static void countingSort(int[] sourceArray, int[] resultArray, int k) {
        // 辅助数组
        int[] C = new int[k];
        for (int i=0; i<k; i++) {
            C[i] = 0;
        }

        for (int j=0; j<sourceArray.length; j++) {
            // 对应图a中A到图a中C的过程
            C[sourceArray[j]]++;
        }

        //C[i] now contains the number of elements equal to i.
        for (int i=1; i<k; i++) {
            // 对应图a中C到图b中C的过程,也就是把C中每个元素变成前面所有元素出现次数的累加值
            C[i] += C[i - 1];
        }

        //C[i] now contains the number of elements less than or equal to i.
        for (int j = sourceArray.length - 1; j>=0; j--) {
            // count确定sourceArray[j]在最终数组resultArray中的位置.
            int index = C[sourceArray[j]];
            resultArray[index- 1] = sourceArray[j];
            // 防止相同的数被放到同一个位置
            C[sourceArray[j]] = C[sourceArray[j]] - 1;
        }
    }

    /**
     * 使用计数排序算法对给定的数组排序
     * @param A 需要排序的数组
     * @param B 存放排序结果的数组
     * @param k 数组中元素必须在[0,k)区间上
     * @return 指示排序后元素原序号的数组
     */
    public static int[] countingSortReturnOrder(int[] A, int k) {
        //辅助数组
        int[] C = new int[k];
        int[] O = new int[A.length];
        for (int i=0; i<A.length; i++) {
            O[i] = i;
        }
        for (int i=0; i<k; i++) {
            C[i] = 0;
        }
        for (int j=0; j<A.length; j++) {
            C[A[j]]++;
        }
        //C[i] now contains the number of elements equal to i.
        for (int i=1; i<k; i++) {
            C[i] += C[i - 1];
        }
        //C[i] now contains the number of elements less than or equal to i.
        for (int j = A.length - 1; j>=0; j--) {
//			B[C[A[j]] - 1] = A[j];
            O[C[A[j]] - 1] = j;
            C[A[j]] = C[A[j]] - 1;
        }
        return O;
    }

}
