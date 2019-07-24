package com.java.datastructure.map;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * jdk8的HashMap代码摘要
 */
public class JDK8HashMapSource<K, V> {

    /**
     * 初始化桶大小，因为底层是数组，所以这是数组默认的大小
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * 桶最大值
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认的负载因子（0.75）
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 用于判断是否需要将链表转换为红黑树的阈值,
     * 链表的长度超过8时，会将链表转为红黑树
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     *  链表的长度小于6时，会将红黑树转为链表
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 桶的长度不小于64，才会将链表转化为红黑树
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    transient Node<K,V>[] table;

    /**
     * 存放键值对的集合
     */
    transient Set<Map.Entry<K, V>> entrySet;

    /**
     * 当前已经存放的元素的数量(键值对个数)，注意的是这个值不等于数组的长度
     */
    transient int size;

    /**
     * 桶大小，可在初始化时显式指定,
     */
    int threshold;

    /**
     * 负载因子
     */
    final float loadFactor;

    /**
     * 每次扩容或者更改map结构的计数器
     */
    transient int modCount;

    public JDK8HashMapSource(int initialCapacity, float loadFactor) {
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

    public JDK8HashMapSource(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public JDK8HashMapSource() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    /**
     * Returns a power of two size for the given target capacity.
     * 现在回过头来看例子，为什么初始化了一个容量为5的HashMap，但是哈希表的容量为8，而且阀值为6？
     * <p>
     * 因为HashMap的构造函数初始化threshold的时候调用了tableSizeFor方法，
     * 这个方法会把容量改成2的幂的整数，主要是为了哈希表散列更均匀。
     * <p>
     * 比如cap传入5，返回值为8,8会赋值给threshold,然后再resize()中将threshold=8赋值给容量，然后threshold会变成"容量*加载因子=6"
     * <p>
     *
     * 阀值为6是因为之后进行resize操作的时候更新了阀值:阀值 = 容量 * 加载因子 = 8 * 0.75 = 6
     */
    static final int tableSizeFor(int cap) {
        // 保证threshold为2的幂
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String[] args) {
        int result = tableSizeFor(5);
        System.out.println(result);
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

        // 现在回过头来看例子，为什么初始化了一个容量为5的HashMap，但是哈希表的容量为8，而且阀值为6？

        //因为HashMap的构造函数初始化threshold的时候调用了tableSizeFor方法，
        // 这个方法会把容量改成2的幂的整数，主要是为了哈希表散列更均匀。

        //// 定位bucket索引的最后操作。如果n为奇数，n-1就是偶数，偶数的话转成二进制
        // 最后一位是0，相反如果是奇数，最后一位是1，这样产生的索引值将更均匀 (n - 1) & hash
    }

    public V put(K key, V value) {
        // 第一个参数就是关键字key的哈希值
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;
        // 哈希表是空的话，重新构建，进行扩容
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;

        /** 根据当前 key 的 hashcode 定位到具体的桶,并判断是否为空，
         * 为空表明没有 Hash 冲突就直接在当前位置创建一个新桶即可。
         */
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 没有hash冲突的话，直接在对应位置上构造一个新的节点即可
            tab[i] = newNode(hash, key, value, null);
        else { // 如果哈希表当前位置上已经有节点的话，说明有hash冲突
            Node<K, V> e;
            K k;
            // 关键字跟哈希表上的首个节点济宁比较
            /** 如果当前桶有值（ Hash 冲突），
             * 那么就要比较当前桶中的(key,以及key的hashcode) 与写入的 key 是否相等，相等就赋值给 e
             */
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
                // 如果使用的是红黑树，用红黑树的方式进行处理
                /** 如果当前桶为红黑树，那就要按照红黑树的方式写入数据*/
            } else if (p instanceof TreeNode) {
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            } else {// 跟链表进行比较
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
        Node<K, V> e;
        /** 首先将 key hash 之后取得所定位的桶 */
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    // getNode方法就说明了如何取数据：
    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
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
                    return ((TreeNode<K, V>) first).getTreeNode(hash, key);
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

    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    // 哈希表扩容是使用resize方法完成：
    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;// 旧的table
        int oldCap = (oldTab == null) ? 0 : oldTab.length;// 旧的容量
        int oldThr = threshold;// 旧的阈值
        int newCap, newThr = 0;// 新容量和新阈值
        if (oldCap > 0) { // 如果老容量大于0，说明哈希表中已经有数据了，然后进行扩容
            if (oldCap >= MAXIMUM_CAPACITY) { // 超过最大容量的话，不扩容
                threshold = Integer.MAX_VALUE;
                return oldTab;
                // 走到else if中说明老的容量不超过最大容量MAXIMUM_CAPACITY，
                // 所以容量oldCap加倍(oldCap左移一位就相当于x2,然后赋值给newCap，
                // 然后判断新容量newCap是否小于最大容量，如果新容量也小于最大容量,
                // 并且老容量oldCap大于等于默认容量16的话，阈值也加倍)
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY) {
                newThr = oldThr << 1; // double threshold// 阀值加倍
            }
        } else if (oldThr > 0) {// initial capacity was placed in threshold// 根据threshold初始化数组
            newCap = oldThr;
        } else {// zero initial threshold signifies using defaults  // 使用默认配置
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }

        threshold = newThr;

        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];// 新数组
        table = newTab;
        if (oldTab != null) {
            // HashMap的扩容会把原先哈希表的容量扩大两倍。扩大之后，会对节点重新进行处理
            // 哈希表上的节点的状态有3种，分别是单节点，无节点，链表，扩容对于这3种状态的处理方式如下：

            // 以8为旧容量，扩容到16讲解:

            // 1. 单节点：由于容量扩大两倍，相当于左移1位。扩容前与00000111[7，n - 1 = 8 - 1]进行与操作。
            // 扩容后与00001111[15, n - 1 = 16 - 1]进行与操作。所以最终的结果要是还是在原位置，
            // 要么在原位置 +8(+old capacity) 位置
            // 2. 无节点：不处理
            // 3. 链表扩容：遍历各个节点，每个节点的处理方式跟单节点一样，结果分成2种，还在原位置和原位置 +8 位置

            // 单节点处理示意图如https://raw.githubusercontent.com/fangjian0423/blogimages/master/images/hashmap07.jpg，
            // 这么设计的原因就是不需要再次计算hash值，只需要移动位置(+old capacity)即可：
            for (int j = 0; j < oldCap; ++j) { // 扩容之后进行rehash操作
                Node<K, V> e;
                // 遍历每个节点赋值给e,如果e为空，就不处理，也就是对应第2种情况(这个桶没有节点)
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null; // 将oldTab中的位置为j的元素置位null
                    if (e.next == null) {// 这个桶只有一个节点，也就是单节点
                        newTab[e.hash & (newCap - 1)] = e;// 单节点扩容
                    } else if (e instanceof TreeNode){
                        ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);// 红黑树方式处理
                    } else { // preserve order// 链表扩容
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
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

    // HashMap有个内部静态类Node，这个Node就是为了解决冲突而设计的链表中的节点的概念
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash; // hash 存放的是当前 key 的 hashcode
        final K key;
        V value;
        Node<K, V> next; //  next 就是用于实现链表结构

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

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
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue());
            }
            return false;
        }
    }

    // 红黑树节点
    static final class TreeNode<K, V> extends Node {
        TreeNode<K, V> parent;  // 父
        TreeNode<K, V> left;    // 左
        TreeNode<K, V> right;   // 右
        TreeNode<K, V> prev;
        boolean red;           // 判断颜色

        TreeNode(int hash, Object key, Object value, Node next) {
            super(hash, key, value, next);
        }

        public Node<K, V> putTreeVal(JDK8HashMapSource<K, V> kvHashMap8, Node<K, V>[] tab,
                                     int hash, K key, V value) {
            return null;
        }

        public Node<K, V> getTreeNode(int hash, Object key) {
            return null;
        }

        public void split(JDK8HashMapSource<K, V> kvHashMap8, Node<K, V>[] newTab, int j, int oldCap) {
        }
    }
}
