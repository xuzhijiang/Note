package com.java.algorithm.queue.BlockingQueue;

import com.java.algorithm.queue.MyQueue;

import java.util.concurrent.TimeUnit;

/**
 * since 1.5
 *
 * 阻塞队列接口BlockingQueue继承自Queue接口
 * 常用的阻塞队列具体类有ArrayBlockingQueue、LinkedBlockingQueue、
 * PriorityBlockingQueue、LinkedBlockingDeque等。
 * @param <E>
 */
public interface MyBlockingQueue<E> extends MyQueue<E> {

    // 3个添加元素的方法: add(Collection), offer(Queue), put(BlockingQueue)
    // add方法内部调用offer方法，如果队列满了，抛出IllegalStateException异常，否则返回true
    // offer方法如果队列满了，返回false，否则返回true
    // add方法和offer方法不会阻塞线程，put方法如果队列满了会阻塞线程，
    // 直到有线程消费了队列里的数据才有可能被唤醒。
    // 这3个方法内部都会使用可重入锁保证原子性。

    // 添加元素到队列里，添加成功返回true，由于容量满了添加失败会抛出IllegalStateException异常
    // 此方法不是自带的，而是继承自Collection接口
    boolean add(E e);

    // 添加元素到队列里，添加成功返回true，添加失败返回false
    // 不是自带，是从Queue继承而来
    boolean offer(E e);

    // 添加元素到队列里，如果容量满了会阻塞直到容量不满
    // 是BlockingQueue自带的，不是从Queue继承而来的.
    void put(E e) throws InterruptedException;

    // 3个删除方法: take, poll(Queue), remove(Collection)
    // poll方法对于队列为空的情况，返回null，否则返回队列头部元素。
    // remove方法取的元素是基于对象的下标值，删除成功返回true，否则返回false。
    // poll方法和remove方法不会阻塞线程。
    // take方法对于队列为空的情况，会阻塞并挂起当前线程，直到有数据加入到队列中。
    // 这3个方法内部都会调用notFull.signal方法通知正在等待队列满情况下的阻塞线程。

    // 删除队列头部元素，如果队列为空，一直阻塞到队列有元素并删除
    // 是BlockingQueue自带的，不是从Queue继承而来的.
    E take() throws InterruptedException;

    // 删除队列头部元素，如果队列为空，返回null。否则返回元素。
    // 从Queue继承而来
    E poll(long timeout, TimeUnit unit) throws InterruptedException;

    int remainingCapacity();

    // 基于对象找到对应的元素，并删除。删除成功返回true，否则返回false
    // 从Collection接口继承而来
    boolean remove(Object o);

}
