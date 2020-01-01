package com.java.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2个线程交替打印1-100
 */
public class MultipleThreadAlternatelyPrint {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                shareResource.APrint();
            }
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                shareResource.BPrint();
            }
        }, "BBB").start();
    }

    private static class ShareResource {
        private int num = 1;

        // flag为A表示线程AAA,为B表示线程BBB
        private String flag = "A";

        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void APrint() {
            lock.lock();
            try {
                while (!"A".equals(flag)) {
                    condition.await();
                }

                System.out.println(Thread.currentThread().getName() + "\t print: " + num++);
                flag = "B";
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void BPrint() {
            lock.lock();
            try {
                while (!"B".equals(flag)) {
                    condition.await();
                }

                System.out.println(Thread.currentThread().getName() + "\t print: " + num++);
                flag = "A";
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
