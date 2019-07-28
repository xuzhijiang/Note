package com.java.datastructure.map.custom;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿照1.7的 HashMap 自定义HashMap
 */
public class MyHashMap7<K, V> implements MyMap<K, V> {

    // 数组默认的大小
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**  数组(桶)的最大值 */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    // 默认负载因子，为0.75
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // Entry数组
    private Node<K, V>[] table = null;

    /** 扩容的阈值，等于容量*loadFactor */
    int threshold;

    /** 负载因子，可以在初始化的时候显示指定 */
    final float loadFactor;

    // 已经存放到HashMap中的元素的数量
    transient int size = 0;

    public MyHashMap7() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap7(int initialCapacity, float loadFactor) {
        this.threshold = (int) (initialCapacity * loadFactor);
        this.loadFactor = loadFactor;
        this.table = new Node[initialCapacity];
    }

    @Override
    public V get(K k) {
        // 使用key,通过哈希(hash)算法计算在数组中的位置(获取此key对应的entry对象所存放的索引index)
        // 注意HashMap中key是不允许重复的，但是key的hash值可能计算出的index一样.
        int index = hash(k);

        // 如果桶的第一个Entry为null，意味着不存在key对应的value.直接返回null
        if (table[index] == null) {
            return null;
        }

        return findValueByEquals(k, table[index]);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 通过哈希算法实现散列的过程，那么什么是散列呢？散列(分散的列开)即把数据均匀的存放到数组中的各个位置，
     * 从而尽量避免出现多个数据存放在一块区间内.
     */
    @Override
    public V put(K k, V v) {
        // 判断是否达到扩容的标准: HashMap的扩容机制是：当map存放的元素的数量大于等于 (容量*负载因子),则需要扩容.
        if (size >= threshold) {
            expand();
        }

        // 我们需要通过哈希算法得到数组的下标，然后把一个包含键值对以及next指针的entry对象存到该位置中
        int index = hash(k);
        Node<K, V> headEntry = table[index];
        if (headEntry == null) { // 判断headEntry是否为空
            // 如果entry为空，则代表当前位置没有数据。new一个Node对象，
            // 内部存放key，value。此时next指针没有值，因为这个位置上只有一个entry对象
            table[index] = new Node(k, v, null);
        } else {
            // 如果entry不为空，则代表当前位置已经有数据了, new一个entry对象，内部存放key，value。
            // 把next指针设置为原来的headEntry对象,并且把数组中的数据替换为新的entry对象
            table[index] = new Node<>(k, v, headEntry);
        }
        size++;
        return table[index].getValue();
    }

    /**
     * 哈希算法有很多种(优秀的哈希算法应该具备以下两点：保证散列值非常均匀,保证冲突极少出现)，
     * 在这里 取模的哈希算法 为例
     *
     * 由于这种算法会导致这样的情况出现，即不同的key经过哈希运算之后得到了一样的index，
     * 如key为2和15的index值都为2，那么此时就需要我们处理冲突了。
     *
     * 用拉链法解决冲突: 把原先存放在这个位置的数据取出来，然后在这个位置存放新的数据，并且把新数据的next指针设为原数据，
     * 也就是说链表头位置的数据永远是最新的数据。
     */
    private int hash(K k) {
        // m的取值规则是比数组长度小的最大质数。在这里数组长度为16，m为13。
        int m = table.length;
        int index = k.hashCode() % m;
        // 最后返回的时候用了一个三元运算符，是为了要确保index的值必须是一个正数
        return index >= 0 ? index : -index;
    }

    /**
     * 扩容涉及到数据的复制，rehash等，消耗性能
     */
    private void expand() {
        // 创建一个大小是原来两倍的数组
        Node<K, V>[] newTable = new Node[(table.length << 1)];
        // 重新散列
        rehash(newTable);
    }

    /**
     * 重新散列的过程(数组中的数据会重新散列然后再存放)
     */
    private void rehash(Node<K, V>[] newTable) {
        //创建一个list用于装载HashMap中所有的entry对象
        List<Node<K, V>> list = new ArrayList<>();

        //遍历整个数组
        for (int i = 0; i < table.length; i++) {
            // 如果数组中的某个位置没有数据，则跳过
            if (table[i] == null) {
                continue;
            }
            // 通过递归的方式将所有的entry对象装载到list中
            packEntryToList(table[i], list);
        }

        if (list.size() > 0) {
            // 把size重置
            size = 0;
            // 桶的容量变化，阈值也要变化
            threshold = (int) (newTable.length * loadFactor);
            table = newTable;
            for (Node<K, V> entry : list) {
                if (entry.next != null) {
                    // 把所有entry的next指针置空
                    entry.next = null;
                }
                // 对新table进行散列,也就是重新put,对应的size也会从0开始变化.所以上面将size置为了0
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    private void packEntryToList(Node<K, V> entry, List<Node<K, V>> list) {
        Node<K, V> currentEntry = entry;
        while (true) {
            list.add(currentEntry);
            currentEntry = currentEntry.next;
            if (currentEntry == null) {
                return;
            }
        }
    }

    /**
     * 比较key值的方式找到真正要找的entry,然后返回value
     */
    public V findValueByEquals(K k, Node<K, V> headEntry) {
        Node<K, V> currentEntry = headEntry;
        while (true) {
            // 找到key所对应的entry
            if (k == currentEntry.getKey() || k.equals(currentEntry.getKey())) {
                break;
            }
            currentEntry = currentEntry.next;
            // 没有找到key所对应的entry
            if (currentEntry == null) {
                return null;
            }
        }
        return currentEntry.getValue();
    }

    /**
     * 静态内部类Node实现了MyMap的内部接口Entry.
     */
    static class Node<K, V> implements MyMap.Entry<K, V> {

        // 存放着每个条目的key
        K k;

        // 存放着每个条目的value
        V v;

        // 指向下一个条目
        Node<K, V> next;

        public Node(K k, V v, Node next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }
    }

}
