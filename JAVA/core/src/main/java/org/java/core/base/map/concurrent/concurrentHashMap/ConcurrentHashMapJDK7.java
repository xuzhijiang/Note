package org.java.core.base.map.concurrent.concurrentHashMap;

import java.io.Serializable;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConcurrentHashMap 同样也分为 1.7 、1.8 版，两者在实现上略有不同。先来看看 1.7 的实现:
 *
 * 是由 Segment 数组、HashEntry 组成，和 HashMap 一样，仍然是数组加链表。
 *
 * 原理上来说：ConcurrentHashMap 采用了分段锁技术，其中 Segment 继承于 ReentrantLock。不会像 HashTable 那样不管是 put 还是 get 操作都需要做同步处理，理论上 ConcurrentHashMap 支持 CurrencyLevel (Segment 数组数量)的线程并发。每当一个线程占用锁访问一个 Segment 时，不会影响到其他的 Segment。
 */
public class ConcurrentHashMapJDK7<K, V> {

    /**
     * Segment 数组，存放数据时首先需要定位到具体的 Segment 中。
     */
    // final Segment<K, V>[] segments;

    transient Set<K> keySet;

    transient Set<Map.Entry<K, V>> entrySet;

        /*
    // 首先是通过 key 定位到 Segment，之后在对应的 Segment 中进行具体的 put。
    public V put(K key, V value) {
        Segment<K,V> s;
        if (value == null)
            throw new NullPointerException();
        int hash = hash(key);
        int j = (hash >>> segmentShift) & segmentMask;
        if ((s = (Segment<K,V>)UNSAFE.getObject          // nonvolatile; recheck
                (segments, (j << SSHIFT) + SBASE)) == null) //  in ensureSegment
            s = ensureSegment(j);
        return s.put(key, hash, value, false);
    }
    */

    /*
        // get 逻辑比较简单：只需要将 Key 通过 Hash 之后定位到具体的 Segment ，再通过一次 Hash 定位到具体的元素上。
        //由于 HashEntry 中的 value 属性是用 volatile 关键词修饰的，保证了内存可见性，所以每次获取时都是最新值。
        // ConcurrentHashMap 的 get 方法是非常高效的，因为整个过程都不需要加锁。
        public V get(Object key) {
            Segment<K,V> s; // manually integrate access methods to reduce overhead
            HashEntry<K,V>[] tab;
            int h = hash(key);
            long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
            if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&
                (tab = s.table) != null) {
                for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile
                         (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
                     e != null; e = e.next) {
                    K k;
                    if ((k = e.key) == key || (e.hash == h && key.equals(k)))
                        return e.value;
                }
            }
            return null;
        }
    */

    // Segment 是 ConcurrentHashMap 的一个内部类
    static final class Segment<K, V> extends ReentrantLock implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;

        // 和 HashMap 中的 HashEntry 作用一样，真正存放数据的桶
        transient volatile HashEntry<K, V>[] table;
        transient int count;
        transient int modCount;
        transient int threshold;
        // final float loadFactor;


        // 和 HashMap的Node 类似，唯一的区别就是其中的核心数据如 value ，
        // 以及链表都是 volatile 修饰的，保证了获取时的可见性。
        static final class HashEntry<K,V> {
            final int hash;
            final K key;
            // 虽然 HashEntry 中的 value 是用 volatile 关键词修饰的，
            // 但是并不能保证并发的原子性，所以 put 操作时仍然需要加锁处理。
            volatile V value;
            volatile HashEntry<K,V> next;

            HashEntry(int hash, K key, V value, HashEntry<K,V> next) {
                this.hash = hash;
                this.key = key;
                this.value = value;
                //this.next = next;
            }
        }

        /*
        final V put(K key, int hash, V value, boolean onlyIfAbsent) {
        // 0. 虽然 HashEntry 中的 value 是用 volatile 关键词修饰的，
        // 但是并不能保证并发的原子性，所以 put 操作时仍然需要加锁处理。
        // 首先第一步的时候会尝试获取锁，如果获取失败肯定就有其他线程存在竞争，
        // 则利用 scanAndLockForPut() 自旋获取锁。
        HashEntry<K,V> node = tryLock() ? null :
                scanAndLockForPut(key, hash, value);
        V oldValue;
        try {
            // 1. 将当前 Segment 中的 table 通过 key 的 hashcode 定位到 HashEntry。
            HashEntry<K,V>[] tab = table;
            int index = (tab.length - 1) & hash;
            HashEntry<K,V> first = entryAt(tab, index);
            for (HashEntry<K,V> e = first;;) {
                if (e != null) {
                    K k;
                    // 2. 遍历该 HashEntry，如果不为空则判断传入的 key 和当前遍历的 key 是否相等，相等则覆盖旧的 value。
                    if ((k = e.key) == key ||
                            (e.hash == hash && key.equals(k))) {
                        oldValue = e.value;
                        if (!onlyIfAbsent) {
                            e.value = value;
                            ++modCount;
                        }
                        break;
                    }
                    e = e.next;
                } else { // 3. 不为空则需要新建一个 HashEntry 并加入到 Segment 中，同时会先判断是否需要扩容。
                    if (node != null)
                        node.setNext(first);
                    else
                        node = new HashEntry<K,V>(hash, key, value, first);
                    int c = count + 1;
                    if (c > threshold && tab.length < MAXIMUM_CAPACITY)
                        rehash(node);
                    else
                        setEntryAt(tab, index, node);
                    ++modCount;
                    count = c;
                    oldValue = null;
                    break;
                }
            }
        } finally {
            unlock(); // 4. 最后会解除在 0 中所获取当前 Segment 的锁。
        }
        return oldValue;
    }

    private HashEntry<K,V> scanAndLockForPut(K key, int hash, V value) {
            HashEntry<K,V> first = entryForHash(this, hash);
            HashEntry<K,V> e = first;
            HashEntry<K,V> node = null;
            int retries = -1; // negative while locating node
            // 1. 尝试自旋获取锁。
            while (!tryLock()) {
                HashEntry<K,V> f; // to recheck first below
                if (retries < 0) {
                    if (e == null) {
                        if (node == null) // speculatively create node
                            node = new HashEntry<K,V>(hash, key, value, null);
                        retries = 0;
                    }
                    else if (key.equals(e.key))
                        retries = 0;
                    else
                        e = e.next;
                }
                else if (++retries > MAX_SCAN_RETRIES) { // 2. 如果重试的次数达到了 MAX_SCAN_RETRIES 则改为阻塞锁获取，保证能获取成功。
                    lock();
                    break;
                }
                else if ((retries & 1) == 0 &&
                         (f = entryForHash(this, hash)) != first) {
                    e = first = f; // re-traverse if entry changed
                    retries = -1;
                }
            }
            return node;
        }
        * */
    }

}