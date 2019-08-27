package org.java.core.base.collection.queue.deque;

import java.util.Iterator;
import java.util.Queue;

public interface MyDeque<E> extends Queue<E> {

    // *** Deque methods ***

    /**
     * 如果容量够的话,往deque的front插入元素,如果容量满了,就抛出异常.
     * 当有容量限制的时候,应该优先使用offerFirst
     */
    void addFirst(E e);

    /**
     * 如果容量够的话,往deque的end插入元素,如果容量满了,就抛出异常.
     * 当有容量限制的时候,应该优先使用offerLast
     */
    void addLast(E e);

    /**
     * 往deque的front插入元素,如果容量满了,返回false,插入成功,返回true
     */
    boolean offerFirst(E e);

    /**
     * 往deque的end插入元素,如果容量满了,返回false,插入成功,返回true
     */
    boolean offerLast(E e);

    /**
     * 删除deque的第一个元素,如果deque是空的,就抛异常.
     */
    E removeFirst();

    /**
     * 删除deque的最后一个元素,如果deque是空的,就抛异常.
     */
    E removeLast();

    /**
     * 删除deque的the first元素,如果deque是空的,就return null.
     */
    E pollFirst();

    /**
     * 删除deque的the last元素,如果deque是空的,就return null.
     */
    E pollLast();

    /**
     * 查看deque的the first元素,不删除,如果deque为空,抛出异常.
     */
    E getFirst();

    /**
     * 查看deque的the last元素,不删除,如果deque为空,抛出异常.
     */
    E getLast();

    /**
     * 查看deque的the first元素,不删除,如果deque为空,teturn null.
     */
    E peekFirst();

    /**
     * 查看deque的the last元素,不删除,如果deque为空,teturn null.
     */
    E peekLast();

    // *** Queue methods ***

    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();


    // *** Stack methods ***

    /**
     * Pushes an element onto the stack represented by this deque (in other
     * words, at the head of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, throwing an
     * {@code IllegalStateException} if no space is currently available.
     *
     * <p>This method is equivalent to {@link #addFirst}.
     */
    void push(E e);

    /**
     * Pops an element from the stack represented by this deque.  In other
     * words, removes and returns the first element of this deque.
     *
     * <p>This method is equivalent to {@link #removeFirst()}.
     */
    E pop();

    // *** Collection methods ***

    boolean remove(Object o);

    boolean contains(Object o);

    public int size();

    Iterator<E> iterator();

    Iterator<E> descendingIterator();

}
