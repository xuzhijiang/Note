package com.java.algorithm.map;

/**
 * 类比于JDK的顶层接口java.util.Map。
 * @param <K>
 * @param <V>
 */
public interface MyMap<K, V> {

    V put(K k, V v);

    V get(K k);

    int size();

    /**
     * Entry内部接口
     * @param <K>
     * @param <V>
     */
    interface Entry<K, V> {

        /**
         * 根据entry对象获取key
         * @return
         */
        K getKey();

        /**
         * 根据entry对象获取value
         * @return
         */
        V getValue();
    }

}
