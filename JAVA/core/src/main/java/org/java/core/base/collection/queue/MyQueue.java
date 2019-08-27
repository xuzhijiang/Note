package org.java.core.base.collection.queue;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @since 1.5
 */
public interface MyQueue<E> extends Collection<E> {

    /**
     * 插入元素到队列尾部，如果容量满了，会抛出IllegalStateException异常
     * 执行成功时返回true
     */
    boolean add(E e);

    /**
     * 插入元素到队列尾部，插入成功返回true，否则返回false
     * 如果有容量限制,此方法优先于add方法，因为容量满的时候，不会抛出异常.
     */
    boolean offer(E e);

    /**
     * 删除队头元素,返回这个要删除的元素
     * 这个方法和poll的区别是，如果队列为空，就抛出 NoSuchElementException异常
     * @throws NoSuchElementException if the queue is empty.
     */
    E remove();

    /**
     * 轮询队头元素,并且删除队列的头元素,返回要删除的元素.
     * 优于remove,因为队列为空的时候,不抛异常,返回null
     */
    E poll();

    /**
     * 查看队头元素,如果队列为空，抛出NoSuchElementException异常
     * @throws NoSuchElementException 如果队列为空
     */
    E element();

    /**
     * 查看队头元素，如果队列为空，就返回null
     * 优于element,因为队列为空的时候不抛异常.
     */
    E peek();
}
