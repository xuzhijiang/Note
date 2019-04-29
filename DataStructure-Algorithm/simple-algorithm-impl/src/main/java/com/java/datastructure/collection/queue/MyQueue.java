package com.java.datastructure.collection.queue;

import java.util.Collection;

public interface MyQueue<E> extends Collection<E> {

    boolean add(E e);

    boolean offer(E e);

    // Queue接口的方法，删除queue的头元素
    E remove();

    E poll();

    E element();

    E peek();
}
