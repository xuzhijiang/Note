package com.java.algorithm.queue;

import java.util.Collection;

public interface MyQueue<E> extends Collection<E> {

    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
