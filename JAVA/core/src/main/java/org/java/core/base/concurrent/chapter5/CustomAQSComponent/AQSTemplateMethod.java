package org.java.core.base.concurrent.chapter5.CustomAQSComponent;

import java.util.Collection;

public abstract class AQSTemplateMethod {

    /**
     * AQS提供的模板方法分为3类
     * 1. 独占式 获取与释放锁的相关方法
     * 2. 共享式 获取与释放锁的相关方法
     * 3. 查询同步队列中的线程等待情况
     */

    //====================================独占式 获取与释放锁的相关方法======================
    // 独占式获取锁(获取许可)，如果获取成功,直接返回，否则，将进入队列等待，该方法将会调用重写的tryAcquire(int arg)方法
    abstract void acquire(int arg);

    // 与acquire(int arg)相同，但是该方法响应中断，如果当前线程被中断，则该方法会抛出InterruptedException
    abstract void acquireInterruptibly(int arg) throws InterruptedException;

    // 在acquireInterruptibly(int arg)基础上增加了超时限制，如果当前线程在超时时间内没有获取到同步状态，那么将会返回false，如果获取到了就返回true
    abstract boolean tryAcquireNanos(int arg, long nanosTimeout) throws InterruptedException;

    // 独占式释放同步状态，该方法会在释放同步状态后，将同步队列中第一个节点包含的线程唤醒
    abstract boolean release(int arg);
    //====================================独占式 获取与释放锁的相关方法 END======================


    //====================================共享式 获取与释放锁的相关方法 START======================
    // 共享式的获取同步状态，如果当前线程未获取到同步状态，将会进入同步队列等待，与独占式获取的主要区别是在同一时刻可以有多个线程获取到同步状态
    abstract void acquireShared(int arg);

    // 该方法响应中断
    abstract void acquireSharedInterruptibly(int arg);

    // 增加了超时限制。
    abstract boolean tryAcquireSharedNanos(int arg, long nanosTimeout);

    // 共享式释放同步状态
    abstract boolean releaseShared(int arg);
    //====================================共享式 获取与释放锁的相关方法 END======================


    // 获取等待在同步队列上的线程集合。
    abstract Collection<Thread> getQueuedThreads();
}