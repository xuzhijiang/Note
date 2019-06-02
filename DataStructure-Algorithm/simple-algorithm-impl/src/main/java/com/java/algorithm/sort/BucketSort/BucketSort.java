package com.java.algorithm.sort.BucketSort;

import com.java.algorithm.sort.InsertionSort.InsertionSort;

import java.util.Arrays;

/**
 * 桶排序
 *
 * 桶排序假设输入是由一个随机过程产生，该过程将元素均匀、独立地分布在[0,1)区间上。
 * 我们将[0,1)区间划分为n个相同大小的子区间，称为桶。
 *
 * 然后将输入数据分别放到各个桶中。如果数据分布得很均匀，每个桶中的数据就不会太多，都会维持在常数量级。
 * 我们先对每个桶中的元素排序，然后把所有桶中的元素顺序列出来即可。图BucketSort.png为n=10的一个案例。
 *
 * 创建一个长度也为10的数组，将A中的元素按照大小找到B中合适的位置，插入链表。
 * 之后，分别对B中每个链表中的元素执行插入排序。最后将B中的所有元素依次取出即可。
 *
 * 现在分析桶排序的时间代价。将A中元素放入B用时Θ(n)，B中每个链表执行插入排序的用时，可以证明是O(2 - 1/n)，于是总用时就是Θ(n) + n * O(2 - 1/n) = Θ(n)。
 *
 * 具体证明过程比较难理解,先记住桶排序总用时就是Θ(n)吧.
 *
 * https://www.jianshu.com/p/ff1797625d66
 */
public class BucketSort {

    public static void main(String[] args) {
        double[] arr = {0.78, 0.17, 0.39d, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        System.out.println("排序前: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后: " + Arrays.toString(arr));
    }

    static void sort(double[] arr) {
        int len = arr.length;

        // 创建len个桶的数组
        Node[] B = new Node[len];

        // 用空元素(空元素也就是value为0.0d)填充桶数组
        for (int i=0; i<len; i++) {
            B[i] = new Node();
        }

        // 把数组arr中的元素依次插入桶中，如果有2个元素a和b都插入了某一个桶
        // 先插入的元素会在链表末尾，后插入的在链表头
        for (int i=0; i<len; i++) {
            Node node = new Node();
            node.value = arr[i];
            int index = (int) (len * arr[i]);
            node.next = B[index].next;
            B[index].next = node;
        }


        int arrIndex = 0;
        // 遍历每个桶
        for (int i=0; i<len; i++) {
            // head为空元素，也就是value为0.0d
            Node head = B[i];
            // length为每个桶中的元素个数
            int length = 0;
            while (head.next != null) {
                head = head.next;
                length++;
            }

            // 每个桶中有好多元素，用一个辅助数组bucketArr存放这些元素
            // 用插入排序给这个辅助数组排序
            double[] bucketArr = new double[length];
            head = B[i];
            for (int j=0; j<length; j++) {
                head = head.next;
                // 把桶中的每一个元素放入辅助数组中
                bucketArr[j] = head.value;
            }

            // 对每个桶中的元素进行插入排序
            // 排序完成后，每个桶中的元素是有序的
            InsertionSort.sort(bucketArr,true);
            // 把每个桶中排序好的元素拷贝会原数组.
            for (int j=0; j<bucketArr.length; j++) {
                arr[arrIndex++] = bucketArr[j];
            }
        }
    }

    // 桶排序中使用的链表数据结构
    // 要想从static method中使用此class，就必须要定义为static的，
    // 这样即使不创建BucketSort对象，也可以使用.
    static class Node {
        double value; // 默认值为0.0d
        Node next;
    }

}
