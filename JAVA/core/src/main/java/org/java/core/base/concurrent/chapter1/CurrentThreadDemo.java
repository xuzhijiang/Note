package org.java.core.base.concurrent.chapter1;

public class CurrentThreadDemo {

    public static void main(String[] args) {
//        通过Thread的静态方法currentThread()获取当前线程的信息
        new Thread("custom thread") {
            @Override
            public void run() {
                // Thread.currentThread()位于哪一个线程的执行代码中，
                // 在运行时通过这个方法获取的这个线程，也就是当前线程。
                System.out.println("当前线程：" + Thread.currentThread().getName());
            }
        }.start();

        System.out.println("当前线程：" + Thread.currentThread().getName());
    }
}
