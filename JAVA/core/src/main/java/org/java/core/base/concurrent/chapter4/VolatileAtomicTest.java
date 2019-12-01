package org.java.core.base.concurrent.chapter4;

public class VolatileAtomicTest {

    private static volatile int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j=0;j<1000;j++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(sum);
    }

    private static void increase() {
        sum++;
    }

}
