package org.java.core.base.map.core;

import java.util.*;

/**
 * 遍历Map的3种方式
 *
 * **强烈建议**使EntrySet 进行遍历,因为EntrySet可以把 key value 同时取出，
 * map.keySet()还得需要通过 key取一次 value，效率较低
 */
public class MapTraversalDemo {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("si", "李四");
        map.put("wu", "王五");
        map.put("wang", "老王");
        map.put("wang", "老王2");
        map.put("lao", "老王");

        // 第一种(map.keySet().iterator())
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println("******** key: " + key  + "\t value: " + map.get(key));
        }
        System.out.println();
        System.out.println();
        System.out.println();

        // 第二种( map.entrySet().iterator)
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println("key: " + entry.getKey() + "\t value: " + entry.getValue());
        }
        System.out.println();
        System.out.println();
        System.out.println();

        // 第三种
        map.forEach((key, value) -> {
            System.out.println("key: " + key + "\t value: " + value);
        });
    }
}
