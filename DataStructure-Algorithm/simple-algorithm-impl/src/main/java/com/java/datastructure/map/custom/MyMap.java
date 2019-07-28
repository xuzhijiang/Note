package com.java.datastructure.map.custom;

public interface MyMap<K, V> {

    V put(K k, V v);

    V get(K k);

    int size();

    /**
     * Entry内部接口
     */
    interface Entry<K, V> {

        K getKey();

        V getValue();
    }

}
