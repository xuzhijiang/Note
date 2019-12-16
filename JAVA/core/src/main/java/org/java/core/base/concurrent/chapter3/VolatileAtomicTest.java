package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileAtomicTest {

    // volatile不能保证原子性
    private static volatile int sum = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            }, String.valueOf(i)).start();
        }

        // main暂停5秒,等待其他线程计算完成
        Thread.sleep(5000L);

        // 防止其他线程没有计算完成
        // while (Thread.activeCount() > 2) { // 因为最少有2个线程: main+gc线程
        //    Thread.yield();
        // }

        Thread.yield();
        System.out.println("计算完成,sum 最终结果是: " + sum);
        System.out.println("计算完成,AtomicInteger最终结果是: " + atomicInteger.get());
    }

    /**
     * sum++在底层并不是原子操作,可以通过反编译字节码看出来,每次sum++实际上是分为3个步骤：
     * 1. 把sum从主内存读到线程的工作内存
     * 2. 在工作内存中sum++
     * 3. 把修改完的值刷回到主内存中
     */
    private static void increase() {
        sum++;
        atomicInteger.getAndIncrement();
    }

}
