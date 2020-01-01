package org.java.core.base.concurrent.chapter2;

public class CustomThread extends Thread {

    @Override
    public void run() {
        System.out.println("CustomThread running");
    }

    public static void main(String[] args) {
        System.out.println("main线程：" + Thread.currentThread().getName());

        // 自定义线程,因为我们没有定义有参数的构造方法,所以不能够传入参数作为线程的名字.
        CustomThread customThread = new CustomThread();
        customThread.start();

        // 传入参数为线程的名字
        Thread t = new Thread("我的匿名线程类--") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 匿名线程类");
            }
        };
        t.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("------线程名: " + Thread.currentThread().getName());

            }
        }, "我定义的Runnable");
        t2.start();
        System.out.println("t2 name: " + t2.getName());
    }

}
