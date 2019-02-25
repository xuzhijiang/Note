package org.java.core.base.concurrent.chapter2;

public class MyRunnable implements Runnable{

    @Override
    public void run() {
        //如果我们编写的只是运行时代码，那么要获取将会执行这段运行时代码的线程的信息，
        // 通过Thread.currentThread()的方式
        System.out.println("执行这段运行时代码的线程名: " + Thread.currentThread().getName());
    }
}
