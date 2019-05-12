package com.java.datastructure.map;

import java.util.*;

/**
 * jdk1.8实现原理简单分析
 *
 * 在jdk1.7中HashMap实现原理分析，我们知道当hash冲突很严重的时候，链表的长度就会很长，
 * 我们也知道数组和链表的优缺点，简单总结一下：
 *
 * 数组：数组查找效率高，插入和删除效率比较低。而且需要连续内存.空间复杂度较高
 *
 * 链表：存储空间零散，存储内存可以不连续，故空间复杂度较低，
 * 链表查找效率低，插入和删除效率高
 *
 * 所以，在jdk1.8中，对HashMap的实现做了相应的修改，jdk1.8 以后在解决哈希冲突时有了较大的变化，
 * 当链表长度大于阈值（默认为 8）时，将链表转化为红黑树，以减少查找时间。
 *
 * 从源码中，可以知道jdk1.8之后，对HashMap的实现做了改变，主要在于将链表的长度超过临界值的时候，
 * 就将链表转为红黑树，利用红黑树的优点，可以更好的查找元素，使得查询的时间复杂度变为O(logn)
 *
 * 但是，jdk1.8并未有修改HashMap之前的线程安全问题，我们都知道HashMap是线程不安全的，
 * 涉及到线程安全的时候，我们应该使用ConcurrentHashMap，有关ConcurrentHashMap的知识将在下一片博客中学习，
 * 这里简单的分析一下，为什么HashMap会造成线程不安全？
 *
 * 多线程不能使用HashMap，要使用CurrentHashMap
 *
 * HashMap注意的地方:
 * HashMap底层是个哈希表，使用拉链法解决冲突
 * HashMap内部存储的数据是无序的，这是因为HashMap内部的数组的下表是根据hash值算出来的
 * HashMap允许key为null
 * HashMap不是一个线程安全的类
 */
public class HashMap8<K, V> {

//    对比1.7中的常量，我们就会发现1.8中做了如下的改变。
//    增加了TREEIFY_THRESHOLD，当链表的长度超过这个值的时候，就会将链表转换红黑树。
//    Entry修改为Node，虽然Node的核心也是key、value、next。

    /** 默认的初始容量16 */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /** 最大的容量 */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /** 默认的填充因子 */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** 当桶上的节点数量大于8时，会将链表转为红黑树 */
    static final int TREEIFY_THRESHOLD = 8;

    /** 当桶上的节点数量小于6时，会将红黑树转为链表 */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**桶中的结构转为红黑树对应的最小数组大小为64 */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /** 存储元素的数组(哈希表数组)，总是2的幂次倍 */
    transient Node<K,V>[] table;

    /** 存放具体元素的集合 */
    transient Set<Map.Entry<K,V>> entrySet;

    /** 存放元素的个数(键值对个数)，注意的是这个值不等于数组的长度 */
    transient int size;

    /** 每次扩容或者更改map结构的计数器 */
    transient int modCount;

    /** 临界值，当实际大小（容量 * 负载因子）超过临界值的时候，就会进行扩容操作 */
    // 阀值(等于容量 * 加载因子)。默认值为12(16(默认容量) * 0.75(默认加载因子))。
    // 当哈希表中的键值对个数超过该值时，会进行扩容
    int threshold;

    /** 加载因子，默认是0.75 */
    final float loadFactor;

    /**
     * 有2个重要的特性影响着HashMap的性能，分别是capacity(容量)和load factor(加载因子)。
     *
     * 其中capacity表示哈希表bucket的数量，HashMap的默认值是16。load factor加载因子表示
     * 当一个map的实际大小达到了bucket的这个比例之后，和ArrayList一样，
     * 将会创建原来HashMap大小的两倍的bucket数组，来重新调整map的大小，
     * 并将原来的对象放入新的bucket数组中。这个过程也叫做再哈希。默认的load factor为0.75 。
     */

    public HashMap8(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    public HashMap8(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap8() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    // 红黑树节点
    static final class TreeNode<K,V> extends Node{
        TreeNode<K,V> parent;  // 父
        TreeNode<K,V> left;    // 左
        TreeNode<K,V> right;   // 右
        TreeNode<K,V> prev;
        boolean red;           // 判断颜色

        TreeNode(int hash, Object key, Object value, Node next) {
            super(hash, key, value, next);
        }

        public Node<K,V> putTreeVal(HashMap8<K, V> kvHashMap8, Node<K, V>[] tab,
                                    int hash, K key, V value) {
            return null;
        }

        public Node<K,V> getTreeNode(int hash, Object key) {
            return null;
        }

        public void split(HashMap8<K, V> kvHashMap8, Node<K, V>[] newTab, int j, int oldCap) {
        }
    }

    // 参考,1.8的HashMap_put示意图.png
    public V put(K key, V value) {
        // 第一个参数就是关键字key的哈希值
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        /** 判断当前桶是否为空，空的就需要初始化（resize 中会判断是否进行初始化） */
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;// 哈希表是空的话，重新构建，进行扩容

        /** 根据当前 key 的 hashcode 定位到具体的桶,并判断是否为空，
         * 为空表明没有 Hash 冲突就直接在当前位置创建一个新桶即可。
         */
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 没有hash冲突的话，直接在对应位置上构造一个新的节点即可
            tab[i] = newNode(hash, key, value, null);
        else { // 如果哈希表当前位置上已经有节点的话，说明有hash冲突
            Node<K,V> e; K k;
            // 关键字跟哈希表上的首个节点济宁比较
            /** 如果当前桶有值（ Hash 冲突），
             * 那么就要比较当前桶中的(key,以及key的hashcode) 与写入的 key 是否相等，相等就赋值给 e
             */
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            // 如果使用的是红黑树，用红黑树的方式进行处理
            /** 如果当前桶为红黑树，那就要按照红黑树的方式写入数据*/
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {// 跟链表进行比较
                /** 如果是个链表，就需要将当前的 key、value 封装成一个新节点写入到
                 * 当前桶的后面（形成链表）*/
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {// 一直遍历链表，直到找到最后一个
                        p.next = newNode(hash, key, value, null); // 构造链表上的新节点
                        /** 接着判断当前链表的大小是否大于预设的阈值，大于时就要转换为红黑树 */
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            //treeifyBin(tab, hash);
                        break;
                    }
                    /** 如果在遍历过程中找到 key 相同时直接退出遍历 */
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            // 如果找到了节点，说明关键字相同，进行覆盖操作，直接返回旧的关键字的值
            /** 如果 e != null 就相当于存在相同的 key,那就需要将值覆盖 */
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                //afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        /** 最后判断是否需要进行扩容 */
        if (++size > threshold) // 如果目前键值对个数已经超过阀值，重新构建
            resize();
        //afterNodeInsertion(evict);// 节点插入以后的钩子方法
        return null;
    }

    // get操作关键点就是怎么在哈希表上取数据，理解了put操作之后，get方法很容易理解了：
    public V get(Object key) {
        Node<K,V> e;
        /** 首先将 key hash 之后取得所定位的桶 */
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    // getNode方法就说明了如何取数据：
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        /** 如果桶为空则直接返回 null  */
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {// 如果哈希表容量为0或者关键字没有命中，直接返回null
            /** 否则判断桶的第一个位置(有可能是链表、红黑树)的 key 是否为查询的 key，是就直接返回 value */
            if (first.hash == hash && // always check first node, // 关键字命中的话比较第一个节点
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            /** 如果第一个不匹配，则判断它的下一个是红黑树还是链表 */
            if ((e = first.next) != null) {
                /** 红黑树就按照树的查找方式返回值 */
                if (first instanceof TreeNode)// 以红黑树的方式查找
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                /** 链表就遍历匹配返回值 */
                do {// 遍历链表查找
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    HashMap8.Node<K,V> newNode(int hash, K key, V value, HashMap8.Node<K,V> next) {
        return new HashMap8.Node<>(hash, key, value, next);
    }

    // hash过程在HashMap里就是一个hash方法：
    static final int hash(Object key) {
        int h;
        // 使用hashCode的值和hashCode的值无符号右移16位做异或操作
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        // 这段代码是什么意思呢？ 我们以文中的那个demo为例，
        // 说明”java”这个关键字是如何找到对应bucket的过程。
        // https://raw.githubusercontent.com/fangjian0423/blogimages/master/images/hashmap06.jpg

        //从上图可以看到，hash方法得到的hash值是根据关键字的hashCode的高16位和
        // 低16位进行异或操作得到的一个值(也就是hash方法的返回值)。
        // 这个值再与哈希表容量-1值进行与操作得到最终的bucket索引值: (n - 1) & hash

        // hashCode的高16位与低16位进行异或操作主要是设计者想了一个
        // 顾全大局的方法(综合考虑了速度、作用、质量)来做的。

        // 如果链表的数量大了，HashMap会把哈希表转换成红黑树来进行处理，本文不讨论这部分内容。
        //
        //现在回过头来看例子，为什么初始化了一个容量为5的HashMap，但是哈希表的容量为8，而且阀值为6？
        //
        //因为HashMap的构造函数初始化threshold的时候调用了tableSizeFor方法，
        // 这个方法会把容量改成2的幂的整数，主要是为了哈希表散列更均匀。
        //
        //// 定位bucket索引的最后操作。如果n为奇数，n-1就是偶数，偶数的话转成二进制
        // 最后一位是0，相反如果是奇数，最后一位是1，这样产生的索引值将更均匀
        // (n - 1) & hash


    }

    static class Node<K,V> implements Map.Entry<K,V> {
        // 哈希值
        final int hash;
        final K key;
        V value;
        HashMap8.Node<K,V> next;

        Node(int hash, K key, V value, HashMap8.Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    // 哈希表扩容是使用resize方法完成：
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) { // 如果老容量大于0，说明哈希表中已经有数据了，然后进行扩容
            if (oldCap >= MAXIMUM_CAPACITY) { // 超过最大容量的话，不扩容
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&// 容量加倍
                    oldCap >= DEFAULT_INITIAL_CAPACITY) // 如果老的容量超过默认容量的话
                newThr = oldThr << 1; // double threshold// 阀值加倍
        }
        else if (oldThr > 0) // initial capacity was placed in threshold// 根据thresold初始化数组
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults  // 使用默认配置
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) { // 扩容之后进行rehash操作
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;// 单节点扩容
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);// 红黑树方式处理
                    else { // preserve order// 链表扩容
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
    
}
