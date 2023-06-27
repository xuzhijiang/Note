package com.java.algorithm.lru;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 以下是基于 HashMap + 双向链表 的 LRU 算法实现，使get和put的时间复杂度达到O(1).对算法的解释如下：
 *
 * 1. 访问某个节点时，将其从原来的位置删除，并重新插入到链表头部。
 * 这样就能保证链表尾部(tail)存储的就是最近最久未(eldest)使用的节点，
 * 当节点数量大于缓存最大空间时就淘汰链表尾部的节点。
 *
 * 2. 为了使删除操作时间复杂度为 O(1)，就不能采用遍历的方式找到某个节点。
 * HashMap 存储着 Key 到节点的映射，通过 Key 就能以 O(1) 的时间得到节点，
 * 然后再以 O(1) 的时间来删除。
 */
public class LRUCache {

    private static class Node {
        int key;
        int val;
        Node pre;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * head侧 存放 访问时间较新(youngest)的数据，也就是距离当前时间越近
     */
    private Node head;

    /**
     * tail侧 存放访问时间较早(eldest)的数据，也就是距离当前时间越远
     */
    private Node tail;

    // map中包含的元素全都是最近使用过的节点
    private HashMap<Integer, Node> map;

    /**
     * 缓存容量
     */
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        // head和tail是两个dummy节点
        head = new Node(0, 0);
        tail = new Node(0, 0);

        // 维护head和tail的关系
        head.next = tail;
        head.pre = null;
        tail.pre = head;
        tail.next = null;
    }

    public void deleteNode (Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;

        node.pre = null;
        node.next = null;
    }

    public void addToHead(Node node) {
        node.next = head.next;
        node.next.pre = node;
        node.pre = head;
        head.next = node;
    }

    // 返回-1表示未命中缓存
    public int get(int key) {
        Node node = map.get(key);
        if (node != null) {
            int retVal = node.val;
            deleteNode(node); // 删除node的引用关系
            addToHead(node); // 把node添加到链表头部，这样就能保证链表尾部存储的就是最近最久未使用的节点
            return retVal;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (map.get(key) != null) {
            Node node = map.get(key);
            node.val = value;
            deleteNode(node);
            addToHead(node);
        } else {
            Node node = new Node(key, value);
            map.put(key, node);
            if (map.size() > capacity) {
                // 如果map的大小超过了容量限制,则把链表尾部的元素给删除了
                // 也就是删除了“最近最久未使用的节点”
                map.remove(tail.pre.key);
                deleteNode(tail.pre);
                // 把新创建的节点node添加到链表头部
                addToHead(node);
            } else {
                addToHead(node);
            }
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);
        printCache(cache);

        cache.put(6, 6);
        printCache(cache);

        cache.get(4); // 访问4
        printCache(cache);
    }

    public static void printCache(LRUCache lruCache) {
        System.out.println("BEGIN **************************");
        Node node = lruCache.head.next;
        while (node != null && node != lruCache.tail) {
            System.out.print(node.val);
            node = node.next;
        }
        System.out.println();
        System.out.println("END **************************");
    }
}
