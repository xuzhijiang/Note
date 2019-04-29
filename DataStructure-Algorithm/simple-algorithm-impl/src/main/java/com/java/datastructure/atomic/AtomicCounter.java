package com.java.datastructure.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter{

    //MyAtomicInteger atomicInteger = new MyAtomicInteger();
    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void increment() {
        atomicInteger.incrementAndGet();
    }

    @Override
    public int getCounter() {
        return atomicInteger.get();
    }
}
