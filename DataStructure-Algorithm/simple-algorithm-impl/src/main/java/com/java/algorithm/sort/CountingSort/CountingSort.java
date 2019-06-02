package com.java.algorithm.sort.CountingSort;

import java.util.Arrays;

/**
 * 计数排序(见CountingSort.png)
 *
 * 假设有n个输入元素，每一个都是在0到k(大于0的某个整数)区间内的一个整数，时间复杂度为O(n)
 *
 * 步骤:
 * 1. 构造辅助数组C，C的长度为k(最大整数)。第一次遍历A数组后，得到[0,k)区间上每个数出现的次数，
 * 将这些次数写入C，得到图(a)的结果。(0出现2次，1出现0次，2出现2次，3出现3次，4出现0次,5出现1次)
 *
 * 2. 然后把C中每个元素变成前面所有元素的累加和，得到图(b)的结果。(C中的每个元素表示当前位置对应的数值对应的A数组中的最大索引位置,每放置一个元素后，索引都要--，防止相同的元素放在同一位置.)
 *
 * 3. 接下来，再次从后向前遍历数组A，根据取出的元素查找C中对应下标的值，再把这个值作为下标找到B中的位置，
 * 即是该元素排序后的位置。例如，图中A的最后一个元素是3，找到C[3]是7，再令B[7]=3即可，然后顺便把C[3]减一，这是防止相同的数被放到同一个位置。
 *
 * 计数排序的思想是，对每一个输入元素，计算小于它的元素个数，如果有10个元素小于它，那么它就应该放在11的位置上，
 * 如果有17个元素小于它，它就应该放在18的位置上。
 *
 * 当有几个元素相同时，这一方案要略做修改，因为不能把它们放在同一个输出位置上。下图展示了实际的运行过程。
 *
 * 参考: https://www.jianshu.com/p/ff1797625d66
 */
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
     * @param A 需要排序的数组
     * @param B 存放排序结果的数组
     * @param k 数组中元素必须在[0,k)区间上
     */
    static void countingSort(int[] A, int[] B, int k) {
        // 辅助数组
        int[] C = new int[k];
        for (int i=0; i<k; i++) {
            C[i] = 0;
        }

        // 对应图a中A到图a中C的过程
        for (int j=0; j<A.length; j++) {
            C[A[j]]++;
        }

        // 对应图a中C到图b中C的过程,也就是把C中每个元素变成前面所有元素出现次数的累加值
        //C[i] now contains the number of elements equal to i.
        for (int i=1; i<k; i++) {
            C[i] += C[i - 1];
        }

        //C[i] now contains the number of elements less than or equal to i.
        for (int j = A.length - 1; j>=0; j--) {
            // count确定A[j]在最终数组B中的位置.
            int index = C[A[j]];
            B[index- 1] = A[j];
            // 防止相同的数被放到同一个位置
            C[A[j]] = C[A[j]] - 1;
        }
    }

    /**
     * 使用计数排序算法对给定的数组排序
     * @param A 需要排序的数组
     * @param k 数组中元素必须在[0,k)区间上
     * @return 把A中的元素排序后放到一个新数组中，并返回
     */
    public static int[] countingSortReturnOrder(int[] A, int k) {
        //辅助数组
        int[] C = new int[k];
        int[] B = new int[A.length];

        for (int i=0; i<A.length; i++) {
            B[i] = i;
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
            int index = C[A[j]];
            B[index - 1] = j;
            C[A[j]] = C[A[j]] - 1;
        }

        return B;
    }

}
