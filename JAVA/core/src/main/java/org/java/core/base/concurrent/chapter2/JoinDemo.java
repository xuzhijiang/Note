package org.java.core.base.concurrent.chapter2;

public class JoinDemo {

    public static void main(String[] args) throws InterruptedException{
        long start = System.currentTimeMillis();
        System.out.println("程序开始运行");
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    // 模拟自定义线程干某件事情花费了5秒
                    System.out.println("自定义线程执行开始...");
                    Thread.sleep(5000);
                    System.out.println("自定义线程执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // 模拟主线程干其他事情花了2秒
        Thread.sleep(2000);
        System.out.println("主线程执行完毕，等待t线程执行完成...");
        // 主线程2秒后就可以继续执行，但是其必须执行的条件是t线程必须
        // 执行完成，此时调用t的join方法
        long joinstart = System.currentTimeMillis();
        // 等待线程t终止
        // 在调用t.join()时会一直阻塞到 t 执行完毕，所以最终主线程会等待 t 线程执行完毕
        // 其实从源码可以看出，t.join() 也是利用的Object的wait，notify等待通知机制：

        //核心逻辑:
        //
        //java
        //    while (isAlive()) {
        //        相当于主线程调用wait方法，主线程变成WAITING等待状态.
        //        wait(0);
        //    }
        //
        //在 join 线程完成后会调用 notifyAll() 方法，是在 JVM 实现中调用，所以这里看不出来。
        t.join();
        System.out.println("主线程: t已经执行完毕了，等待了: " + (System.currentTimeMillis() - joinstart)/1000 + "秒");
        System.out.println("程序运行总时间: " + (System.currentTimeMillis() - start)/1000 + "秒");
    }
}
