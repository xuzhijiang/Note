package org.java.core.base.map.hashmap;

import java.util.Map;

public class JDK7HashMapSource<K, V> {

    static final Map.Entry<?, ?>[] EMPTY_TABLE = {};

    /**
     * 真正存放数据的数组，总是2的幂次方
     */
    transient Map.Entry<K,V>[] table = (Map.Entry<K, V>[]) EMPTY_TABLE;

    /*
    public V put(K key, V value) {
        if (table == EMPTY_TABLE) { // 1. 判断当前数组是否需要初始化。
            inflateTable(threshold);
        }
        if (key == null) // 2. 如果 key 为空，则 put 一个空值进去。
            return putForNullKey(value);
        int hash = hash(key);// 3. 根据 key 计算出 hash值，然后根据计算出的 hash 定位出所在桶。
        int i = indexFor(hash, table.length);
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            // 4. 如果桶是一个链表则需要遍历判断链表上的每个entry中的key 是否和传入 key 相等，
            // 如果相等则进行覆盖，并返回原来的值。
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        // 5. 如果桶是空的，说明当前位置没有数据存入；新增一个 Entry 对象写入当前位置。
        addEntry(hash, key, value, i);
        return null;
    }
    */

    /*
    void addEntry(int hash, K key, V value, int bucketIndex) {
    // 6. 当调用 addEntry 写入 Entry 时需要判断是否需要扩容。
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
            // 如果需要就进行两倍扩充，并将当前的 key 重新 hash 并定位。
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    // 而在 createEntry 中会将当前位置的桶传入到新建的桶中，如果当前桶有值就会在位置形成链表。
    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        size++;
    }
    */

    /*
    public V get(Object key) {
        if (key == null)
            return getForNullKey();
        Entry<K,V> entry = getEntry(key);
        return null == entry ? null : entry.getValue();
    }

    final Entry<K,V> getEntry(Object key) {
        if (size == 0) {
            return null;
        }
        // 根据 key 计算出 hash值，然后根据计算出的 hash 定位出所在桶。
        int hash = (key == null) ? 0 : hash(key);
        // 判断该位置是否为链表,不是链表就根据 key、key 的 hashcode 是否相等来返回值。
        // 为链表则需要遍历直到 key 及 hashcode 相等时候就返回值。
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        // 啥都没取到就直接返回 null 。
        return null;
    }
    */
}
