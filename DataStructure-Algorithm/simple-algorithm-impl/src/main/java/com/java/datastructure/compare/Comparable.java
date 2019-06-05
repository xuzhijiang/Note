package com.java.datastructure.compare;

public interface Comparable<T> {

    /**
     * @param o 要比较的对象
     * @return 负数: 这个对象小于传入的对象
     *  0: 这个对象等于传入的对象
     *  正数: 这个对象大于传入的对象
     */
    public int compareTo(T o);
}
