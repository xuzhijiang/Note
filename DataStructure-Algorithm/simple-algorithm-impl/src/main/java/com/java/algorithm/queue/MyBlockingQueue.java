package com.java.algorithm.queue;

import java.util.concurrent.TimeUnit;

/**
 * since 1.5
 * 阻塞队列接口BlockingQueue继承自Queue接口
 * 常用的阻塞队列具体类有ArrayBlockingQueue、LinkedBlockingQueue、
 * PriorityBlockingQueue、LinkedBlockingDeque等。
 * @param <E>
 */
public interface MyBlockingQueue<E> extends MyQueue<E>{

    // 添加元素到队列里，添加成功返回true，由于容量满了添加失败会抛出IllegalStateException异常
    boolean add(E e);

    // 添加元素到队列里，添加成功返回true，添加失败返回false
    boolean offer(E e);

    // 添加元素到队列里，如果容量满了会阻塞直到容量不满
    void put(E e) throws InterruptedException;

    // 删除队列头部元素，如果队列为空，一直阻塞到队列有元素并删除
    E take() throws InterruptedException;

    // 删除队列头部元素，如果队列为空，返回null。否则返回元素。
    E poll(long timeout, TimeUnit unit)
            throws InterruptedException;

    int remainingCapacity();

    // 基于对象找到对应的元素，并删除。删除成功返回true，否则返回false
    boolean remove(Object o);

    public boolean contains(Object o);
}
