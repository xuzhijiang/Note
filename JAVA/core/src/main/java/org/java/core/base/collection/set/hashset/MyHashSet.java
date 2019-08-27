package org.java.core.base.collection.set.hashset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * public class HashSet<E> extends AbstractSet<E>
 * implements Set<E>, Cloneable, java.io.Serializable{}
 *
 * HashSet总结:
 * 1. HashSet内部使用HashMap，HashSet集合内部所有的操作基本上都是基于HashMap完成的
 * 2. HashSet中的元素是无序的，这是因为它内部使用HashMap进行存储，
 * 而HashMap添加键值对的时候是根据hash函数得到数组的下标的
 */
public class MyHashSet<E> {

    private transient HashMap<E,Object> map;

    // HashSet内部使用HashMap进行处理，由于Set只需要键值对中的键，
    // 而不需要值，所有的值都用这个对象
    private static final Object PRESENT = new Object();

    public MyHashSet(int initialCapacity, float loadFactor, boolean dummy) {
        // map使用LinkedHashMap构造，LinkedHashMap是HashMap的子类，accessOrder为false，即使用插入顺序
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    // 调用HashMap的put操作完成Set的add操作
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;  // HashMap put成功返回true，否则false
    }

    // 调用HashMap的remove操作完成。
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT; // 对应的节点移除成功返回true，否则false
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<String>(5);
        set.add("java");
        set.add("golang");
        set.add("python");
        set.add("ruby");
        set.add("scala");
        set.add("c");

        for (String str : set) {
            System.out.println(str);
        }
    }

}
