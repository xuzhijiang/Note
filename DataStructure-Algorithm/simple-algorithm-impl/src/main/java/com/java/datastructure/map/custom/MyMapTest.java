package com.java.datastructure.map.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyMapTest {
    public static void main(String[] args) {
        MyHashMap7<String,String > map = new MyHashMap7();
        Long t1 = System.currentTimeMillis();
        for (int i=0; i<100000;i++) {
            map.put("a" + i, "a" + i);
        }
        for (int i = 0; i < 100000; i++) {
            System.out.println(map.get("a" + i));
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("MyHashMap耗时：" + TimeUnit.MILLISECONDS.toSeconds(t2 - t1) + "秒");

        System.out.println("-------------------HashMap-------------------------" );
        Map<String,String > hashMap = new HashMap();
        Long t3 = System.currentTimeMillis();
        for (int i=0; i<100000;i++) {
            hashMap.put("a" + i, "a" + i);
        }
        for (int i = 0; i < 100000; i++) {
            System.out.println(map.get("a" + i));
        }
        Long t4 = System.currentTimeMillis();
        System.out.println("JDK的HashMap耗时："+ TimeUnit.MILLISECONDS.toSeconds(t4 - t3) + "秒");
    }
}