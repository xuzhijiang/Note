package com.java.algorithm.other;

import java.util.Arrays;

// 将两个有序数组，合并成一个大的有序数组
public class TwoArrayMergeToOne {

    public static void main(String[] args) {
        // 准备2个待合并升序数组进行合并
        int[] firstArray = {1, 3, 5, 7};
        int[] secondArray = {3, 4, 6, 7};
        System.out.println("待合并升序数组:\n" + (Arrays.toString(firstArray) + "\n" + Arrays.toString(secondArray)));

        int[] resultArray = merge(firstArray, secondArray, true);
        System.out.println("合并后:\n" + Arrays.toString(resultArray));
        System.out.println();

        // 准备2个待合并降序数组进行合并
        firstArray = new int[]{9, 6, 3, 0};
        secondArray = new int[]{10, 5, 3, -1};
        System.out.println("待合并降序数组:\n" + (Arrays.toString(firstArray) + "\n" + Arrays.toString(secondArray)));
        resultArray = merge(firstArray, secondArray, false);
        System.out.println("合并后:\n" + Arrays.toString(resultArray));
    }

    public static int[] merge(int[] firstArray, int[] secondArray, boolean asc) {
        int[] result = new int[firstArray.length + secondArray.length];// 创建一个临时数组,存放合并后的结果

        int i = 0; // firstArray迭代到的下标位置
        int j = 0; // secondArray迭代到的下标位置
        int k = 0; // result迭代到的下标位置
        while (i < firstArray.length && j < secondArray.length) {
            if (asc) { //升序
                if (firstArray[i] < secondArray[j]) {
                    result[k++] = firstArray[i++];
                } else {
                    result[k++] = secondArray[j++];
                }
            } else { //降序
                if (firstArray[i] > secondArray[j]) {
                    result[k++] = firstArray[i++];
                } else {
                    result[k++] = secondArray[j++];
                }
            }
        }

        // firstArray还没有迭代完,所以要把firstArray中的剩余元素放到result中
        while (i < firstArray.length) {
            result[k++] = firstArray[i++];
            // 可以使用System.arraycopy替代
        }

        // secondArray还没有迭代完,所以要把secondArray中的剩余元素放到result中
        while (j < secondArray.length) {
            result[k++] = secondArray[j++];
        }

        return result;
    }

}
