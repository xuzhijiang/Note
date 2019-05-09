package com.java.algorithm.sort.BucketSort;

import com.java.algorithm.sort.InsertionSort.InsertionSort;

import java.util.Arrays;

/**
 * Default Value of Data Types in Java :
 * Data Type	Default Value (for fields)
 * float	0.0f
 * double	0.0d
 * char	    'u0000'
 * String (or any object)	null
 *
 * 0.78 默认是Double类型的，如果要指定它为Float类型的，就要加上f
 * Object o = 0.78; System.out.println(o instanceof Double);    // true
 * Object o = 0.7; System.out.println(o instanceof Float);    // false
 * 注意instanceof后面的类型必须是对象类型，不能为primitive原始类型，否则编译不过去.
 */
public class BucketSort {

    public static void main(String[] args) {
        double[] arr = {0.78, 0.17, 0.39d, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        System.out.println("排序前: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后: " + Arrays.toString(arr));
    }

    static void sort(double[] arr) {
        int arrayLength = arr.length;
        Node[] B = new Node[arrayLength];
        for (int i=0; i<arrayLength; i++) {
            B[i] = new Node();
        }
        for (int i=0; i<arrayLength; i++) {
            Node node = new Node();
            node.value = arr[i];
            int index = (int) (arrayLength * arr[i]);
            node.next = B[index].next;
            B[index].next = node;
        }

        int arrIndex = 0;
        // 遍历每个桶
        for (int i=0; i<arrayLength; i++) {
            Node head = B[i];
            // lenth为每个桶中的元素个数
            int length = 0;
            while (head.next != null) {
                head = head.next;
                length++;
            }
            double[] bucketArr = new double[length];
            head = B[i];
            for (int j=0; j<length; j++) {
                head = head.next;
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
