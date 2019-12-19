package org.java.core.base.concurrent.chapter5.Semaphore;

import java.util.concurrent.Semaphore;

// 8个学生抢占5台电脑
public class SemaphoreDemo {

    // Semaphore经常用于限制获取某种资源的线程数量
    public static void main(String[] args) {
        // 学生数
        int N = 8;
        // 机器数
        Semaphore semaphore = new Semaphore(5);

        for (int i = 1; i <= N; i++) {
            final int number = i;
            new Thread(() -> {
                try {
                    // 当前线程要获取一台机器(当然也可以一次获取多个)
                    // 如果没有获取到机器,线程就会被挂起
                    semaphore.acquire();
                    System.out.println("工人" + number + "占用一个机器在生产...");
                    Thread.sleep(2000L);
                    System.out.println("工人" + number + "释放机器");
                    // 释放机器(资源)
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
