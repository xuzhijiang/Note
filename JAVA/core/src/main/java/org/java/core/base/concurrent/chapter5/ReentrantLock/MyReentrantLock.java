package org.java.core.base.concurrent.chapter5.ReentrantLock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * ReentrantLock里面有一个内部类Sync，Sync继承AQS
 * 添加锁和释放锁的大部分操作实际上都是在Sync中实现的。
 * 它有公平锁FairSync和非公平锁NonfairSync两个子类。ReentrantLock默认使用非公平锁，
 */
/**
 * 互斥锁ReentrantLock
 *
 * 我们发现在ReentrantLock虽然有公平锁和非公平锁两种，但是它们添加的都是独享锁。
 * 所以可以确定ReentrantLock无论读操作还是写操作，添加的锁都是都是独享锁。
 */
public class MyReentrantLock {

    private final MyReentrantLock.Sync sync;

    public MyReentrantLock() {
        sync = new MyReentrantLock.NonfairSync();
    }

    public MyReentrantLock(boolean fair) {
        sync = fair ? new MyReentrantLock.FairSync() : new MyReentrantLock.NonfairSync();
    }

    public void lock() {
        sync.lock();
    }

    public void unlock() {
        sync.release(1);
    }

    abstract static class Sync extends AbstractQueuedSynchronizer {

        abstract void lock();

        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            // Sync继承了AQS，父类AQS中维护了一个state变量来表示许可证的个数,state的初始值为0
            int c = getState();
            if (c == 0) { // 如果state == 0,表明表示没有其他线程拿到锁,当前线程尝试去获取锁
                // 尝试更新state,尝试把state设置为自己要获得的许可数
                if (compareAndSetState(0, acquires)) {
                    // 更新state成功的Thread会走到这里,说明当前线程获取到锁
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            //  走到这里说明如果status != 0,说明已经有其他线程获取到锁了,那么就要去判断已经拿到锁的线程是不是当前线程
            else if (current == getExclusiveOwnerThread()) {
                // 走进else if代码块,说明拿到锁的线程就是当前线程,那么当前线程依然可以获取锁,这个也就解释了为什么ReentrantLock是可重入锁
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                // 重新更新获得的许可数
                setState(nextc);
                return true;
            }
            return false;
        }

        // 返回true表示当前线程已经把锁彻底释放
        protected final boolean tryRelease(int releases) {
            // releases是要释放的许可数
            int c = getState() - releases;
            // 如果当前线程不是持有锁的线程,直接抛异常
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            // 如果c==0,表示锁已经彻底释放了
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            // 如果c != 0,要更新state
            setState(c);
            return free;
        }
    }

    /**
     * 非公平锁
     */
    static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        final void lock() {
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                acquire(1);
        }

        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }

    /**
     * 公平锁
     */
    static final class FairSync extends Sync {

        final void lock() {
            acquire(1);
        }

        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                // 我们可以明显的看出公平锁与非公平锁的lock()方法唯一的区别就在于公平锁在获取锁时多了一个限制条件：hasQueuedPredecessors()
                // 该方法主要做一件事情：主要是判断当前线程是否位于同步队列中的第一个。如果是则返回true，否则返回false
                if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

}
