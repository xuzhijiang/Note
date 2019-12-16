package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {

    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    // 初始值为100，版本号为0
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("***********************以下是ABA问题的演示*****************************");
        new Thread(() -> {
            Integer current = atomicReference.get();
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + current);
            // 暂停1秒
            try { Thread.sleep(1000L); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicReference.compareAndSet(current, 102);
            System.out.println(Thread.currentThread().getName() + "\t 修改为: " + atomicReference.get());
            atomicReference.compareAndSet(102, 100);
            System.out.println(Thread.currentThread().getName() + "\t 读到最终的值为: " + current);
        }, "T1").start();

        new Thread(() -> {
            Integer current = atomicReference.get();
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + current);
            try { Thread.sleep(2500L); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 修改成功与否: " + atomicReference.compareAndSet(100, 2000));
        }, "T2").start();


        try { Thread.sleep(5000L); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("***********************以下是ABA问题的解决方案*****************************");

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + atomicStampedReference.getReference() + "\t 第1个版本号: " + atomicStampedReference.getStamp());
            // 暂停1秒
            try { Thread.sleep(1000L); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100, 102, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + atomicStampedReference.getReference() + "\t 第2个版本号: " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(102, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + atomicStampedReference.getReference() + "\t 第3个版本号: " + atomicStampedReference.getStamp());
        }, "T3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + atomicStampedReference.getReference() + "\t 第1个版本号: " + atomicStampedReference.getStamp());
            try { Thread.sleep(2500L); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 修改成功与否: " + atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1));
            System.out.println(Thread.currentThread().getName() + "\t 读到当前的值为: " + atomicStampedReference.getReference() + "\t 第2个版本号: " + atomicStampedReference.getStamp());
        }, "T4").start();
    }

}
