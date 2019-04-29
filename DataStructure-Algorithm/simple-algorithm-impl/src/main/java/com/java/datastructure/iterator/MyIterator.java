package com.java.datastructure.iterator;

/**
 * 关于迭代器，Java中已经相关的接口定义java.util.Iterator，其定义了一个迭代器最基本要实现的功能，
 * 虽然实现这个接口不是必要的，但是这里打算自己实现这个接口
 * @param <E>
 */
public interface MyIterator<E> {

    /**
     * 是否还有更多的元素可以迭代
     * @return
     */
    boolean hasNext();

    /**
     * 返回下一个元素
     * @return
     */
    E next();

    /**
     * 将迭代器当前迭代的元素，从链表中移除
     */
    void remove();
}
