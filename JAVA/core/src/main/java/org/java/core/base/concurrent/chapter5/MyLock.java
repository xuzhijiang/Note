package org.java.core.base.concurrent.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

// jdk内部的java.util.concurrent.locks.Lock源码解析,java1.5引入

// Lock是一个接口，它定义了锁获取和释放的基本操作

// 看Java类图,可以看到，Lock接口有三个实现类，分别是ReentrantLock(可重入锁)、
// ReadLock(读锁)、WriteLock(写锁)。Segment继承了ReentrantLock。


// 我们以ReentrantLock来讲解Lock接口的使用。
// 以下代码说明了ReentrantLock的使用方式

/**
 * // Lock接口使用的模板方法
 *     Lock lock = new ReentrantLock();
 *     ...
 *     lock.lock();//获取锁
 *     try {
 *         ...
 *     } finally {
 *         lock.unlock();//释放锁
 *     }
 * //在finally中释放锁，目的是在保证获取到锁之后，一定能够被释放。
 * 不要将锁的获取过程写在try块中。因为如果在获取锁(自定义锁的实现)时，发生了异常，异常抛出的同时，也会导致锁的无故释放。
 */
public interface MyLock {
    // 获取锁，调用该方法当前线程会获取锁，当锁获得后，该方法返回
    void lock();

    // 可中断的获取锁，和lock()方法不同之处在于该方法会响应中断，即在锁的获取中可以中断当前线程
    void lockInterruptibly() throws InterruptedException;

    // 尝试非阻塞的获取锁，调用该方法后立即返回。如果能够获取到返回true，否则返回false
    boolean tryLock();

    // 超时获取锁，当前线程在以下三种情况下会被返回:1.当前线程在超时时间内获取了锁,2.当前线程在超时时间内被中断,3. 超时时间结束，返回false
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    // 释放锁
    void unlock();

    // 获取等待通知组件，该组件和当前的锁绑定，当前线程只有获取了锁，才能调用该组件的wait()方法，而调用后，当前线程将释放锁
    Condition newCondition();
}
