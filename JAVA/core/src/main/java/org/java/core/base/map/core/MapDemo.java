package org.java.core.base.map.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        map.put("si", "李四");
        map.put("wu", "王五");
        map.put("wang", "老王");
        map.put("wang", "老王2");
        map.put("lao", "老王");

        // 获取Map中所有值
        Collection<String> values = map.values();
        for (String value : values) {
            System.out.println("********value: " + value);
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
