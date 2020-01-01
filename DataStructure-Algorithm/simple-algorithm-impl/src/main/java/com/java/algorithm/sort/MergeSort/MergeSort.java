package com.java.algorithm.sort.MergeSort;

import java.util.Arrays;

// 归并排序的递归实现
public class MergeSort {

    public static void main(String[] args) {
        int data[] = { 51, 46, 20, 18, 65, 97, 82, 30, 77, 50 };
        partition(data, 0, data.length - 1);
        System.out.println("排序结果：" + Arrays.toString(data));
    }

    /**
     * 递归的分割(分治)
     */
    public static void partition(int[] data, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            partition(data, left, mid); // 分左边
            partition(data, mid + 1, right); // 分右边
            // 以上就是分的过程,接下来是合并
            merge(data, left, mid, right);
        }
        // 走到这里说明 left = right, 不再满足 left < right ,则表示只有一个元素了,当只有一个元素的时候,
        // 表示这个子数组已经排好序了, left = right也是递归的终止条件
    }

	/**
	 * 把 2个各自 有序的数组 合并成 一个有序数组
	 */
    public static void merge(int[] data, int left, int mid, int right) {
    	// 例如：把两个已排序好的子数组{3, 27, 38, 43} 和 {9, 10, 82}
    	
    	// {3, 27, 38, 43,   9, 10, 82}
    	//  0   1   2  3     4   5   6
        int[] temp = new int[right - left + 1];// 合并后的临时数组，数组大小为: right - left + 1
        int i = left;// 左指针(左边数组的第一个元素)
        int j = mid + 1;// 右指针(右边数组的第一个元素)
        int k = 0;
        
        // 把较小的数先移到新数组中
        while (i <= mid && j <= right) {
            if (data[i] < data[j]) {
                temp[k++] = data[i++];
            } else {
                temp[k++] = data[j++];
            }
        }
        
        // 当某一个数组的元素消耗完时，把未消耗完的数组里面的元素拷贝到临时数组
        
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = data[i++];
            // 可以使用System.arraycopy替代
        }
        
        // 把右边边剩余的数移入数组
        while (j <= right) {
            temp[k++] = data[j++];
        }

        // 把临时数组temp中的排好序的元素覆盖原数组left-right之间的元素.
        System.arraycopy(temp, 0, data, left, temp.length); // 这种复制方式比较快
    }

}