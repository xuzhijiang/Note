package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyForkTask myTask = new MyForkTask(1, 100);

        // ForkJoin 使用 ForkJoinPool 来启动，它是一个特殊的线程池，线程数量取决于 CPU 核数
        ForkJoinPool threadPool = new ForkJoinPool();

        threadPool.submit(myTask);

        System.out.println(myTask.get());

        threadPool.shutdown();
    }

    private static class MyForkTask extends RecursiveTask<Integer> {
        // 停止继续拆分的阈值.
        private static Integer THRESHOLD = 10;
        // 当前这个任务的开始下标
        private int begin;
        // 当前这个任务的结束下标
        private int end;
        // 当前这个任务的计算结果
        private int result;

        public MyForkTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if ((end - begin) <= THRESHOLD) {
                for (int i=begin;i<=end;i++) {
                    result += i;
                }
            } else {
                int middle = (begin + end) / 2;
                MyForkTask task1 = new MyForkTask(begin, middle);
                MyForkTask task2 = new MyForkTask(middle+1, end);
                task1.fork();
                task2.fork();
                result = task1.join() + task2.join();
            }
            return result;
        }

    }
}
