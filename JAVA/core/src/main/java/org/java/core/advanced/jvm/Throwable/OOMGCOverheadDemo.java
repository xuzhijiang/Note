package org.java.core.advanced.jvm.Throwable;

import java.util.*;

/**
 * -Xms30m -Xmx30m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=3m
 *
 * 如果垃圾回收耗费了98%的时间，但是回收的内存还不到2%，那么JVM会认为即将发生OOM，让程序提前结束
 *
 * 不同的系统环境可能需要设置不同的堆内存大小，否则会产生不同的OOM错误。
 * 其实也算是好理解，因为java.lang.OutOfMemoryError: GC overhead limit exceeded需要有两个条件：
 * 98%的时间和2%的内存。如果这两个条件有一个没有达到，那就可能出现java.lang.OutOfMemoryError: Java heap space这个错误。
 *
 */
public class OOMGCOverheadDemo {

    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println("**************** i: " + i);
            e.printStackTrace();
            throw e;
        }
    }

}
