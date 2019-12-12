package org.java.core.advanced.jvm;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JstackCase {

    private static Executor executor = Executors.newFixedThreadPool(5);

    private static Object obj = new Object();

    public static void main(String[] args) {
        Task task1 = new Task();
        Task task2 = new Task();
        executor.execute(task1);
        executor.execute(task2);
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            synchronized (obj) {
                calculate();
            }
        }

        public void calculate() {
            long i = 0L;
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

}
