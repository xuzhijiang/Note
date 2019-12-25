package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子的数组
 */
public class AtomicIntegerArrayDemo {
    private static int[] arr = new int[5];

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);

        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
                ++arr[2];
                atomicIntegerArray.incrementAndGet(2);
            }, String.valueOf(i)).start();
        }

        try { Thread.sleep(15000L); } catch (InterruptedException e) { e.printStackTrace();}

        System.out.println("result: " + atomicIntegerArray.get(2));
        System.out.println("result: " + arr[2]);
    }
}
