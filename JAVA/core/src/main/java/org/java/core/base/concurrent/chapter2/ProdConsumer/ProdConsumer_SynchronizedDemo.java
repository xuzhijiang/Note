package org.java.core.base.concurrent.chapter2.ProdConsumer;

/**
 * V1.0版的生产消费模式
 *
 * 题目: 一个初始值为0的变量，两个线程对其操作，一个+1，一个-1，来5轮
 *
 * 1 线程         操作(资源类对外暴露的方法)      资源类
 * 2 多线程交互期间的三步: 判断         干活           通知
 * 3 多线程交互中,必须要防止虚假唤醒
 */
public class ProdConsumer_SynchronizedDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BBB").start();


        /**
         * 测试虚假唤醒(不该被唤醒的时候唤醒)
         */
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CCC").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DDD").start();
    }

    /**
     * 共享资源类
     */
    private static class ShareData {
        private int number = 0;

        public void increment() throws InterruptedException {
            synchronized (this) {
                // 1. 判断: 是不是轮到我这个线程来做了
                // 把while改成if，之后多线程环境下，就会出现问题，会出现虚假唤醒,所以不能使用if判断
                while (number != 0) {
                    // 等待，不能生产
                    this.wait();
                }
                // 2. 干活
                number++;
                System.out.println(Thread.currentThread().getName() + "\t number: " + number);
                // 3. 通知
                this.notifyAll();
            }
        }

        public void decrement() throws InterruptedException {
            synchronized (this) {
                while (number == 0) {
                    this.wait();
                }
                number--;
                System.out.println(Thread.currentThread().getName() + "\t number: " + number);
                // 有可能会唤醒其他的decrement的线程,这个时候就可能会decrement连续减了2次.
                // 所以一定要通过while循环来防止虚假唤醒.
                this.notifyAll();
            }
        }

    }

}
