package org.java.core.base.concurrent.chapter2.threadCommunication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可以实现指定唤醒线程,而synchronized不行
 *
 * 题目: 多线程之间按顺序调用: AA->BB->CC三个线程依次启动,要求如下:
 * AA线程打印5次,BB打印10次,CC打印15次
 * 紧接着
 * AA线程打印5次,BB打印10次,CC打印15次
 * 循环10轮
 */
public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print5();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print10();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print15();
            }
        }, "CC").start();
    }

    /**
     * 共享资源类
     */
    private static class ShareData {
        // 标记位,表示当前轮到哪个线程执行
        private String flag = "A"; // A: AA  B: BB  C:CC
        private Lock lock = new ReentrantLock();
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();

        public void print5() {
            lock.lock();
            try {
                // 1. 判断
                while (!flag.equals("A")) {
                    c1.await();
                }
                // 2. 干活
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t " + i);
                }
                // 3. 通知
                flag = "B";
                c2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print10() {
            lock.lock();
            try {
                while (!flag.equals("B")) {
                    c2.await();
                }
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t " + i);
                }
                flag = "C";
                c3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print15() {
            lock.lock();
            try {
                while (!flag.equals("C")) {
                    c3.await();
                }
                for (int i = 1; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t " + i);
                }
                flag = "A";
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
