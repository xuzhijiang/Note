package com.java.algorithm.sort.MergeSort;

import com.java.algorithm.sort.MergeSort.MergeSort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

// 这个示例相当于我们自己手写的Fork/Join框架,实际使用的时候直接使用ForkJoin框架即可.

// 并行归并排序与普通的归并排序没有多大区别，只是利用计算机多核的优势，
// 在有可能的情况下，让两个或多个子问题的处理同时进行。这样一来，在效率上，并行归并排序将会比归并排序更胜一筹。
public class ParallelMergeSort {

    public static void main(String[] args) {
        System.out.println("MAX_DEPTH: " + MAX_DEPTH);

        int[] data = new int[500];
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(600);
        }

        sort(data);

        System.out.println(Arrays.toString(data));
    }

    // 深度不能超过这个值
    // 为什么要除以Math.log(2),这个2是怎么得来的? 因为sortParallel()每次都会在当前线程的基础上新开2个线程,这个2就是这么得到的.
    private static final int MAX_DEPTH = (int)(Math.log(Runtime.getRuntime().availableProcessors())/Math.log(2));

    public static void sort(int[] data) {
        // 我们传入的初始深度为1,如果MAX_DEPTH为1,则按照代码逻辑,会开2个线程去进行排序
        // 如果MAX_DEPTH为2,则会开4个线程去进行排序
        sortParallel(data, 0, data.length - 1, 1);
    }

    public static void sortParallel(int[] data,int left,int right,int depth){
        if (left < right) {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            int mid = (right + left) / 2;
            new Thread(new SortRunnable(data, left, mid, depth, countDownLatch)).start();
            new Thread(new SortRunnable(data, mid + 1, right, depth, countDownLatch)).start();

            try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            // 等到上面的子数组排序完成后,才可以合并
            MergeSort.merge(data, left, mid, right);
        }
    }

    private static class SortRunnable implements Runnable {
        private int depth;
        private int[] data;
        private CountDownLatch countDownLatch;
        private int left;
        private int right;

        public SortRunnable(int[] data, int left, int right, int depth, CountDownLatch countDownLatch) {
            this.depth = depth;
            this.data = data;
            this.countDownLatch = countDownLatch;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            if (depth < MAX_DEPTH) {
                sortParallel(data, left, right, depth+1);
            } else {
                MergeSort.partition(data, left, right);
            }
            countDownLatch.countDown();
        }

    }

}
