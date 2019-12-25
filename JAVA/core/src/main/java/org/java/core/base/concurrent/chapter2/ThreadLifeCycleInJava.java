package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

// 通过jps查看当前进程的pid,然后jstack <pid>查看
public class ThreadLifeCycleInJava {

    // NEW状态
    @Test
    public void testNew() {
        // NEW状态比较简单，实例化一个线程之后，并且这个线程没有调用start()，这个时候的状态就是NEW
        Thread stateNewThread = new Thread();
        System.out.println("THREAD STATE NEW: " + stateNewThread.getState());// NEW
    }

    // RUNNABLE状态
    @Test
    public void testRunnable() {
        Thread stateRunnableThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<Integer.MAX_VALUE;i++){
                    System.out.println(i);
                }
            }
        }, "xzj-RUNNABLE-Thread");
        // start()之后代表线程进入了就绪状态,代表可以运行了,但是并不是马上线程就启动了.
        // start()之后,什么时候真正run方法? 答案是等待操作系统的调度.依赖于OS
        stateRunnableThread.start();
        // 通过jps和jstack查看线程状态
        System.out.println("THREAD STATE RUNNABLE: " + stateRunnableThread.getState());
    }

    // BLOCKED状态
    @Test
    public void testBlocked() throws InterruptedException {
        // 线程A和线程B共同竞争一个锁,在同步代码块中是一个死循环,然后调用了SleepUtils.sleep()方法
        // 因此一直不会释放锁。所以二者，应该是有一个大部分情况下处于Time-Waiting状态，
        // 另一个处于Blocked状态。
        // 如果线程A持有锁，那么线程B处于BLOCKED状态；
        // 如果线程B持有锁，那么线程A处于BLOCKED状态

        final Object lock = new Object();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    // 某一个线程获取锁后，一直死循环，不会释放锁.
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + " - Blocked........");

                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Thread-A");

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    // 某一个线程获取锁后，一直死循环，不会释放锁.
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + " - Blocked........");

                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Thread-B");
        threadA.start();
        threadB.start();

        Thread.sleep(100 * 1000);
    }


    private static void testWaiting() {
        // Object的wait方法、Thread的join方法以及Conditon的await方法都会产生WAITING状态。

        //1.没有时间参数的Object的wait方法
        //testWaitingMethodWait();

        // 2. 没有时间参数的join方法
        //testWaitingMethodJoin();

        //3.没有时间参数的Condition的await方法
        //testWaitingMethodAwait();
        // Condition的await方法跟Obejct的wait方法原理是一样的，故也是WAITING状态
    }

    @Test
    public void testJoinWaiting() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread-A over");
            }
        }, "xzj-Thread-A");
        threadA.start();

        try {
            threadA.join();
            // 主线程main处于WAITING状态：threadA处于TIMED_WAITING
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWaitWaiting() throws InterruptedException {
        final Object lock = new Object();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println("Thread name: " + Thread.currentThread().getName());
                        lock.wait();// Thread-A调用了锁的wait，处于WAITING状态：
                        // Object.wait()方法只能够在同步代码块中调用(否则会抛出IllegalMonitorStateException)。
                        // 调用了wait()方法后，会释放锁。
                        System.out.println("wait over");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "xzj-Thread-A");

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("Thread name: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(15000);// 进入TIMED_WAITING
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notifyAll();
                }
            }
        }, "xzj-Thread-B");
        threadA.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();

        threadA.join();
        threadB.join();
    }

    @Test
    public void testTimedWaiting() {
        // TIMED_WAITING状态跟TIMEING状态类似，是一个有等待时间的等待状态，不会一直等待下去。
        // 最简单的TIMED_WAITING状态例子就是Thread的sleep方法：
        // 或者是Object的wait方法带有时间参数、
        // Thread的join方法带有时间参数也会让线程的状态处于TIMED_WAITING状态。
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread-A over");
            }
        }, "Thread-A");
        threadA.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadA.getState()); // TIMED_WAITING
    }

    @Test
    public void testTimedTerminated() {
        // 线程终止的状态，线程执行完成，结束生命周期。
        Thread t = new Thread();
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.getState()); // TERMINATED
    }

}
