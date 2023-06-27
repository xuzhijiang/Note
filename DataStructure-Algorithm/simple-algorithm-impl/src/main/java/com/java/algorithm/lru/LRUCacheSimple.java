package com.java.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 一个更简单实用的LRUCache方案
 *
 * 使用LinkedHashMap即可实现
 *
 * 1. LinkedHashMap提供了按照访问顺序排序的方案，内部也是使用HashMap+双向链表(LinkedList)
 * 2. 只需要重写removeEldestEntry方法，当该方法返回true时，LinkedHashMap会删除最旧的结点。
 */
public class LRUCacheSimple {

    private LinkedHashMap<Integer, Integer> map;

    private final int capacity;

    public LRUCacheSimple(int capacity) {
        this.capacity = capacity;

        map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {

            // 当该方法返回true时，LinkedHashMap会删除最旧的结点,移除的是链表首部的节点head.
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return map.getOrDefault(key, -1);
    }

    public void put(int key,int value) {
        map.put(key, value);
    }

    public static void main(String[] args) {
        LRUCacheSimple cacheSimple = new LRUCacheSimple(2);
        cacheSimple.put(1, 1);
        cacheSimple.put(2, 2);
        /**
         * 将访问的节点移到链表尾部,在每次访问一个节点时，会将这个节点移到链表尾部，
         * 保证链表尾部是最近访问的节点，那么链表首部就是最近最久未使用(eldest)的节点
         */
        //System.out.println(cacheSimple.get(2));// 2
        //System.out.println(cacheSimple.get(1));// 1
        // 因为accessOrder为true，2最老，所以删除的时候最先被删除
        cacheSimple.put(3, 3);
        System.out.println(cacheSimple.get(2));// -1，因为最老，已经被删除
        System.out.println(cacheSimple.get(1));// 1
        cacheSimple.put(4, 4);// 删除最老的1
        System.out.println(cacheSimple.get(3));//-1
        System.out.println(cacheSimple.get(1));//1
        System.out.println(cacheSimple.get(3));//-1
        System.out.println(cacheSimple.get(4));
    }

}
