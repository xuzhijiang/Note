package com.java.algorithm.sort.MergeSort;

import java.util.Arrays;

// 非递归的代码
public class WhileMergeSort {

    public static void main(String args[]){
        int[] array = new int[]{1,5,6,8,9,4,3};
        System.out.println("OriginalArray:" + Arrays.toString(array));
        mergeSort(array);
        System.out.println("SortedArray:" + Arrays.toString(array));
    }

    public static void mergeSort(int[] array){
        int len = 1;
        while(len < array.length){
            for(int i = 0; i < array.length; i += 2*len){
                merge(array, i, len);
            }
             len *= 2;
        }
    }

    public static void merge(int[] array, int startIndex, int endIndex){
        int leftStartIndex = startIndex;
        // 归并的前半部分数组
        int leftHalfLength = startIndex + endIndex;
        int rightStartIndex = startIndex + endIndex;
        // 归并的后半部分数组
        int rightHalfLength = rightStartIndex +endIndex;
        int[] temp = new int[2*endIndex];
        int count = 0;

        while(startIndex < leftHalfLength && rightStartIndex < rightHalfLength && rightStartIndex < array.length){
            if(array[startIndex] <= array[rightStartIndex]){
                temp[count++] = array[startIndex++];
            }
            else{
                temp[count++] = array[rightStartIndex++];
            }
        }

        while(startIndex < leftHalfLength && startIndex < array.length){//注意：这里i也有可能超过数组长度
            temp[count++] = array[startIndex++];
        }

        while(rightStartIndex < rightHalfLength && rightStartIndex < array.length){
            temp[count++] = array[rightStartIndex++];
        }
        count = 0;
        while(leftStartIndex < rightStartIndex && leftStartIndex < array.length){
            array[leftStartIndex++] = temp[count++];
        }
    }
}