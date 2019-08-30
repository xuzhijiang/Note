package org.java.core.base.concurrent.chapter5.Semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    public static void main(String[] args) {
        int N = 8;//人工数
        Semaphore semaphore = new Semaphore(5);//机器数

        for(int i=0;i<N;i++)
            new WorkerThread(i, semaphore).start();
    }

    private static class WorkerThread extends Thread {
        private int num;
        private Semaphore semaphore;

        public WorkerThread(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // 当前线程要获取一台机器(资源获取)
                semaphore.acquire();
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("g工人" + this.num + "释放机器");
                // 释放机器(资源)
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
