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

    private static class MyRunnable implements Runnable{

        @Override
        public void run() {
            //如果我们编写的只是运行时代码，那么要获取将会执行这段运行时代码的线程的信息，
            // 通过Thread.currentThread()的方式
            System.out.println("执行这段运行时代码的线程名: " + Thread.currentThread().getName());
        }
    }
}
