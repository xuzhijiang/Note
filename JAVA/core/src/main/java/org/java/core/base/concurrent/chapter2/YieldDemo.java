package org.java.core.base.concurrent.chapter2;

public class YieldDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "\t i: "+ i);
                Thread.yield();
            }
        }, "AAA");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "\t i: "+ i);
            }
        }, "BBB");

        t1.start();
        t2.start();
    }
}
