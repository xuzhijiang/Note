package com.java.algorithm.sort.MergeSort;

import java.util.Arrays;

/**
 * 非递归归并排序实现代码:
 * 一开始为n个长度为1子数组，然后经过一轮合并后，变成n/2个长度为2的子数组，然后再排序一轮
 * 变成n/4个长度为4的子数组，一直到最终的1个长度为n的数组为止.
 */
public class WhileMergeSort {

    public static void main(String args[]){
        int[] array = new int[]{1,5,6,8,9,4,3};
        System.out.println("OriginalArray:" + Arrays.toString(array));
        mergeSort(array);
        System.out.println("SortedArray:" + Arrays.toString(array));
    }

    public static void mergeSort(int[] arr){
        int[] b = new int[arr.length];//申请个大小和a相等的数组b
        int s = 1;
        while(s < arr.length){//这里不能为<=
            mergePass(arr, b, s);//将数组a 合并到 数组 b
            s+=s;//s翻倍
            mergePass(b,arr,s);//将数组b 合并到数组a
            s+=s;//s翻倍
            //交替合并
        }
    }

    /**
     * 用于合并排好序的相邻数组段
     * 将 x 合并到 y
     * @param x
     * @param y
     * @param s 合并大小
     */
    private static void mergePass(int [] x,int [] y,int s){
        int i = 0;//从第一个元素开始
        // 合并大小为s的相邻2段子数组

        while(i+2*s <= x.length){/*  i+2*s 要小于等于数组长度，也就是说未合并的元素个数要大于2*s  */
            merge(x, y, i, i+s-1, i+2*s-1);//合并大小为s的相邻2段子数组
            i+=2*s;
        }//此循环执行的次数为： x.length/(2*s) 次       9/(2*1)=4

        if(i+s<x.length){//未合并的元素个数大于 1*s
            merge(x, y, i, i+s-1, x.length-1); //合并最后两个序列
        }else{//未合并的元素个数小于 1*s
            //直接复制到y就行了
            while(i<x.length){
                y[i] = x[i];
                i++;
            }
        }
    }

    private static void merge(int [] c,int [] d,int left,int mid,int right){
        int i = left, j = mid+1, k = left;

        //两边进行比较，哪个更小就把哪个放入数组d
        while((i<=mid)&&(j<=right)){
            if(c[i]<=c[j])
                d[k++] = c[i++];
            else
                d[k++] = c[j++];
        }
        //如果数组左边部分最大的几个元素值 大于右边部分的 ，左边就会剩下还未放入数组d的元素
        //或者理解为 上面while循环中  j>right 跳出while循环了 ，而 i < mid
        while(i<=mid)
            d[k++] = c[i++];
        //i>mid 而 j<right
        while(j<=right)
            d[k++] = c[j++];
    }

}