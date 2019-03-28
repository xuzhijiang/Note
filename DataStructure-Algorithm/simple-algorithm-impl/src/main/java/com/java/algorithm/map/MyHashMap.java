package com.java.algorithm.map;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements MyMap<K, V> {

    // 默认数组大小，初始大小为16
    private static int defaultLength = 16;

    // 默认负载因子，为0.75
    private static double defaultLoader = 0.75;

    // Entry数组
    private Entry<K, V>[] table = null;

    // HashMap中item的数量
    private int size = 0;

    /**
     * 使用默认值初始化
     */
    public MyHashMap() {
        this(defaultLength, defaultLoader);
    }

    /**
     * 自定义默认长度和负载因子
     *
     * @param length
     * @param loader
     */
    public MyHashMap(int length, double loader) {
        defaultLength = length;
        defaultLoader = loader;
        table = new Entry[defaultLength];
    }

    @Override
    public V get(K k) {
        //获取此key对应的entry对象所存放的索引index
        int index = getKey(k);
        //非空判断
        if (table[index] == null) {
            return null;
        }
        //调用方法找到真正的value值并返回。
        return findValueByEqualKey(k, table[index]);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V put(K k, V v) {
        //判断size是否达到扩容的标准
        if (size >= defaultLength * defaultLoader) {
            expand();
        }
        //根据key和哈希算法算出数组下标
        int index = getKey(k);
        Entry<K, V> entry = table[index];
        //判断entry是否为空
        if (entry == null) {
            /*
             * 如果entry为空，则代表当前位置没有数据。
             * new一个entry对象，内部存放key，value。
             * 此时next指针没有值，因为这个位置上只有一个entry对象
             * */
            table[index] = new Entry(k, v, null);
            size++;
        } else {
            /*
             * 如果entry不为空，则代表当前位置已经有数据了
             * new一个entry对象，内部存放key，value。
             * 把next指针设置为原本的entry对象并且把数组中的数据替换为新的entry对象
             * */
            table[index] = new Entry<K, V>(k, v, entry);
        }
        return table[index].getValue();
    }

    /**
     * 自定义哈希算法
     * 根据key的哈希值得到一个index索引，即存放到数组中的下标
     * @param k
     * @return
     */
    private int getKey(K k) {
        int m = defaultLength;
        int index = k.hashCode() % m;
        return index >= 0 ? index : -index;
    }

    /**
     * 数组的扩容
     */
    private void expand() {
        //创建一个大小是原来两倍的entry数组
        Entry<K, V>[] newTable = new Entry[2 * defaultLength];
        //重新散列
        rehash(newTable);
    }

    /**
     * 重新散列的过程
     * @param newTable
     */
    private void rehash(Entry<K, V>[] newTable) {
        //创建一个list用于装载HashMap中所有的entry对象
        List<Entry<K, V>> list = new ArrayList<Entry<K, V>>();
        //遍历整个数组
        for (int i = 0; i < table.length; i++) {
            //如果数组中的某个位置没有数据，则跳过
            if (table[i] == null) {
                continue;
            }
            //通过递归的方式将所有的entry对象装载到list中
            findEntryByNext(table[i], list);
            if (list.size() > 0) {
                //把size重置
                size = 0;
                //把默认长度设置为原来的两倍
                defaultLength = 2 * defaultLength;
                table = newTable;
                for (Entry<K, V> entry : list) {
                    if (entry.next != null) {
                        //把所有entry的next指针置空
                        entry.next = null;
                    }
                    //对新table进行散列
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void findEntryByNext(Entry<K, V> entry, List<Entry<K, V>> list) {
        if (entry != null && entry.next != null) {
            list.add(entry);
            //递归调用
            findEntryByNext(entry.next, list);
        } else {
            list.add(entry);
        }
    }

    /**
     * 通过递归比较key值的方式找到真正我们要找的value值
     *
     * @param k
     * @param entry
     * @return
     */
    public V findValueByEqualKey(K k, Entry<K, V> entry) {
        /*
         * 如果传进来的key等于这个entry的key值，说明这个就是我们要找的entry对象
         * 那么直接返回这个entry的value
         * */
        if (k == entry.getKey() || k.equals(entry.getKey())) {
            return entry.getValue();
        } else {
            /*
             * 如果不相等，说明这个不是我们要找的entry对象，
             * 通过递归的方式去比较它的next指针中的entry的key值，来找到真正的entry对象
             * */
            if (entry.next != null) {
                return findValueByEqualKey(k, entry.next);
            }
        }
        return entry.getValue();
    }

    /**
     * 内部类Entry来实现MyMap的内部接口Entry.
     *
     * @param <K>
     * @param <V>
     */
    class Entry<K, V> implements MyMap.Entry<K, V> {

        // 存放着每个条目的key
        K k;

        // 存放着每个条目的value
        V v;

        // 指向下一个条目
        Entry<K, V> next;

        public Entry(K k, V v, Entry next) {
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
