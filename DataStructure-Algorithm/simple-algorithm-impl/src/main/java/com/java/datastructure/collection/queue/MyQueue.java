package com.java.datastructure.collection.queue;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @since 1.5
 * @param <E> the type of elements held in this collection
 */
public interface MyQueue<E> extends Collection<E> {

    /**
     * 插入元素到队列尾部，如果容量满了，会抛出异常
     *
     * @param e
     * @return 如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列中，
     * 成功时返回true，如果当前没有可用空间则抛出IllegalStateException
     * @throws IllegalStateException 如果容量满了
     */
    boolean add(E e);

    /**
     * 插入元素到队列尾部，如果有容量限制，此方法优先于add方法，
     * 因为add方法在容量满的时候，会抛出IllegalStateException异常.
     * @param e
     * @return 插入成功返回true，否则返回false
     */
    boolean offer(E e);

    /**
     *检索并且删除队列的头元素,这个方法和poll的区别是，
     *如果队列为空，就抛出 NoSuchElementException异常
     * @return 队列的头元素
     * @throws NoSuchElementException if the queue is empty.
     */
    E remove();

    /**
     * 轮询,检索并且删除队列的头元素
     * @return 队列的头元素,如果队列为空，就返回null
     */
    E poll();

    /**
     * 检索并且不删除队列的头元素,如果队列为空，抛出NoSuchElementException异常
     * @return 队列的头元素
     * @throws NoSuchElementException 如果队列为空
     */
    E element();

    /**
     * 窥视,检索并且不删除队列的头元素，如果队列为空，就返回null
     *
     * @return 队列的头元素,如果队列为空，就返回null
     */
    E peek();
}
