package org.java.core.base.concurrent.chapter5.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// Cyclic Barrier: 循环障碍(回环栅栏)
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int N = 4;
        //int N = 3;
        CyclicBarrier barrier = new CyclicBarrier(N);
        for(int i=0;i<N;i++){
            new WorkerThread(barrier).start();
        }
    }

    private static class WorkerThread extends Thread {

        private CyclicBarrier cyclicBarrier;

        public WorkerThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据");
            try {
                Thread.sleep(new Random().nextInt(10) * 1000);//以睡眠来模拟写入数据操作
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务");
        }
    }
}
