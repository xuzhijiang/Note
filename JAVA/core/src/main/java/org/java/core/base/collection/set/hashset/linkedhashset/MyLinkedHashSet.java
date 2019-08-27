package org.java.core.base.collection.set.hashset.linkedhashset;

import org.java.core.base.collection.set.hashset.MyHashSet;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LinkedHashSet总结
 *
 * LinkedHashSet继自HashSet，但是内部的map是使用LinkedHashMap构造的，并且accessOrder为false，使用查询顺序。所以LinkedHashSet遍历的顺序就是插入顺序。
 *
 */
public class MyLinkedHashSet<E> extends MyHashSet<E> {

    // LinkedHashSet继承自HashSet，它的构造函数会调用父类HashSet的构造函数：
    public MyLinkedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, true);
    }

    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<String>(5);
        set.add("java");
        set.add("golang");
        set.add("python");
        set.add("ruby");
        set.add("scala");

        for(String str : set) {
            System.out.println(str);
        }
    }
}
