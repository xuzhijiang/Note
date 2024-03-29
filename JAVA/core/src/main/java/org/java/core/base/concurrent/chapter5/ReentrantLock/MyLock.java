package org.java.core.base.concurrent.chapter5.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface MyLock {
    // 阻塞的方式获取锁，调用该方法当前线程会获取锁，当锁获得后，该方法返回
    void lock();

    // 可中断的获取锁，和lock()方法不同之处在于该方法会响应中断，即在锁的获取中可以中断当前线程
    void lockInterruptibly() throws InterruptedException;

    // 尝试非阻塞的获取锁，调用该方法后立即返回。如果能够获取到返回true，否则返回false
    boolean tryLock();

    // 超时获取锁，当前线程在以下三种情况下会被返回:
    // 1.当前线程在超时时间内获取了锁,
    // 2.当前线程在超时时间内被中断,
    // 3. 超时时间结束，返回false
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    // 释放锁
    void unlock();

    // 获取等待通知组件，该组件和当前的锁绑定，当前线程只有获取了锁，才能调用该组件的wait()方法，而调用后，当前线程将释放锁
    Condition newCondition();
}
