package org.java.core.base.collection.list.CopyOnWrite;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @since 1.5
 */
public class MyCopyOnWriteArrayList<E> {

    private static final long serialVersionUID = 8673264195747942595L;

    final transient ReentrantLock lock = new ReentrantLock();

    /** 一个对象数组，只从方法getArray/setArray方法访问 */
    private transient volatile Object[] array;

    final Object[] getArray() {
        return array;
    }

    final void setArray(Object[] a) {
        array = a;
    }

    /**
     * Creates an empty list.
     * 在无参构造方法中会调用setArray方法，参数是一个空的对象数组，
     * 然后通过setArray把这个空的数组赋值给属性array
     */
    public MyCopyOnWriteArrayList() {
        setArray(new Object[0]);
    }

    public int size() {
        return getArray().length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // 添加一个新元素到list的尾部
    // 从add方法中我们可以看到所谓的CopyOnWrite(在写的时候复制)是如何实现的，
    // 在需要修改的时候，复制一个新数组，在新数组上修改，修改结束取代老数组，这样保证了修改操作不影响老数组的正常读取，
    // 另修改操作是加锁的，也就是说没有了线程不安全的问题。
    //
    // 和ArrayList相比较，效率比较低，只添加一个元素的情况下（初始容量均为0），用时是ArrayList的5倍左右，
    // 但是随着CopyOnWriteArrayList中元素的增加，CopyOnWriteArrayList的修改代价将越来越昂贵。
    //
    // 除了添加其他的修改操作也都是这样的套路,如remove，也是加锁，复制新数组。
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            // getArray获取的就是老数组
            Object[] elements = getArray();
            int len = elements.length;
            //这里是重点 在这里 复制老数组得到了一个长度+1的新数组
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            // 添加元素
            newElements[len] = e;
            //用新数组取代老数组
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public E remove(int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            E oldValue = get(elements, index);
            int numMoved = len - index - 1;
            if (numMoved == 0)
                setArray(Arrays.copyOf(elements, len - 1));
            else {
                Object[] newElements = new Object[len - 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index + 1, newElements, index,
                        numMoved);
                setArray(newElements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }

    private E get(Object[] elements, int index) {
        return (E) elements[index];
    }

}

