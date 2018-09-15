package net.sunniwell.sort;

import java.util.Arrays;

/*
 * 时间复杂度:  O（n*log2n）
 * 归并排序的时间复杂度与插入排序相比，已经降低了很多，这一点在数组的输入规模较大时将会非常明显，因为log函数的增加速度将远远低于n的增加速度。
 */
public abstract class MergeSort {
    
    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖源数组a
        for (int t = 0; t < temp.length; t++) {
            a[t + low] = temp[t];
        }
    }

    public static void separate(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
        	separate(a, low, mid);
            // 右边
        	separate(a, mid + 1, high);
            // 左右归并
            merge(a, low, mid, high);
            System.out.println(Arrays.toString(a));
        }
    }

    public static void main(String[] args) {
        int a[] = { 51, 46, 20, 18, 65, 97, 82, 30, 77, 50 };
        separate(a, 0, a.length - 1);
        System.out.println("排序结果：" + Arrays.toString(a));
    }
    
}