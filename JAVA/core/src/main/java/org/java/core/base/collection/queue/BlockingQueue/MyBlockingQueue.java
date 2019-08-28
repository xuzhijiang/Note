package org.java.core.base.collection.queue.BlockingQueue;

import org.java.core.base.collection.queue.MyQueue;

import java.util.concurrent.TimeUnit;

/**
 * since 1.5
 * 阻塞队列接口BlockingQueue继承自Queue接口
 * 常用的阻塞队列具体类有ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue
 */
public interface MyBlockingQueue<E> extends MyQueue<E> {

    // *** Queue methods ***

    // add方法和offer方法不会阻塞线程
    boolean add(E e);
    boolean offer(E e);

    // poll方法和remove方法不会阻塞线程
    E remove();
    E poll();

    E element();

    E peek();

    // *** BlockingQueue methods ***

    // 添加元素到队列里，如果容量满了会阻塞,直到有线程消费了队列里的数据(有空闲空间),才有可能被唤醒
    // 是BlockingQueue自带的，不是从Queue继承而来的.
    // 在BlockingQueue的 实现类中,这3个方法内部都会使用可重入锁保证原子性。
    void put(E e) throws InterruptedException;

    // 删除队列头部元素，如果队列为空，一直阻塞到队列有元素并删除
    // 是BlockingQueue自带的，不是从Queue继承而来的.
    // take方法对于队列为空的情况，会阻塞并挂起当前线程，直到有数据加入到队列中。
    // remove(),poll(),take() 3个方法内部都会调用notFull.signal方法通知正在等待队列满情况下的阻塞线程。
    E take() throws InterruptedException;

    // 等待指定的时间再放弃添加.
    boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;

    // 检索，并且删除队列头部元素，如果队列为空，返回null。否则返回元素。
    // 是BlockingQueue自带的，不是从Queue继承而来的.
    // 如果队列为空,则等待指定的时间,,看看段时间内是否会有元素添加到队列中,过了指定的时候后如果队列还是空的,就返回null.
    E poll(long timeout, TimeUnit unit) throws InterruptedException;
}
