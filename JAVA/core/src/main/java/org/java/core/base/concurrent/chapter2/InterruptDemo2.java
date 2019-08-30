package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

import java.util.Date;

public class InterruptDemo2 {

    /**
     * 在线程中,对interrupt flag进行判断处理
     */
    @Test
    public void threadInterruptedDemo() throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                int i=0;
                while (true) {
                    // 每次打印前都判断是否被中断,判断the interrupt flag
                    if(!Thread.currentThread().isInterrupted()){
                        i++;
                        System.out.println("自定义线程，打印...."+i+"次");
                    }else{// 如果被中断，停止运行
                        System.out.println("自定义线程：被中断...");
                        return;
                    }
                }
            }
        };
        t.start();

        //主线程休眠1毫秒，以便自定义线程执行
        Thread.sleep(1);
        System.out.println("主线程:休眠1毫秒后发送中断信号...");

        // 此方法的内部调用了Thread的native方法interrupt0(); // Just to set the interrupt flag
        // 调用了 t.interrupt() 方法就是将 thread 中的一个标志属性置为了 true。
        // 源码看不到，是jvm在native底层设置的
        // 并不是说调用了t.interrupt()就可以中断线程，如果不对这个interrupt标记进行判断处理,其实是没有什么作用
        // 上面调用了Thread.currentThread().isInterrupted()对这个标志进行了判断,做了处理,所以这里是有作用的
        // 当然如果抛出了 InterruptedException 异常，该interrupe flag就会被 JVM 重置为 false
        t.interrupt();

        // 在收到中断信号后，自定义线程停止了运行。
        // 不过，为什么自定义线程t在收到中断信号后还执行了一次呢？
        // 这是因为中断信号发送的后，t线程正好运行到了打印的代码，
        // 因此只有到下一次循环的时候才会检测到中断信号，停止运行。
        //
        // 如果你多运行几次的话，会发现每次的结果都是不一样的。
    }

    /**
     * 在线程中,不对interrupt flag进行判断处理
     */
    @Test
    public void interrupteWithoutProcess() throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                for (int i=0;i<100000;i++) {
                    System.out.println("自定义线程，打印...." + i + "次.");
                }
            }
        };
        t.start();
        Thread.sleep(1);
        System.out.println("主线程:休眠1毫秒后发送中断信号...");

        t.interrupt();
        // 因为没有对interrupt flag做处理,所以主线程不停止的情况下,t一直打印.
        Thread.sleep(10 * 1000);

        // 主线程一旦退出,t也会停止运行,不会再继续运行,因为jvm已经停止了.
    }

    /**
     * Thread.sleep()中会判断是否当前的线程被中断.
     */
    @Test
    public void threadInterruptedTest2() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                //预期循环10次
                for (int i=0;i<10;i++) {
                    try {
                        // sleep方法中会判断当前线程是否被中断.如果被中断,会抛出中断异常.
                        Thread.sleep(4000);
                        System.out.println("自定义线程：当前时间：" + new Date().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("自定义线程收到中断信号，总循环了: " + i + "次");
                    }
                }
            }
        };
        t.start();

        // 主线程休眠9秒
        Thread.sleep(9000);
        System.out.println("主线程：等到9秒后发送中断信号...");
        t.interrupt();
    }
}
