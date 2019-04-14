package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {

    // 初始值为1，版本号为0
    private static AtomicStampedReference<Integer> a = new AtomicStampedReference<>(1, 0);

    // 计数器
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            // 获取当前版本号
            int stamp = a.getStamp();
            System.out.println("线程: " + Thread.currentThread() + ", 当前 value = " + a.getReference() + ", version: " + stamp);

            // 计数器阻塞，直到计数器为0，才执行
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程: " + Thread.currentThread() + ",CAS操作结果: " + a.compareAndSet(1, 2, stamp, stamp + 1));
        }, "aaaa").start();

        Thread.sleep(1000);

        // 线程2
        new Thread(() -> {
            // 将 value 值改成 2
            a.compareAndSet(1, 2, a.getStamp(), a.getStamp() + 1);
            System.out.println("线程: " + Thread.currentThread() + "value = " + a.getReference() + ", version: " + a.getStamp());

            // 将 value 值又改成 1
            a.compareAndSet(2, 1, a.getStamp(), a.getStamp() + 1);
            System.out.println("线程: " + Thread.currentThread() + "value = " + a.getReference() + " version : " + a.getStamp());

            // 线程计数器
            countDownLatch.countDown();
        }, "bbbb").start();

    }

}
