package com.java.algorithm.sort.HeapSort;

import java.util.Arrays;

/**
* 当不加限定时，堆通常指的就是二叉堆,
二叉堆是一种特殊的堆，是一棵完全二叉树或者是近似完全二叉树.

同时二叉堆还满足堆的特性：父节点的键值总是保持固定的有序关系于
任何一个子节点的键值，且每个节点的左子树和右子树都是一个二叉堆。

当父节点的键值总是大于或等于任何一个子节点的键值时为最大堆。 
当父节点的键值总是小于或等于任何一个子节点的键值时为最小堆。
*/
public class Heap {

    public static void main(String[] args) {
        int[] arr = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        System.out.println("构建heap之前: " + Arrays.toString(arr));
        buildMaxHeap(arr);
        System.out.println("构建heap之后: " + Arrays.toString(arr));
    }

    /**
     * 实际保存数据的数组
     */
    public int[] arr;

    /**
     * 当前堆中有效元素的个数
     */
    public int size;

    private Heap(int[] arr){
        this.arr = arr;
        this.size = arr.length;
    }

    /**
     * 使用给定的数组初始化最大堆
     * @return 返回初始化好的堆对象
     */
    public static Heap buildMaxHeap(int[] arr) {
        Heap heap = new Heap(arr);
        // i的起始值为数组的中间元素
        for (int i = heap.size / 2 - 1; i >= 0; i--) {// 最后一个非叶子节点开始调整
            heap.maxHeapify(i);
        }
        return heap;
    }

    /**
     * 维护最大堆,由于给定结点导致的不稳定性
     * @param currentNodeIndex 给定结点号
     */
    public void maxHeapify(int currentNodeIndex) {
        // 获取左孩子的索引
        int leftChildIndex = leftChild(currentNodeIndex);
        // 获取右孩子的索引
        int rightChildIndex = rightChild(currentNodeIndex);
        // 记录值最大的节点的索引
        int largest;
        if (leftChildIndex < size && arr[leftChildIndex] > arr[currentNodeIndex]) {
            largest = leftChildIndex;
        } else {
            largest = currentNodeIndex;
        }
        if (rightChildIndex < size && arr[rightChildIndex] > arr[largest]) {
            largest = rightChildIndex;
        }
         // 左右子节点的值存在比父节点的值更大
        if (largest != currentNodeIndex) {
            swap(arr, currentNodeIndex, largest);// 交换值
            maxHeapify(largest);// 递归调整
        }
    }

    private void swap(int[] arr,int i,int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 使用给定的数组初始化最小堆
     * @return 返回初始化好的堆对象
     */
    public static Heap buildMinHeap(int[] arr) {
        Heap heap = new Heap(arr);
        for (int i = heap.size / 2 - 1; i >= 0; i--) {
            heap.minHeapify(i);
        }
        return heap;
    }

    /**
     * 维护最小堆由于给定结点导致的不稳定性
     * @param currentNodeIndex 给定结点号
     */
    public void minHeapify(int currentNodeIndex) {
        int leftChildIndex = leftChild(currentNodeIndex);
        int rightChildIndex = rightChild(currentNodeIndex);
        int smallest;
        if (leftChildIndex < size && arr[leftChildIndex] < arr[currentNodeIndex]) {
            smallest = leftChildIndex;
        }
        else {
            smallest = currentNodeIndex;
        }
        if (rightChildIndex < size && arr[rightChildIndex] < arr[smallest]) {
            smallest = rightChildIndex;
        }
        if (smallest != currentNodeIndex) {
            swap(arr, currentNodeIndex, smallest);
            minHeapify(smallest);
        }
    }

    /**
     * 获取给定节点的父节点索引
     */
    private int parent(int i) {
        return (i - 1)/2;
    }

    /**
     * 获取给定节点的左孩子索引
     */
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * 获取给定节点的右孩子索引
     */
    private int rightChild(int i) {
        return 2 * (i + 1);
    }

}
