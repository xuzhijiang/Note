package org.java.core.base.concurrent.chapter5.AQSCore;

import org.java.core.advanced.Unsafe.UnsafeInstance;
import sun.misc.Unsafe;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 公平锁
 */
public class TulingLock {

    /**
     * 当前加锁的状态,记录加锁的次数
     */
    private volatile int state = 0;

    private final static Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

    /**
     * state在内存中的偏移
     */
    private final static long stateOffset;

    /**
     * 当前持有锁的线程
     */
    private Thread lockHolder;

    // 是一个线程安全的队列,但不能是一个阻塞队列
    private ConcurrentLinkedQueue<Thread> waiters = new ConcurrentLinkedQueue();

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(TulingLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public int getState() {
        return state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    public boolean compareAndSwapInt(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    public boolean acquire() {
        Thread t = Thread.currentThread();
        // 第一板斧: 通过cas保证state修改的原子性
        // waiters.size()==0 || t==waiters.peek(): 队列为空,或者当前线程就是队头的线程的时候才有获取锁的资格,这样可以保证是公平锁
        if ((waiters.size()==0 || t==waiters.peek()) && compareAndSwapInt(0, 1)) { // 原子的比较与交换,这步cas可以保证只1个线程可以执行成功
            setLockHolder(t);
            return true;
        }
        return false;
    }

    /**
     * 加锁
     */
    public void lock() {
        if (acquire()) {
            return;
        }

        Thread current = Thread.currentThread();

        // 第三板斧: // 如果一直自旋,让一个空循环一直跑,会严重浪费cpu资源,所以自旋
        // 到一定程度的时候,需要让出cpu使用权,这个时候就需要让一直自旋的线程阻塞

        // 如果上面的T1加锁成功,那么T2 T3就会被阻塞在这里,这个自旋到一定程度一定要阻塞
        // 否则当线程数量增多的时候,会有越来越多的线程一直空耗cpu资源,造成资源大量浪费,机器就扛不住
        // 我们需要一个数据结构保存所有被阻塞的线程,并且在持有锁的线程解锁的时候,去唤醒!
        // 需要一个FIFO这样的队列,把先来的线程放到队头,后来的线程放到队尾,等到唤醒的时候,
        // 先把队头的线程唤醒,
        // 这样可以避免像synchronized唤醒所有阻塞停留在管城对象上的线程(synchronized会让这些线程公平竞争)
        waiters.add(current); // 保证入队的安全性,不能丢失线程,而且不能用基于aqs的阻塞队列,我们用一个线程安全的队列,

        // 第二板斧: 通过自旋保证T1 T2 T3不跳出竞争锁的逻辑之外
        for (;;) { // T1 T2 T3

            if (current == waiters.peek() && acquire()) {
                waiters.poll();
                return;
            }

            // 思考: 这里为什么没用Object.wait()方法?
            // 因为多个线程竞争一把锁,只有一个线程能拿到,其他线程就会使用set集合来管理,是无序的
            // 那么唤醒的时候,能够抢到锁的线程就是随机的,我们现在要实现公平锁,也就是针对绝对公平的场景,先来先唤醒,所以使用LockSupport.park()
            LockSupport.park(); // 如果没有人唤醒,会一直处于waiting状态

            // 本例中没有考虑线程被中断,也就是Thread.interrupt()方法被调用的情况
            // 因为一个线程调用LockSupport.park();之后,如果被中断了(Thread.interrupt()方法被调用),马上就会被唤醒,本例中没有考虑这种情况

            // 注意区分: 在Thread.sleep()的时候,线程被中断(Thread.interrupt()方法被调用),会抛出异常
            // 在线程调用LockSupport.park();之后,线程被中断,不会抛出异常.
        }
    }

    /**
     * 解锁
     */
    public void unlock() {
        if (Thread.currentThread() != getLockHolder()) {
            throw new RuntimeException("lockHolder is not currentThread");
        }
        int state = getState();
        if (compareAndSwapInt(state, 0)) {
            setLockHolder(null);
            // 释放锁,通知唤醒被阻塞的队列头部的线程,实现绝对公平
            Thread t = waiters.peek();
            if (t != null) {
                LockSupport.unpark(t);
            }
        }
    }

}
