package com.java.algorithm.sort.PigeonholeSort;

import java.util.Arrays;

public class PigeonholeSortCaseTwo {

    public static void main(String[] args) {
        // 注意这个数组中存在一个负数
        int[] arr = {11, 13, 56, -23, 63, 98 ,87};
        System.out.println("排序前: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后: " + Arrays.toString(arr));
    }

    static void sort(int[] arr) {
        // 第一步：确定数组中最大和最小元素
        int maxNum = arr[0];
        int minNum = arr[0];
        for (int i : arr) {
            if (i > maxNum) {
                maxNum = i;
            }
            if (i < minNum) {
                minNum = i;
            }
        }

        // 第二步: 创建辅助数组,大小为max-min+1,注意这里是Integer，所以每个元素的默认值为null
        int bucketArrayLength = maxNum - minNum + 1;
        Integer[] bucketArray = new Integer[bucketArrayLength];

        // 第三步: 将数组中的元素分配到bucket数组中
        for (int i=0;i<arr.length;i++){
            // 比起原来元素全都是>=0的情况，现在相当于把bucketArray腾出了前(-minNum)个位置，
            // 用来存放负数.正数相当于都右移了(-minNum)个位置.
            // 所以，minNum存放在bucketArray中的(minNum-minNum),也就是位置0.
            bucketArray[arr[i]-minNum] = arr[i];
        }

        //第四步：迭代辅助数组，将不是null的元素依次拷贝到待排序数组中
        int index=-1;
        for (int i = 0; i < bucketArray.length; i++) {
            if(bucketArray[i]!=null){
                arr[++index]=bucketArray[i];
            }
        }
    }
}
