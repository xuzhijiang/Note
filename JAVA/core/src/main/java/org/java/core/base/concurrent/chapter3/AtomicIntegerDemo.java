package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        // 默认初始值为0
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println("AtomicInteger默认初始值: " + atomicInteger.get());
        // 指定增加的步长: ++i
        System.out.println(atomicInteger.addAndGet(3));
        // 指定增加的步长: i++
        System.out.println(atomicInteger.getAndAdd(4));
        // 只增加1的 ++i
        System.out.println(atomicInteger.incrementAndGet());
        // 只增加1的 i++
        System.out.println(atomicInteger.getAndIncrement());

        // 指定初始值
        AtomicInteger atomicInteger1 = new AtomicInteger(100);
    }
}
