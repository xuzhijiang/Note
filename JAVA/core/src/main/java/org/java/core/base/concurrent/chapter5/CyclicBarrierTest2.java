package org.java.core.base.concurrent.chapter5;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 假若有若干个线程都要进行写数据操作，并且只有所有线程都完成写数据操作之后，
 * 这些线程才能继续做后面的事情，此时就可以利用CyclicBarrier了：
 */
public class CyclicBarrierTest2 {

    public static void main(String[] args) {
        int N = 4;
        // 　如果说想在所有线程写入操作完之后，进行额外的其他操作可以为CyclicBarrier提供Runnable参数：
        CyclicBarrier barrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有线程执行完毕，当前正在执行额外操作..m" + Thread.currentThread().getName());
            }
        });

        for(int i=0;i<N;i++)
            new Writer(barrier).start();

        // 从结果可以看出，当四个线程都到达barrier状态后，
        // 会从四个线程中选择最后一个执行完的线程去执行Runnable。
        //
        // CyclicBarrier实际用途：假设我们要导入一批数据，因为数据量较大有4亿条，
        // 所以我们希望启动四个线程，每个线程负责导入1亿条数据，我们希望所有的数据导入完成之后，
        // 还在数据库中插入一条记录，表示任务执行完成，此时就可以使用CyclicBarrier。
    }

    static class Writer extends Thread {

        private CyclicBarrier barrier;
        public Writer(CyclicBarrier barrier){
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println("所有线程写入完毕，继续处理其他任务...");

        }
    }
}
