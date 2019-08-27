package org.java.core.base.collection.queue.deque;

import java.util.NoSuchElementException;

/**
 * java.util.ArrayDeque源码解析
 */
public class MyArrayDeque<E> {

    // 数组
    transient Object[] elements; // non-private to simplify nested class access
    // 头索引
    transient int head;
    // 尾索引
    transient int tail;

    private static final int MIN_INITIAL_CAPACITY = 8;

    // ArrayDeque内部使用的循环数组的容量，当首次进行初始化的时候，
    // 最小容量为8，如果超过8，扩大成2的幂
    // 调用带有容量参数的构造函数后，数组初始化过程
    private void allocateElements(int numElements) {
        int initialCapacity = MIN_INITIAL_CAPACITY; // 最小容量为8
        if (numElements >= initialCapacity) { // 如果要分配的容量大于等于8，扩大成2的幂；否则使用最小容量8
            initialCapacity = numElements;
            initialCapacity |= (initialCapacity >>>  1);
            initialCapacity |= (initialCapacity >>>  2);
            initialCapacity |= (initialCapacity >>>  4);
            initialCapacity |= (initialCapacity >>>  8);
            initialCapacity |= (initialCapacity >>> 16);
            initialCapacity++;

            if (initialCapacity < 0)
                initialCapacity >>>= 1;
        }
        elements = new Object[initialCapacity]; // 构造数组
    }

    // 把数组容量扩大2倍，同时还会重置头索引和尾索引，头索引置为0，尾索引置为原容量的值。
    //比如容量为8，扩容为16，头索引变成0，尾索引变成8。
    private void doubleCapacity() {
        assert head == tail;
        int p = head;
        int n = elements.length;
        int r = n - p; // number of elements to the right of p
        int newCapacity = n << 1;
        if (newCapacity < 0)
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, p, a, 0, r);
        System.arraycopy(elements, 0, a, r, p);
        elements = a;
        head = 0;
        tail = n;
    }

    //addFirst方法跟addLast方法相反，添加数据到双向队列头端：
    public void addFirst(E e) {
        if (e == null)
            throw new NullPointerException();
        elements[head = (head - 1) & (elements.length - 1)] = e; // 根据头索引，添加到头端，头索引-1
        if (head == tail) // 如果头索引和尾索引重复了，说明数组满了，进行扩容
            doubleCapacity();
    }

    public void addLast(E e) {
        if (e == null)
            throw new NullPointerException();
        elements[tail] = e; // 根据尾索引，添加到尾端
        // 尾索引+1，如果尾索引和头索引重复了，说明数组满了，进行扩容
        if ( (tail = (tail + 1) & (elements.length - 1)) == head)
            doubleCapacity();
    }

    public E removeFirst() {
        E x = pollFirst();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }

    public E removeLast() {
        E x = pollLast();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }

    public E pollLast() {
        int t = (tail - 1) & (elements.length - 1); // 尾索引 -1
        @SuppressWarnings("unchecked")
        E result = (E) elements[t]; // 根据尾索引，得到尾元素
        if (result == null)
            return null;
        elements[t] = null; // 尾元素置空
        tail = t;
        return result;
    }

    public E pollFirst() {
        int h = head;
        E result = (E) elements[h];
        // Element is null if deque empty
        if (result == null)
            return null;
        elements[h] = null;     // Must null out slot
        head = (h + 1) & (elements.length - 1);
        return result;
    }

    // ####### Queue methods #########

    public boolean add(E e) {
        addLast(e);    // 使用的add方法，其实内部使用了addLast方法，addLast也就添加数据到双端队列尾端：
        return true;
    }

    public E remove() {
        return removeFirst();
    }

    // ####### Stack methods ##########

    public void push(E e) {
        addFirst(e);
    }

    public E pop() {
        return removeFirst();
    }

}
