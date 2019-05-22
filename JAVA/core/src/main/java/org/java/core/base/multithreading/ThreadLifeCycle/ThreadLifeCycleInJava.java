package org.java.core.base.multithreading.ThreadLifeCycle;

/**
 * 理解Java中线程的生命周期以及线程的状态是对于多线程程序的环境是
 * 非常重要的，在Java中创建一个thread，并且start它，但是thread
 * 从Runnable到Running再到Blocked依赖于OS线程调度器(thread scheduler)
 * 的实现，Java不能完全控制它.
 * <p>
 * <strong>New</strong>
 * 当我们用new 操作符new一个Thread对象的时候，线程状态是New Thread.at this point,
 * 线程还不是活的，它只是java程序内部的状态
 * <p>
 * <strong>Runnable</strong>
 * When we call start() function on Thread object, 
 * it’s state is changed to Runnable. The control is given to 
 * Thread scheduler to finish it’s execution. Whether to run this 
 * thread instantly or keep it in runnable thread pool before running, 
 * depends on the OS implementation of thread scheduler.
 * <p>
 * <p>Blocked/Waiting</p>
 * A thread can be waiting for other thread to finish using 
 * thread join or it can be waiting for some resources to 
 * available. For JdbcQuickStartExample producer consumer problem or waiter
 * notifier implementation or IO resources, then it’s state is 
 * changed to Waiting. Once the thread wait state is over, it’s 
 * state is changed to Runnable and it’s moved back to runnable thread pool.
 * <p>
 * <strong>Dead</strong>
 * Once the thread finished executing, it’s state is changed to 
 * Dead and it’s considered to be not alive. 
 * <p>
 * Above are the different states of thread. It’s good to know 
 * them and how thread changes it’s 
 * state. That’s all for thread life cycle in java.
 */
public class ThreadLifeCycleInJava {

    public static void main(String[] args) {

//        testNew();

//        testRunnable();

//        testBlocked();

        //testWaiting();

//        testTimedWaiting();

        testTimedTerminated();
    }

    private static void testTimedTerminated() {
        // 线程终止的状态，线程执行完成，结束生命周期。
        Thread threadA = new Thread();
        threadA.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadA.getState()); // TERMINATED
    }

    private static void testTimedWaiting() {
        // TIMED_WAITING状态跟TIMEING状态类似，是一个有等待时间的等待状态，不会一直等待下去。
        //
        // 最简单的TIMED_WAITING状态例子就是Thread的sleep方法：

        // 或者是Object的wait方法带有时间参数、Thread的join方法带有时间参数也会让线程的状态处于TIMED_WAITING状态。
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
        }, "WAITING-Thread-A");
        threadA.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadA.getState()); // TIMED_WAITING
    }

    private static void testWaiting() {
        // Object的wait方法、Thread的join方法以及Conditon的await方法都会产生WAITING状态。

        //1.没有时间参数的Object的wait方法
        //testWaitingMethodWait();

        // 2.Thread的join方法
        //testWaitingMethodJoin();

        //3.没有时间参数的Condition的await方法
        testWaitingMethodAwait();
    }

    private static void testWaitingMethodAwait() {
        // Condition的await方法跟Obejct的wait方法原理是一样的，故也是WAITING状态
    }

    private static void testWaitingMethodJoin() {
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
        }, "WAITING-Thread-A");
        threadA.start();
        try {
            threadA.join();
            // 主线程main处于WAITING状态：threadA处于TIMED_WAITING
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testWaitingMethodWait() {
        final Object lock = new Object();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println("Thread name: " + Thread.currentThread().getName());
                        lock.wait();// WAITING-Thread-A调用了lock的wait，处于WAITING状态：
                        System.out.println("wait over");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "WAITING-Thread-A");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("Thread name: " + Thread.currentThread().getName());
                    try {
                        // "WAITING-Thread-B" #12 prio=5 os_prio=0 tid=0x000000001893a800 nid=0x288c waiting on condition [0x0000000019b0f000]
                        //   java.lang.Thread.State: TIMED_WAITING (sleeping)
                        Thread.sleep(15000);// 进入TIMED_WAITING
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notifyAll();
                }
            }
        }, "WAITING-Thread-B");
        threadA.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
    }

    // BLOCKED状态
    private static void testBlocked() {
        // 线程A和线程B都需要持有lock对象的锁才能调用方法。
        // 如果线程A持有锁，那么线程B处于BLOCKED状态；
        // 如果线程B持有锁，那么线程A处于BLOCKED状态。例子中使用Thread.sleep方法主要是用于调试方便：

        // 使用jstack查看线程状态。由于线程A先执行，线程B后执行，
        // 而且线程A执行后调用了Thread.sleep方法，所以线程A会处于TIMED_WAITING状态，线程B处于BLOCKED状态：
        final Object lock = new Object();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("Thread Name: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "BLOCKED-Thread-A");

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("Thread Name: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "BLOCKED-Thread-B");
        threadA.start();
        threadB.start();
    }

    // RUNNABLE状态
    private static void testRunnable() {
        Thread stateRunnableThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<Integer.MAX_VALUE;i++){
                    System.out.println(i);
                }
            }
        }, "RUNNABLE-Thread");
        stateRunnableThread.start();
        // 通过jps和jstack查看线程状态
        System.out.println("THREAD STATE RUNNABLE: " + stateRunnableThread.getState());
    }

    // NEW状态
    private static void testNew() {
        // NEW状态比较简单，实例化一个线程之后，并且这个线程没有开始执行，这个时候的状态就是NEW
        Thread stateNewThread = new Thread();
        System.out.println("THREAD STATE NEW: " + stateNewThread.getState());// NEW
    }

}
