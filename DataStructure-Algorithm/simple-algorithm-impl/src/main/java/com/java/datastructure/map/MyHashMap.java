package com.java.datastructure.map;

import java.util.ArrayList;
import java.util.List;

/**
 * 模仿JDK的HashMap，自定义自己的HashMap
 */
public class MyHashMap<K, V> implements MyMap<K, V> {

    // 初始化桶大小，HashMap底层是数组，这个是数组默认的大小
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**  桶的最大值 */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    // 默认负载因子，为0.75
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // Entry数组
    private Entry<K, V>[] table = null;

    /** 桶大小。可以在初始化的时候显示指定 */
    int threshold;

    /** 负载因子，可以在初始化的时候显示指定 */
    // 用来控制数组存放数据的疏密程度，loadFactor太大(loadFactor越趋紧与1越大)导致查找元素效率低，链表的长度就越长。
    // 太小导致数组的利用率低，存放的数据会很分散
    // 所以，建议当我们知道HashMap的使用大小时，应该在初始化的时候指定大小，减少扩容带来的性能消耗。
    // loadFactor的默认值为0.75f是官方给出的一个比较好的临界值。
    final float loadFactor;

    // HashMap中item的数量
    private int size = 0;

    /**
     * 使用默认值初始化
     */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 自定义默认长度和负载因子
     *
     * @param length
     * @param loaderFactor
     */
    public MyHashMap(int length, float loaderFactor) {
        threshold = length;
        loadFactor = loaderFactor;
        table = new Entry[length];
    }

    /**
     * 由于hash算法可能会导致相同的索引中包含了不同的entry对象，
     * 我们需要通过对比key值的方式来找到我们真正要的那个entry对象
     * @param k
     * @return
     */
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
        // 判断size是否达到扩容的标准
        // HashMap的扩容机制是：当map的大小大于默认长度*默认负载因子,则需要扩容.
        System.out.println("size: " + size);
        System.out.println("threshold * loadFactor: " + threshold * loadFactor);
        if (size >= threshold * loadFactor) {
            System.out.println("expand---------" + table.length);
            expand();
        }

        // 哈希算法又被称为散列的过程，那么什么是散列呢？散列(分散的列开)即把数据均匀的存放到数组中的各个位置，
        // 从而尽量避免出现多个数据存放在一块区间内.
        // 在HashMap中，哈希算法主要是用于根据key的值算出存放数组的index值
        // 优秀的哈希算法应该具备以下两点：保证散列值非常均匀,保证冲突极少出现

        // ，我们需要通过哈希算法得到数组的下标，然后把一个包含键值对以及next指针的entry对象存到该位置中
        int index = getKey(k);
        System.out.println("-------index: " + index);
        System.out.println("table capacity: " + table.length);
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
     *
     * 哈希函数有很多种，在这里以除留取余法（取模）为例，首先定义一个数组的长度，假设为16。
     * 那么此时的索引index为index = key.hashCode() % m，m的取值规则是比数组长度小的最大质数。
     * 在这个情况下m为13。由于这种算法会导致这样的情况出现，即不同的key经过哈希运算之后得到了一样的index，
     * 如key为2和15的index值都为2，那么此时就需要我们处理冲突了。
     *
     * 处理冲突:
     *
     * 1. 线性探测法: 当冲突产生时，查找下一个索引是否被占用，如果没有，则把数据存到该索引上。
     *
     * 2. 链表形式: 由于在HashMap中，单个数据是以entry的形式存储的，而entry中包含了key，value和next指针。
     * 那么当冲突产生时，我们就把原先存放到这个位置的数据取出来，然后在这个位置存放新的数据，并且把新数据的next指针设为原数据，
     * 也就是说链表头位置的数据永远是最新的数据。
     * @param k
     * @return
     */
    private int getKey(K k) {
        int m = threshold;
        System.out.println("mmm: " + m);
        int index = k.hashCode() % m;
        // 最后返回的时候用了一个三元运算符，是为了要确保index的值必须是一个正数
        return index >= 0 ? index : -index;
    }

    /**
     * 扩容就涉及到数据的复制，rehash等，就消耗性能
     *
     * 数组的扩容,HashMap的扩容机制是：当map的大小大于默认长度*默认负载因子，那么数组的长度会翻倍，
     * 数组中的数据会重新散列然后再存放。那么在原先的put方法中，需要先判断是否达到扩容的标准再执行下面的代码。
     * 如果达到扩容的标准则需要调用扩容方法
     */
    private void expand() {
        //创建一个大小是原来两倍的entry数组
        Entry<K, V>[] newTable = new Entry[(threshold << 1)];
        //重新散列
        rehash(newTable);
    }

    /**
     * 重新散列的过程
     * @param newTable 新的数组
     */
    private void rehash(Entry<K, V>[] newTable) {
        System.out.println("call rehash: " + table.length + "----" + newTable.length);
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
        }

        if (list.size() > 0) {
            // 把size重置
            size = 0;
            // 把默认长度设置为原来的两倍
            threshold = threshold << 1;
            System.out.println("talbe111: " + table.length);
            System.out.println("newTable22: " + newTable.length);
            table = newTable;
            System.out.println("talbe333: " + table.length);
            for (Entry<K, V> entry : list) {
                if (entry.next != null) {
                    // 把所有entry的next指针置空
                    entry.next = null;
                }
                // 对新table进行散列,也就是重新put,对应的size也会从0开始变化.所以上面将size置为了0
                put(entry.getKey(), entry.getValue());
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
