package org.java.core.base.concurrent.chapter2;

import java.util.concurrent.TimeUnit;

/**
 * 我们将通过jps，jstack工具演示说明线程的各个状态
 */
public class ThreadState {
    public static void main(String[] args) {
        // RunningThread线程运行一个不断自增加法，持续时间会很长，线程一直应该处于RUNNABLE状态。
        new Thread(new Running(), "RunningThread").start();

        // TimeWaiting线程里面是一个死循环，每次休眠5秒，因此大部分情况下，应该处于TIME-WAITING状态。
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();

        // WaitingThread-1和WaitingThread-2共同竞争一个类锁：Waiting.class。
        // 而同步快里面，又调用了wait()方法，先得到锁的线程会释放锁，
        // 因此最终2个线程都处于Waiting状态。
        new Thread(new Waiting(), "WaitingThread-1").start();
        new Thread(new Waiting(), "WaitingThread-2").start();

        // BlockedThread-1和BlockedThread-2线程也是共同竞争一个类锁：Blocked.class。
        // 而同步快里面，是一个死循环，然后调用了SleepUtils.sleep()方法，
        // 因此一直不会释放锁。所以二者，应该是有一个大部分情况下处于Time-Waiting状态，
        // 另一个处于Blocked状态。
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    /**
     * 表示线程正在运行
     */
    static class Running implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<Long.MAX_VALUE;i++){
                i++;
            }
        }
    }

    /**
     * 该线程不断的进行睡眠
     */
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while(true) {
                System.out.println(Thread.currentThread().getName() + " --- TimeWaiting-------");
                SleepUtils.second(5);
            }
        }
    }

    /**
     * 该线程在Waiting.class实例上等待
     */
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "--- Waiting...before...");
                        Waiting.class.wait();
                        System.out.println(Thread.currentThread().getName() + "--- Waiting....after..");
                        // 调用wait()方法，一个线程处于等待状态，通常是在等待其他线程完成某个操作。
                        // 本线程调用某个对象的wait()方法，其他线程处理完成之后，
                        // 调用同一个对象的notify或者notifyAll()方法，之后本线程才会继续执行.

                        // Object.wait()方法只能够在同步代码块中调用。
                        // 调用了wait()方法后，会释放锁。
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 该线程在Blocked.class实例上加锁后，不会释放该锁
     */
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                // 某一个线程获取锁后，一直死循环，不会释放锁.
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " - Blocked........");
                    SleepUtils.second(5);
                }
            }
        }
    }

    public static class SleepUtils {
        public static final void second(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
