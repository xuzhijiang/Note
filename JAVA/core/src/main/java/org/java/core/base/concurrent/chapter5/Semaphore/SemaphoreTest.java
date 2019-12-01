package org.java.core.base.concurrent.chapter5.Semaphore;

import java.util.concurrent.Semaphore;

/**
 * 执行 `acquire` 方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；
 * 除了 `acquire`方法之外，另一个比较常用的与之对应的方法是`tryAcquire`方法，该方法如果获取不到许可就立即返回false
 *
 * 每个 `release` 方法增加一个许可证，这可能会释放一个阻塞的acquire方法。
 *
 * Semaphore经常用于限制获取某种资源的线程数量
 */
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
                semaphore.acquire(); // 获取一个许可,当然一次也可以一次拿取和释放多个许可
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("g工人" + this.num + "释放机器");
                // 释放机器(资源)
                semaphore.release(); // 释放一个许可
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
