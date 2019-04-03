package com.java.algorithm.map;


public class HashMap7 {

//    /** 初始化桶大小，HashMap底层是数组，这个是数组默认的大小 */
//    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
//
//    /**  桶的最大值 */
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    /** 默认的负载因子 */
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//
//    static final Entry<?,?>[] EMPTY_TABLE = {};
//
//    /** table真正存放数据的数组 */
//    transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;
//
//    /** map中存放数据的大小 */
//    transient int size;
//
//    /** 桶大小。可以在初始化的时候显示指定 */
//    int threshold;
//
//    /** 负载因子，可以在初始化的时候显示指定 */
//    final float loadFactor;
//
//    // 参考1.7HashMap_put方法示意图.png
//    public V put(K key, V value) {
//        /** 判断当前数组是否需要初始化 */
//        if (table == EMPTY_TABLE) {
//            inflateTable(threshold);
//        }
//        /** 如果key为空，则put一个空值进去 */
//        if (key == null)
//            return putForNullKey(value);
//        /** 根据key计算出hashcode值 */
//        int hash = hash(key);
//        /** 根据计算的hashcode值定位所在的桶 */
//        int i = indexFor(hash, table.length);
//        /** 如果桶是一个链表则需要遍历判断里面的 hashcode、key 是否和传入 key 相等， */
//        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
//            Object k;
//            /** 如果相等则进行覆盖，并返回原来的值 */
//            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
//                V oldValue = e.value;
//                e.value = value;
//                e.recordAccess(this);
//                return oldValue;
//            }
//        }
//        modCount++;
//        /** 如果桶是空的，说明当前位置没有数据存入；新增一个 Entry 对象写入当前位置 */
//        addEntry(hash, key, value, i);
//        return null;
//    }
//
//    //搜索指定Hash在对应table中的索引
//    static int indexFor(int h, int length) {
//        return h & (length-1);
//    }
//    // 根据hash值，将key-value放在数组table的索引i位置上。
//    void addEntry(int hash, K key, V value, int bucketIndex) {
//        /** 判断当前HashMap的size与临界值的大小，判断是否需要扩容操作 */
//        if ((size >= threshold) && (null != table[bucketIndex])) {
//            /** 如果需要扩容，就进行2倍扩容 */
//            resize(2 * table.length);
//            /** 将当前的key重新hash并定位 */
//            hash = (null != key) ? hash(key) : 0;
//            bucketIndex = indexFor(hash, table.length);
//        }
//        /** 创建一个Entry,如果当前桶存在元素，就形成链表 */
//        createEntry(hash, key, value, bucketIndex);
//    }
//
//    void createEntry(int hash, K key, V value, int bucketIndex) {
//    //获取指定bucketIndex索引处的Entry
//        Entry<K,V> e = table[bucketIndex];
//        //将新创建的Entry放入bucketIndex索引位置，并让新的Entry指向原来的Entry。
//        table[bucketIndex] = new Entry<>(hash, key, value, e);
//        size++;
//    }
//
//    public V get(Object key) {
//        /** 如果key为null,就去数组[0]的位置找 */
//        if (key == null)
//            return getForNullKey();
//        /** 根据key获取Entry */
//        Entry<K,V> entry = getEntry(key);
//        return null == entry ? null : entry.getValue();
//    }
//
//    //如果key为空就将value放在数组的第一个位置
//    private V putForNullKey(V value) {
//        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
//            if (e.key == null) {
//                V oldValue = e.value;
//                e.value = value;
//                e.recordAccess(this);
//                return oldValue;
//            }
//        }
//        modCount++;
//        addEntry(0, null, value, 0);
//        return null;
//    }
//
//    final Entry<K,V> getEntry(Object key) {
//        /** 如果当前HashMap的size都为0，那就直接返回null */
//        if (size == 0) {
//            return null;
//        }
//        /** 根据key计算hashcode值,然后定位到具体的桶中(定位数组的索引) */
//        int hash = (key == null) ? 0 : hash(key);
//        /** 判断是否是链表，为链表则需要遍历直到 key 及 hashcode 相等时候就返回值*/
//        for (Entry<K,V> e = table[indexFor(hash, table.length)];
//             e != null;
//             e = e.next) {
//            Object k;
//            /** 不是链表就根据 key、key 的 hashcode 是否相等来返回值*/
//            if (e.hash == hash &&
//                    ((k = e.key) == key || (key != null && key.equals(k))))
//                return e;
//        }
//        /** 啥都没取到就直接返回 null */
//        return null;
//    }
//
//    static class Entry<K,V> implements Map.Entry<K,V> {
//        final K key;
//        V value;
//        Entry<K,V> next;
//        int hash;
//        /** 创建一个新的Entry */
//        Entry(int h, K k, V v, Entry<K,V> n) {
//            value = v;
//            next = n;
//            key = k;
//            hash = h;
//        }
//    }

}
