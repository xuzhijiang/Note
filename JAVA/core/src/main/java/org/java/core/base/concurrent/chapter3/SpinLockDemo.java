package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, current)) {
            System.out.println(Thread.currentThread().getName() + "\t 通过CAS尝试获取锁");
        }
        System.out.println(Thread.currentThread().getName() + "\t 获取到锁");
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t 解锁");
        atomicReference.compareAndSet(current, null);
    }

    public static void main(String[] args){
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.lock();
            // 占有锁5秒
            try { TimeUnit.SECONDS.sleep(5L); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.unlock();
        }, "AA").start();

        new Thread(() -> {
            spinLockDemo.lock();
            // 占有锁2秒
            try { TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.unlock();
        }, "BB").start();
    }
}
