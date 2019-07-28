package org.java.core.base.map.concurrentHashMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.7 已经解决了并发问题，并且能支持 N 个 Segment 的并发，但依然存在 HashMap 在 1.7 版本中的问题。
 *
 * 那就是查询遍历链表效率太低。因此 1.8 做了一些数据结构上的调整。
 *
 * 其中抛弃了原有的 Segment 分段锁，而采用了 CAS + synchronized 来保证并发安全性。
 *
 * 1.8 在 1.7 的数据结构上做了大的改动，采用红黑树之后可以保证查询效率（O(logn)），
 * 甚至取消了 ReentrantLock 改为了 synchronized，这样可以看出在新版的 JDK 中对 synchronized 优化是很到位的。
 */
public class ConcurrentHashMapJDK8 {

    // 也将 1.7 中存放数据的 HashEntry 改为 Node，但作用都是相同的。
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;    //其中的 val next 都用了 volatile 修饰，保证了可见性。
        volatile ConcurrentHashMapJDK8.Node<K, V> next;

        Node(int hash, K key, V val, ConcurrentHashMapJDK8.Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            // this.next = next;
        }

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }

        @Override
        public V setValue(V value) {
            return null;
        }
    }

    /*
     public V put(K key, V value) {
        return putVal(key, value, false);
    }

    final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode()); // 1. 根据 key 计算出 hashcode 。
        int binCount = 0;
        for (ConcurrentHashMap.Node<K,V>[] tab = table;;) {
            ConcurrentHashMap.Node<K,V> f; int n, i, fh;
            if (tab == null || (n = tab.length) == 0) // 2. 判断是否需要进行初始化。
                tab = initTable();
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) { // 3. f 即为当前 key 定位出的 Node，如果为空表示当前位置可以写入数据，利用 CAS 尝试写入，失败则自旋保证成功。
                if (casTabAt(tab, i, null,
                        new ConcurrentHashMap.Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }
            else if ((fh = f.hash) == MOVED) // 4. 如果当前位置的 hashcode == MOVED == -1,则需要进行扩容。
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                synchronized (f) { // 5. 如果都不满足，则利用 synchronized 锁写入数据。
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            binCount = 1;
                            for (ConcurrentHashMap.Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                        ((ek = e.key) == key ||
                                                (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                ConcurrentHashMap.Node<K,V> pred = e;
                                if ((e = e.next) == null) {
                                    pred.next = new ConcurrentHashMap.Node<K,V>(hash, key,
                                            value, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof ConcurrentHashMap.TreeBin) {
                            ConcurrentHashMap.Node<K,V> p;
                            binCount = 2;
                            if ((p = ((ConcurrentHashMap.TreeBin<K,V>)f).putTreeVal(hash, key,
                                    value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                if (binCount != 0) {
                    // 6. 如果数量大于 TREEIFY_THRESHOLD 则要转换为红黑树。
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
    **/



    /*
    public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (e = tabAt(tab, (n - 1) & h)) != null) {
            // 根据计算出来的 hashcode 寻址，如果就在桶上那么直接返回值。
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                    return e.val;
            }
            else if (eh < 0) // 如果是红黑树那就按照树的方式获取值。
                return (p = e.find(h, key)) != null ? p.val : null;
            while ((e = e.next) != null) { // 如果不满足那就按照链表的方式遍历获取值。
                if (e.hash == h &&
                    ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }
    * */
}
