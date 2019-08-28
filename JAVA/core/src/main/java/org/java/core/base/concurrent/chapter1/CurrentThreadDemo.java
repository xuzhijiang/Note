package org.java.core.base.concurrent.chapter1;

public class CurrentThreadDemo {

    public static void main(String[] args) {
        // 传入参数为线程的名字
        new Thread("custom thread") {
            @Override
            public void run() {
                System.out.println("当前线程：" + Thread.currentThread().getName());
            }
        }.start();

        System.out.println("当前线程：" + Thread.currentThread().getName());
    }
}
