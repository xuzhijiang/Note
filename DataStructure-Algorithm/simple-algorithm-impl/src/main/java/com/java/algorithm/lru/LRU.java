package com.java.algorithm.lru;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 以下是基于 HashMap + 双向链表 的 LRU 算法实现，使get和put的时间复杂度达到O(1).对算法的解释如下：
 *
 * - 访问某个节点时，将其从原来的位置删除，并重新插入到链表头部。这样就能保证链表尾部存储的就是最近最久未使用的节点，当节点数量大于缓存最大空间时就淘汰链表尾部的节点。
 *
 * - 为了使删除操作时间复杂度为 O(1)，就不能采用遍历的方式找到某个节点。
 * HashMap 存储着 Key 到节点的映射，通过 Key 就能以 O(1) 的时间得到节点，然后再以 O(1) 的时间将其从双向队列中删除。
 */
public class LRU<K, V> implements Iterable<K> {

    /**
     * 双向链表头结点,该侧的缓存项访问时间较新，也就是距离当前时间越近
     */
    private Node head;
    /**
     * 双向链表尾结点，该侧的缓存项访问时间较早，也就是距离当前时间越远
     */
    private Node tail;
    /**
     * 用于加速缓存项随机访问性能的HashMap
     */
    private HashMap<K, Node> map;
    /**
     * 缓存容量
     */
    private int maxSize;

    /**
     * 缓存项的包装类，包含键、值、前驱结点、后继结点
     */
    private class Node {
        Node pre;
        Node next;
        K k;
        V v;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    public LRU(int maxSize) {
        this.maxSize = maxSize;
        this.map = new HashMap<>(maxSize * 4 / 3);

        // 创建head和tail
        head = new Node(null, null);
        tail = new Node(null, null);
        // 维护head和tail的关系
        head.next = tail;
        tail.pre = head;
    }

    /**
     *
     * @param key
     * @return 返回null表示未命中缓存
     */
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        // 获取key所对应的node
        Node node = map.get(key);
        // 删除node的引用关系
        unlink(node);
        // 把node添加到链表头部，这样就能保证链表尾部存储的就是最近最久未使用的节点
        appendHead(node);

        return node.v;
    }

    public void put(K key, V value) {
        // 如果map已经包含了key
        if (map.containsKey(key)) {
            Node node = map.get(key);
            // 把这个node对应的引用都unlink了
            unlink(node);
        }

        // 构建新的node
        Node node = new Node(key, value);
        map.put(key, node);
        // 把新创建的节点node添加到链表头部
        appendHead(node);

        // 如果map的大小超过了容量限制,则把链表尾部的元素给删除了
        // 也就是删除了“最近最久未使用的节点”，map中包含的元素全都是最近的节点
        // 越接近链表尾部，就是越久没有被使用的节点
        if (map.size() > maxSize) {
            Node toRemove = removeTail();
            map.remove(toRemove.k);
        }
    }

    private void unlink(Node node) {
        // 获取要删除的节点的前驱和后继
        Node pre = node.pre;
        Node next = node.next;
        // 维护要删除的节点的前驱和后继的关系
        pre.next = next;
        next.pre = pre;

        // 把要删除的节点的引用给unlink掉
        node.pre = null;
        node.next = null;
    }

    private void appendHead(Node node) {
        // 维护新添加的node和head.next的关系
        node.next = head.next;
        node.next.pre = node;

        // 维护head和node的关系
        node.pre = head;
        head.next = node;
    }

    /**
     * 删除链表尾部的元素，链表尾部的元素代表的就是“最近最久未使用的节点”
     * @return
     */
    private Node removeTail() {
        // 获取要删除的节点
        Node node = tail.pre;

        // 维护要删除的节点的前驱和tail的关系
        Node pre = node.pre;
        tail.pre = pre;
        pre.next = tail;

        // 维护要删除的节点和tail的关系
        node.pre = null;
        node.next = null;

        return node;
    }

    @Override
    public Iterator<K> iterator() {

        return new Iterator<K>() {

            private Node cur = head.next;

            @Override
            public boolean hasNext() {
                return cur != tail;
            }

            @Override
            public K next() {
                Node node = cur;
                cur = cur.next;
                return node.k;
            }
        };
    }

    public static void main(String[] args) {
        LRU<Integer, Integer> cache = new LRU(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);
        printCache(cache);

        cache.put(6, 6);
        printCache(cache);
        // 访问4
        cache.get(4);
        printCache(cache);
    }

    public static void printCache(LRU lru) {
        System.out.println("----------------------");
        Iterator iterator = lru.iterator();
        while (iterator.hasNext()) {
            // 越早打印出的数据，距离当前时间越近，越新
            System.out.println(iterator.next());
        }
    }
}
