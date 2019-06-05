package com.java.datastructure.compare;

public interface Comparator<T> {

    int compare(T o1, T o2);

    /**
     * @see Object#equals(Object)
     * @see Object#hashCode()
     */
    boolean equals(Object obj);

    /**
     * 逆序排序
     *
     * @since 1.8
     */
    default java.util.Comparator<T> reversed() {
        //return Collections.reverseOrder(this);
        return null;
    }

}
