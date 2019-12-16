package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t updated value: " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2014) + "\t updated value: " + atomicInteger.get());
    }
}
