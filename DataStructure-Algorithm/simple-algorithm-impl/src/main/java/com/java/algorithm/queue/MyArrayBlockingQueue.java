package com.java.algorithm.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyArrayBlockingQueue<E> implements MyBlockingQueue<E>{

    // 存储队列元素的数组，是个循环数组
    final Object[] items;

    // 内部通用锁
    final ReentrantLock lock;

    //take时候用于等待的条件
    private final Condition notEmpty;

    //put时候用于等待的条件
    private final Condition notFull;

    // 拿数据的索引，用于take，poll，peek，remove方法
    int takeIndex;

    // 放数据的索引，用于put，offer，add方法
    int putIndex;

    //实际元素数量
    int count;

    // Condition必须依赖于ReentrantLock
    private int remaining;

    // 剩下要返回的元素数量
    private int nextIndex;

    // 下一个需要返回的元素下标
    private E nextItem;    // 下一个需要返回的元素

    private E lastItem;    // 最后一个调用next操作返回的元素

    private int lastRet;  //最后一个调用next操作返回的元素的下标

    public MyArrayBlockingQueue(int capacity) {
        this(capacity, false);
    }

    //所以这个fair就是初始化内部的锁使用的，默认使用非公平锁
    public MyArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    /**
     * 这个构造函数的核心就是c.size()与capacity的大小关系对比了
     * 如果c.size()>capacity那就会报错，所以在初始化的时候要注意
     */
    public MyArrayBlockingQueue(int capacity, boolean fair, Collection<? extends E> c) {
        this(capacity, fair);
        //这种写法我们很常见，使用final表示引用不能改变，但又避免了直接使用成员变量
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = 0;
            try {
                for (E e : c) {//ArrayBlockingQueue不支持null元素噢
                    checkNotNull(e);
                    items[i++] = e;
                }
                //统一异常处理，但是我觉得这里是不是包装一下ex更为合理一些
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new IllegalArgumentException();
            }
            count = i;
            //如果数组全部被沾满的话就开始进入循环状态(数组长度就是队列深度)
            putIndex = (i == capacity) ? 0 : i;
        } finally {
            lock.unlock();
        }
    }

    //类型转换
    static <E> E cast(Object item) {
        return (E) item;
    }

    final int inc(int i) {//循环递增(源码里面经常出现一句话搞定这种写法)
        return (++i == items.length) ? 0 : i;
    }

    final int dec(int i) { //原理同上
        return ((i == 0) ? items.length : i) - 1;
    }

    final E itemAt(int i) {
        return MyArrayBlockingQueue.<E>cast(items[i]);
    }

    /**
     * 这个插入方法是所有公用插入方法的核心插入逻辑
     */
    private void insert(E x) {
        items[putIndex] = x;
        putIndex = inc(putIndex); //因为可能循环，所以不能直接putIndex++
        ++count;
        notEmpty.signal(); //有这个条件说明这个类在调用的时候一定要持有锁，否则这个notEmpty就没有用。插入之后要通知一下可能在put时候进入阻塞状态的线程
    }

    /**
     * 取出操作比插入操作多了类型转换这个操作(内部的数组是Obejct类型)，这个也要求在调用时候持有锁对象
     */
    private E extract() {
        final Object[] items = this.items;
        E x = MyArrayBlockingQueue.<E>cast(items[takeIndex]);
        items[takeIndex] = null;
        takeIndex = inc(takeIndex);
        --count;
        notFull.signal();
        return x;
    }

    /**
     *
     */
    void removeAt(int i) {
        final Object[] items = this.items;
        //如果要移除的元素恰好就是下一个出队的元素
        if (i == takeIndex) {
            items[takeIndex] = null;
            takeIndex = inc(takeIndex);
        } else {
            for (; ; ) {
                int nexti = inc(i);
                if (nexti != putIndex) {//讲i元素后面的元素全部前移一个位置(这里不用System.arraycopy的原因大家自己脑补)
                    items[i] = items[nexti];
                    i = nexti;
                } else { //如果要移除的元素是putIndex的前一个元素(说明移除不会造成元素移动)
                    items[i] = null;
                    putIndex = i;
                    break;
                }
            }
        }
        --count;
        notFull.signal();
    }

    /**
     * add会在队列满的时候抛异常
     */
    public boolean add(E e) {
        if (offer(e))
            return true;
        else
            throw new IllegalStateException("Queue full");
    }

    /**
     * 这里我们就知道其实add调用的还是offer方法
     * offer在队列满的情况下会直接返回false，不会阻塞
     */
    public boolean offer(E e) {
        checkNotNull(e);//不能插入空元素
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {//如果队列满的话直接放回false
            if (count == items.length)
                return false;
            else {
                insert(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E remove() {
        return null;
    }

    /**
     * put在队列满的情况下会直接阻塞，但是可以中断其阻塞
     */
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();//可以响应中断(所以一旦put阻塞后可以调用interrupt来中断)
        try {
            while (count == items.length)
                notFull.await(); //阻塞！！！
            insert(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * offer(E,long,TimeUnit)会在等待一段时间后返回,但是等待的过程中是可以响应中断的
     */
    public boolean offer(E e, long timeout, TimeUnit unit)
            throws InterruptedException {

        checkNotNull(e);
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                if (nanos <= 0)
                    return false;
                nanos = notFull.awaitNanos(nanos);
            }
            insert(e);
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * pull方法在队列为空的情况下直接返回null,不会阻塞
     */
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : extract();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E element() {
        return null;
    }

    /**
     * take跟put相对应，在队列为空的情况下会阻塞，但是可以响应中断
     */
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return extract();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 指定等待时间的pull,可以等待指定时间，响应中断
     */
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                if (nanos <= 0)//即使nanos小于零，但是队列有元素返回的之后就直接返回了，不会考虑这个值
                    return null;
                nanos = notEmpty.awaitNanos(nanos);
            }
            return extract();
        } finally {
            lock.unlock();
        }
    }

    /**
     * peek=偷窥，没有就直接返回null,不会阻塞
     */
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : itemAt(takeIndex);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 为了保证线程安全，还是加了必要的同步操作
     */
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public int remainingCapacity() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return items.length - count;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * 为了保证线程安全，还是加了必要的同步操作
     */
    public <T> T[] toArray(T[] a) {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int count = this.count;
            final int len = a.length;
            if (len < count) //这里跟List里面的toArray注意地方一样
                a = (T[]) java.lang.reflect.Array.newInstance(
                        a.getClass().getComponentType(), count);
            for (int i = takeIndex, k = 0; k < count; i = inc(i), k++)
                a[k] = (T) items[i];
            if (len > count)
                a[count] = null;//清除多余的引用
            return a;
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(Object o) {
        if (o == null) return false;
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock(); // 加锁，保证调用remove方法的时候只有1个线程
        try {
            for (int i = takeIndex, k = count; k > 0; i = inc(i), k--) { // 遍历元素
                if (o.equals(items[i])) { // 两个对象相等的话
                    removeAt(i); // 调用removeAt方法
                    return true; // 删除成功，返回true
                }
            }
            return false; // 删除成功，返回false
        } finally {
            lock.unlock(); // 释放锁，让其他线程可以调用remove方法
        }
    }

    public void clear() {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (int i = takeIndex, k = count; k > 0; i = inc(i), k--)
                items[i] = null;
            count = 0;
            putIndex = 0;
            takeIndex = 0;
            notFull.signalAll(); //注意，清空就是大幅度的take操作一样，一定最后要signalAll()来唤醒等待在notFull上的线程
        } finally {
            lock.unlock();
        }
    }

    /**
     * 将队列的元素全部输出到c里面(不能自己输到自己噢)
     */
    public int drainTo(Collection<? super E> c) {
        checkNotNull(c);
        if (c == this)
            throw new IllegalArgumentException();
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = takeIndex;
            int n = 0;//代表输出成功的元素数量
            int max = count;
            while (n < max) {
                c.add(MyArrayBlockingQueue.<E>cast(items[i]));
                items[i] = null; //清空自身数组
                i = inc(i);
                ++n;
            } //如果队列本省就是空的话就不会变动putIndex和tableIndex,否则全部置0
            if (n > 0) {
                count = 0;
                putIndex = 0;
                takeIndex = 0;
                notFull.signalAll();
            }
            return n;
        } finally {
            lock.unlock();
        }
    }

    public boolean hasNext() {
        return remaining > 0;
    }

    public E next() {
        //跟外部公用一个锁
        final ReentrantLock lock = MyArrayBlockingQueue.this.lock;
        lock.lock();
        try {
            if (remaining <= 0)
                throw new NoSuchElementException();
            lastRet = nextIndex;
            E x = itemAt(nextIndex);  //这里非常有意思，现在的x是比nextItem更新的值
            if (x == null) {
                x = nextItem;         //新值为空的话我就返回老值
                lastItem = null;      //确保移除上次的失败值
            } else
                lastItem = x;
            //这里进行跳过空值的操作，但是在上面操作的时候已经充分保证了没有空值插进来，为什么还要进一步预防Null呢?对这点保持疑问
            while (--remaining > 0 &&
                    (nextItem = itemAt(nextIndex = inc(nextIndex))) == null) ;
            return x;
        } finally {
            lock.unlock();
        }
    }



    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }



    private class Itr implements Iterator<E> {


        Itr() {
            final ReentrantLock lock = MyArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                lastRet = -1;
                if ((remaining = count) > 0)//如果现在一个元素还没取
                    nextItem = itemAt(nextIndex = takeIndex);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public void remove() {
            final ReentrantLock lock = MyArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                int i = lastRet;
                if (i == -1) //说明还没有调用next就直接remove
                    throw new IllegalStateException();
                lastRet = -1;
                E x = lastItem; //如果lastItem没有调用过next()的情况下有可能是null
                lastItem = null;
                //只有在lastItem还在i位置时候才进行移除操作
                if (x != null && x == items[i]) {
                    boolean removingHead = (i == takeIndex);
                    removeAt(i);
                    if (!removingHead) //如果不是移除头部nextIndex的话需要前移,但是整个数组对象是没有移动操作的
                        nextIndex = dec(nextIndex);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
