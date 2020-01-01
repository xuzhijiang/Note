package org.java.core.base.concurrent.ForkJoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// 有一些排序算法如归并排序、快速排序,都使用了分而治之的思想.可以使用多线程来加速排序的
// fork/join框架充分运用了CPU多核计算能力
public class ForkJoinMergeSortDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        forkJoinTest();

        System.out.println();
        System.out.println();

        singleThreadTest();
    }

    private static void singleThreadTest() {
        System.out.println("*********单线程 归并排序测试开始***********");
        int[] data = new int[200000000];
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(100000);
        }

        /**
         * 单线程测试
         */
        long start = System.currentTimeMillis();
        new MergeSortForkJoinTask(data, 0, data.length - 1).partition(data, 0, data.length - 1);
        System.out.println("单线程用时: " + (System.currentTimeMillis() - start));
        System.out.println("*********单线程 归并排序测试结束***********");
    }

    private static void forkJoinTest() {
        System.out.println("*********Fork/Join 归并排序测试开始***********");
        int[] data = new int[200000000];
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(100000);
        }


        /**
         * 使用fork/join框架测试
         */
        long start = System.currentTimeMillis();
        MergeSortForkJoinTask forkJoinTask = new MergeSortForkJoinTask(data, 0, data.length - 1);
        ForkJoinPool threadPool = new ForkJoinPool(2);
        threadPool.invoke(forkJoinTask);
        if (forkJoinTask.isCompletedNormally()) {
            System.out.println("用时: " + (System.currentTimeMillis() - start) + " 毫秒");
        }
        System.out.println("*********Fork/Join 归并排序测试结束***********");
    }



    private static class MergeSortForkJoinTask extends RecursiveAction {
        private final int[] array;
        private int left;
        private int right;

        public MergeSortForkJoinTask(int[] data, int left, int right) {
            this.array = data;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            //1. 当排序项分解成少于10000时直接执行归并排序算法,不再继续分解
            if (right - left < 10000) {
                partition(array, left, right);
            } else {
                //2. 当排序项大于10000时，将数组分成两部分(由框架根据条件自动递归分解，直到项数少于10000为止）
                int mid = (left + right) / 2;
                MergeSortForkJoinTask t1 = new MergeSortForkJoinTask(array, left, mid);
                MergeSortForkJoinTask t2 = new MergeSortForkJoinTask(array, mid + 1, right);
                invokeAll(t1, t2);
                //3. 递归合并被分解的两组数字
                merge(array, left, mid, right);
            }
        }

        // 分解
        public void partition(int[] data, int left, int right) {
            if (left < right) {
                int mid = (left + right) / 2;
                partition(data, left, mid);
                partition(data, mid + 1, right);
                merge(data, left, mid, right);
            }
        }

         // 合并
        private void merge(int[] data, int left, int mid, int right) {
            int[] tmp = new int[right - left + 1];
            int i = left;
            int j = mid + 1;
            int k = 0;

            while (i <= mid && j <= right) {
                if (data[i] < data[j]) {
                    tmp[k++] = data[i++];
                } else {
                    tmp[k++] = data[j++];
                }
            }

            while (i <= mid) {
                tmp[k++] = data[i++];
            }

            while (j <= right) {
                tmp[k++] = data[j++];
            }

            System.arraycopy(tmp, 0, data, left, tmp.length);
        }
    }
}
