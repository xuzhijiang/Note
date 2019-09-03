package org.java.core.base.map.linkedhashmap;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {

    @Test
    public void testInsertOrder() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("语文", "1");
        map.put("数学", "2");
        map.put("英语", "3");
        map.put("历史", "4");
        map.put("政治", "5");
        map.put("地理", "6");
        map.put("生物", "7");
        map.put("化学", "8");
        map.put("物理", "9");
        for(Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Test
    public void testAccessOrder() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(5, 0.75f, true);
        map.put("语文", "1");
        map.put("数学", "2");
        map.put("英语", "3");
        map.put("历史", "4");
        map.put("政治", "5");
        map.put("地理", "6");
        map.put("生物", "7");
        map.put("化学", "8");
        map.put("物理", "9");
        map.get("化学");
        map.get("数学");
        for(Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // 如何遍历LinkedList不引发异常
    @Test
    public void testTraverse()  {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(5, 0.75f, true);
        map.put("语文", "1");
        map.put("数学", "2");
        map.put("英语", "3");
        map.put("历史", "4");
        map.put("政治", "5");
        map.put("地理", "6");
        map.put("生物", "7");
        map.put("化学", "8");
        map.put("物理", "9");
        map.get("化学");
        map.get("数学");

        try {
            for (String  str: map.keySet()) {
                // 每次get的时候,会修改map的结构,导致ConcurrentModificationException
                System.out.println(map.get(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for  (String str : map.values()) {
            System.out.println(str);
        }

        for(Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
