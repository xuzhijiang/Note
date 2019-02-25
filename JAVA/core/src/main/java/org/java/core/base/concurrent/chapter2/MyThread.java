package org.java.core.base.concurrent.chapter2;

public class MyThread extends Thread {
    /**
     * run方法会在调用start()方法之后被执行
     */
    @Override
    public void run() {
        System.out.println("MyThread running");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("匿名线程类");
            }
        };
        t.start();

        Thread t2 = new Thread(new MyRunnable(), "我定义的Runnable");
        t2.start();
        System.out.println("t2 name: " + t2.getName());
    }
}
