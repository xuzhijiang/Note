package com.java.datastructure.map;

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
 */
public class HashMap8 {

//    对比1.7中的常量，我们就会发现1.8中做了如下的改变。
//    增加了TREEIFY_THRESHOLD，当链表的长度超过这个值的时候，就会将链表转换红黑树。
//    Entry修改为Node，虽然Node的核心也是key、value、next。

//    /** 默认的初始容量16 */
//    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
//
//    /** 最大的容量 */
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    /** 默认的填充因子 */
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//
//    /** 当桶上的节点数量大于8时，会将链表转为红黑树 */
//    static final int TREEIFY_THRESHOLD = 8;
//
//    /** 当桶上的节点数量小于6时，会将红黑树转为链表 */
//    static final int UNTREEIFY_THRESHOLD = 6;
//
//    /**桶中的结构转为红黑树对应的最小数组大小为64 */
//    static final int MIN_TREEIFY_CAPACITY = 64;
//
//    /** 存储元素的数组，总是2的幂次倍 */
//    transient Node<K,V>[] table;
//
//    /** 存放具体元素的集合 */
//    transient Set<Map.Entry<K,V>> entrySet;
//
//    /** 存放元素的个数，注意的是这个值不等于数组的长度 */
//    transient int size;
//
//    /** 每次扩容或者更改map结构的计数器 */
//    transient int modCount;
//
//    /** 临界值，当实际大小（容量 * 负载因子）超过临界值的时候，就会进行扩容操作 */
//    int threshold;
//
//    /** 负载因子 */
//    final float loadFactor;
//
//
//    static class Node<K,V> implements Map.Entry<K,V> {
//        final int hash; // 哈希值
//        final K key; // key
//        V value; // value
//        Node<K,V> next; // 指向下一个节点
//    }
//
//    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
//        TreeNode<K,V> parent;  // 父
//        TreeNode<K,V> left;    // 左
//        TreeNode<K,V> right;   // 右
//        TreeNode<K,V> prev;
//        boolean red;           // 判断颜色
//    }
//
//    // 参考,1.8的HashMap_put示意图.png
//    public V put(K key, V value) {
//        return putVal(hash(key), key, value, false, true);
//    }
//
//    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
//                   boolean evict) {
//        Node<K,V>[] tab; Node<K,V> p; int n, i;
//        /** 判断当前桶是否为空，空的就需要初始化（resize 中会判断是否进行初始化） */
//        if ((tab = table) == null || (n = tab.length) == 0)
//            n = (tab = resize()).length;
//        /** 根据当前 key 的 hashcode 定位到具体的桶中并判断是否为空，
//         * 为空表明没有 Hash 冲突就直接在当前位置创建一个新桶即可。
//         */
//        if ((p = tab[i = (n - 1) & hash]) == null)
//            tab[i] = newNode(hash, key, value, null);
//        else {
//            Node<K,V> e; K k;
//            /** 如果当前桶有值（ Hash 冲突），
//             * 那么就要比较当前桶中的 key、key 的 hashcode 与写入的 key 是否相等，相等就赋值给 e
//             */
//            if (p.hash == hash &&
//                    ((k = p.key) == key || (key != null && key.equals(k))))
//                e = p;
//            /** 如果当前桶为红黑树，那就要按照红黑树的方式写入数据*/
//            else if (p instanceof TreeNode)
//                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
//            else {
//                /** 如果是个链表，就需要将当前的 key、value 封装成一个新节点写入到当前桶的后面（形成链表）*/
//                for (int binCount = 0; ; ++binCount) {
//                    if ((e = p.next) == null) {
//                        p.next = newNode(hash, key, value, null);
//                        /** 接着判断当前链表的大小是否大于预设的阈值，大于时就要转换为红黑树 */
//                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
//                            treeifyBin(tab, hash);
//                        break;
//                    }
//                    /** 如果在遍历过程中找到 key 相同时直接退出遍历 */
//                    if (e.hash == hash &&
//                            ((k = e.key) == key || (key != null && key.equals(k))))
//                        break;
//                    p = e;
//                }
//            }
//            /** 如果 e != null 就相当于存在相同的 key,那就需要将值覆盖 */
//            if (e != null) { // existing mapping for key
//                V oldValue = e.value;
//                if (!onlyIfAbsent || oldValue == null)
//                    e.value = value;
//                afterNodeAccess(e);
//                return oldValue;
//            }
//        }
//        ++modCount;
//        /** 最后判断是否需要进行扩容 */
//        if (++size > threshold)
//            resize();
//        afterNodeInsertion(evict);
//        return null;
//    }
//
//    public V get(Object key) {
//        Node<K,V> e;
//        /** 首先将 key hash 之后取得所定位的桶 */
//        return (e = getNode(hash(key), key)) == null ? null : e.value;
//    }
//    final Node<K,V> getNode(int hash, Object key) {
//        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
//        /** 如果桶为空则直接返回 null  */
//        if ((tab = table) != null && (n = tab.length) > 0 &&
//                (first = tab[(n - 1) & hash]) != null) {
//            /** 否则判断桶的第一个位置(有可能是链表、红黑树)的 key 是否为查询的 key，是就直接返回 value */
//            if (first.hash == hash && // always check first node
//                    ((k = first.key) == key || (key != null && key.equals(k))))
//                return first;
//            /** 如果第一个不匹配，则判断它的下一个是红黑树还是链表 */
//            if ((e = first.next) != null) {
//                /** 红黑树就按照树的查找方式返回值 */
//                if (first instanceof TreeNode)
//                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
//                /** 链表就遍历匹配返回值 */
//                do {
//                    if (e.hash == hash &&
//                            ((k = e.key) == key || (key != null && key.equals(k))))
//                        return e;
//                } while ((e = e.next) != null);
//            }
//        }
//        return null;
//    }
}
