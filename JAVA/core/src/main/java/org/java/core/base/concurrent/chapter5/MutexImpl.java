package org.java.core.base.concurrent.chapter5;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Mutex实现
 */
public class MutexImpl implements Mutex{

    // 仅需要将操作代理到Sync上即可
    private Sync sync = new Sync();

    // 自定义同步组件将使用同步器AbstractQueuedSynchronizer，提供的模板方法来实现自己的同步语义。

    // 以AQS-AbstractQueuedSynchronizer模板方法acquire(int arg)为例,
    // 这是一个获取执行许可的方法，arg表示获取许可的数量。其源码如下:

//    public final void acquire(int arg) {
//        if (!tryAcquire(arg) && //功能1:state变量的维护，开发者需要覆写此方法
//                acquireQueued(addWaiter(Node.EXCLUSIVE), arg))//功能2：如果没有获取锁成功，则线程进入等待队列，这个是AQS完全覆写的
//            selfInterrupt();//不响应中断，也就是不抛出异常，目前不在讨论范围，后面会介绍
//    }

    // 可以看到在模板方法acquire中，分别调用了state变量维护相关方法tryAcquire(arg)
    // 以及等待队列维护相关方法acquireQueued(addWaiter(Node.EXCLUSIVE), arg))。
    //对于线程等待队列的维护，AQS已完成，我们不要考虑。

    //对于state变量的维护，调用的是需要开发者覆写的tryAcquire(int arg)方法实现。
    // 在覆写过的tryAcquire(int arg)方法中，通过调用getState()、setState()、
    // compareAndSetState(int expect, int update)方法，从而实现对state变量的维护与访问。

    @Override
    public void lock() {
        // 可以查看AbstractQueuedSynchronizer内部的acquire来学习这部分

        // 独占式获取同步状态，如果当前线程获取同步状态成功，则由该方法返回，否则，线程将会进入同步队列等待，
        // 该方法将会调用重写的tryAcquire(int arg)方法，这是一个获取执行许可的方法，arg表示获取许可的数量

        // 可用许可的数量的支持：它使用了一个int类型volatile的state变量，表示同步状态，也就是说笔者所说"执行许可"。
        // 初始值默认为0。最简单的情况下，假设我们限定最多有5个许可，那么state值为0就表示当前一个许可都未使用。
        // 线程每获取一个许可，state变量+1，表示已经使用了1个许可。
        // 当然state变量的含义是由开发者定义的，你完全可以用负数例如state为-5时，表示5个许可都被释放完。
        sync.acquire(1);
    }


    @Override
    public void release() {
        // 独占式释放同步状态，该方法会在释放同步状态后，将同步队列中第一个节点包含的线程唤醒
        sync.release(1);
    }



    // 独占式同步组件实现:

    // 我们编写一个同步组件，例如我们想实现一个独占锁，假设为Sync，其继承了AQS。
    // 只需要在Sync类中覆写tryRelease和tryAcquire即可，
    // 但是由于继承AQS的时候，会把tryAcquireShared、tryReleaseShared等共享锁方法也继承下来。
    // 而Sync并不会实现这些共享式同步组件的方法，因为Sync只是一个独占锁而已，
    // 从业务含义上，因此应该将这些方法屏蔽，从而防止用户误操作。按照最佳实现，
    // 屏蔽的方式是定义一个新的接口，假设用Mutex表示，这个接口只定义了独占锁相关方法，
    // 再编写一个类MutexImpl实现Mutex接口，而对于同步组件Sync类的操作，都封装在MutexImpl中。

    // 同步组件推荐定义为静态内部类：因为这个同步组件通常是为实现特定的目的而实现，
    // 可能只适用于特定的场合。如果某个同步组件不具备通用性，我们应该将其定义为一个私有的静态内部类。
    // 结合第一点，我们编写的同步组件Sync应该是MutexImpl的一个私有的静态内部类。
    private static class Sync extends AbstractQueuedSynchronizer {//抽象队列同步器

        /**
         *
         * @param arg
         * @return return true if 获取锁成功。
         */
        @Override
        protected boolean tryAcquire(int arg) {
            // AbstractQueuedSynchronizer内部维护了一个stateOffset变量，
            // 这个Offset变量维护了AbstractQueuedSynchronizer内部的private volatile int state;
            // 这个变量在内存的偏移量.以便Unsafe可以通过内存操作的方式修改其变量的值.

            // compareAndSetState解释:
            // 如果当前状态值(内部的state)等于预期值，则以原子方式将同步状态设置为给定的更新值。

            // 尝试将锁对象的state标记由期望值0设置为1(表示已经使用了一个许可证)，如果设置成功，
            // 则说明成功获取了锁，如果失败则通过调用同步队列的acquire()方法，
            // 将线程加入同步队列进行锁等待。这里compareAndSetState便是获取锁的关键实现
            System.out.println(Thread.currentThread().getName() + "尝试获取锁");
            boolean retValue = compareAndSetState(0, 1);
            System.out.println(Thread.currentThread().getName() + "- tryAcquire retValue: " + retValue);
            if(!retValue){
                System.out.println(Thread.currentThread().getName() + "加入等待锁的队列");
            } else{
                System.out.println(Thread.currentThread().getName() + "成功获取锁");
            }
            return retValue;
        }

        @Override
        protected boolean tryRelease(int arg) {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            return compareAndSetState(1, 0);
        }

    }
}
