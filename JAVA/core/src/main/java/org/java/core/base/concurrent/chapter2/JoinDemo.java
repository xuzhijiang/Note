package org.java.core.base.concurrent.chapter2;

public class JoinDemo {

    public static void main(String[] args) throws InterruptedException{
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("自定义线程执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程T");
        t.start();

        Thread.sleep(2000);
        System.out.println("主线程执行完毕，等待t线程执行完成...");

        // t.join方法可用于暂停当前线程(这里暂停的就是主线程)执行,等待t执行完成
        //t.join();
        // main thread等待t 2秒，或者如果2秒内t执行完成，main thread才会继续执行
        t.join(2000);
        System.out.println("全部线程执行完毕");

        // 其实从源码可以看出，t.join() 也是利用的Object的wait，notify等待通知机制：
        //核心逻辑:
        //    while (isAlive()) {
        //        相当于主线程调用wait方法，主线程变成WAITING等待状态.
        //        wait(0);
        //    }
        //
        //在t线程完成后会调用 notifyAll() 方法，是在 JVM 实现中调用，所以这里看不出来。
    }

}
