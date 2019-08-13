package org.java.core.base.multithreading.TheadLocalExample;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal源码解析
 */
public class MyThreadLocal<T> extends ThreadLocal<T>{

    private final int threadLocalHashCode = nextHashCode();

    private static AtomicInteger nextHashCode = new AtomicInteger();

    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    protected T initialValue() {
        return null;
    }

    public MyThreadLocal() { }

    public T get() {
        // 得到当前线程
        Thread t = Thread.currentThread();
        // ,因为每个线程内部都持有一个ThreadLocal.ThreadLocalMap threadLocals = null;
        // 拿到当前线程的ThreadLocalMap对象
        MyThreadLocal.ThreadLocalMap map = getMap(t);
        if (map != null) {
            // 找到ThreadLocal对应的Entry
            MyThreadLocal.ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                T result = (T)e.value;
                return result;
            }
        }
        // 当前线程没有ThreadLocalMap对象的话，那么就初始化ThreadLocalMap
        return setInitialValue();
    }

    private T setInitialValue() {
        // 初始化ThreadLocalMap，默认返回null，可由子类扩展
        T value = initialValue();
        Thread t = Thread.currentThread();
        // 得到这个thread对应的hashmap
        ThreadLocalMap map = getMap(t);
        if (map != null){
            // 实例化ThreadLocalMap之后，将初始值丢入到Map中
            map.set(this, value);
        } else{
            createMap(t, value);
        }
        return value;
    }

    // 其实只是把这个value设置给了当前线程而已.
    public void set(T value) {
        // set逻辑：找到当前线程的ThreadLocalMap，找到的话，设置对应的值，否则创建ThreadLocalMap
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    public void remove() {
        ThreadLocalMap m = getMap(Thread.currentThread());
        if (m != null)
            m.remove(this);
    }

    MyThreadLocal.ThreadLocalMap getMap(Thread t) {
        //return t.threadLocals;
        return null;
    }

    /**
     * Create the map associated with a ThreadLocal. Overridden in
     * InheritableThreadLocal.
     *
     * @param t the current thread
     * @param firstValue value for the initial entry of the map
     */
    void createMap(Thread t, T firstValue) {
        // 给当前线程创建一个ThreadLocalMap
        //t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    // 类似于一个hashmap
    static class ThreadLocalMap {

        // Entry是ThreadLocalMap这个hash map中的每一项，并且Entry中的key基本上都是ThreadLocal。
        static class Entry extends WeakReference<java.lang.ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(java.lang.ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        private static final int INITIAL_CAPACITY = 16;

        private ThreadLocalMap.Entry[] table;

        private int size = 0;

        /**
         * The next size value at which to resize.
         */
        private int threshold; // Default to 0

        /**
         * Set the resize threshold to maintain at worst a 2/3 load factor.
         */
        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }

        /**
         * Increment i modulo len.
         */
        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }

        /**
         * Decrement i modulo len.
         */
        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        private ThreadLocalMap.Entry getEntry(MyThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            ThreadLocalMap.Entry e = table[i];
            return e;
        }

        /**
         * Set the value associated with key.
         *
         * @param key the thread local object
         * @param value the value to be set
         */
        private void set(MyThreadLocal<?> key, Object value) { }

        /**
         * Remove the entry for key.
         */
        private void remove(java.lang.ThreadLocal<?> key) { }

    }
}

