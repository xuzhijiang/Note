package org.java.core.base.concurrent.chapter5;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockTest {

    @Test
    public void holdCountTest() {
        // 注意这里直接定义了ReentrantLock，通过直接使用这个类而不是Lock接口，
        // 我们可以使用其独有的方法getHoldCount()，这个方法表示的是当前线程持有锁的次数
        ReentrantLock lock = new ReentrantLock();

        System.out.println(lock.getHoldCount());// 没有调用lock之前，hold count为0
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
        // 通过输出，我们可以看到，一个线程(本案例就是主线程)的确可以多次持有同一个锁。
        // 每当调用lock方法时，次数+1，每当调用unlock方法时，次数-1。所以中间打印出了2，
        // 这事实上就体现了所谓的可重入。需要注意的是：当getHoldCount()不为0的时候，
        // 表示锁当前正在被某个线程使用，只有当其为0的时候，其他线程才有机会获取这个锁。
    }

    /**
     * Lock接口的独占性演示
     */
    @Test
    public void exclusiveLockDemo() throws InterruptedException {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread("Thread A") {
            @Override
            public void run() {
                lock.lock();//加锁
                try {
                    work();//work
                } finally {
                    lock.unlock();
                }
            }
        };


        Thread t2 = new Thread("Thread B"){
            @Override
            public void run() {
                lock.lock();//加锁
                try{
                    work();//work
                }finally {
                    lock.unlock();//释放锁
                }

            }
        };
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private void work() {
        try {
            System.out.println(Thread.currentThread().getName()+" started to work currrentTime:"+System.currentTimeMillis());
            Thread.currentThread().sleep(1000);
            System.out.println(Thread.currentThread().getName()+" end work,currrentTime:"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
