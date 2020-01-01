package com.java.algorithm.sort.BubbleSort;

import java.util.Arrays;

// 比较是相邻的两个元素比较，交换也发生在这相邻的两个元素之间,所以是稳定的排序，也就是相同元素，相对位置不会发生变化.

// 以升序为例:这个算法名字由来是因为越大的元素会经由相邻元素的交换慢慢“浮”到数列的末端

// 升序冒泡排序就是把大元素往后调，如果有2个相等的元素，前后顺序是不会变化的，
// 因为相同元素，我们肯定不会无聊的去交换位置。所以冒泡排序是一种稳定排序算法
// (所谓稳定排序意思就是相同元素的前后相对位置不变).以[8, 5, 8, 4, 10]为例,第一个8永远相对于第二个8靠前.

// 时间复杂度为O(N^2)
public class BubbleSort {

    public static void main(String[] args){
        int[] arr = new int[]{2, 1, 7, 9, 28, 23, 17, 100, 203};
        System.out.println("排序前: " + Arrays.toString(arr));
        sort(arr, false);
        System.out.println("升序排序: " + Arrays.toString(arr));
        sort(arr, true);
        System.out.println("将序排序: " + Arrays.toString(arr));
    }

    public static void sort(int[] arr, boolean asc){
        int len = arr.length;
        for(int i=0;i<len;i++){ // 这一层循环是干嘛的? 是控制比较次数的
            for(int j=0;j<len-1-i;j++){
                if(asc){
                    if(arr[j] > arr[j+1]){
                        swap(arr, j, j+1);
                    }
                }else{
                    if(arr[j] < arr[j+1]){//如果前一个小于后一个，把前一个小的元素放到后面
                        swap(arr, j, j+1);
                    }
                }
            }
        }
    }

    // 通过异或来交互2给变量,这样就可以不用临时变量,而且速度也是最快的.
    public static void swap(int[] arr, int i, int j){
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}
