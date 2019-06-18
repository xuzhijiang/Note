package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

import java.util.Date;

/**
 * 可以采用中断线程的方式来进行线程之间的通信，
 * 调用了 `thread.interrupt()` 方法其实就是将 thread 中的一个标志属性置为了 true。
 * (源码看不到，是jvm底层设置的)
 *
 * 并不是说调用了该方法就可以中断线程，如果不对这个标志进行响应其实是没有什么作用
 * (本例中调用了Thread.currentThread().isInterrupted()对这个标志进行了判断)。
 *
 * 但是如果抛出了 InterruptedException 异常，该标志就会被 JVM 重置为 false
 */
public class InterruptDemo2 {

    @Test
    public void threadInterruptedDemo() throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                int i=0;
                while (true) {
                    // 每次打印前都判断是否被中断
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
        t.interrupt();

        // 在收到中断信号后，自定义线程停止了运行。
        // 不过，为什么自定义线程t在收到中断信号后还执行了一次呢？
        // 这是因为中断信号发送的后，t线程正好运行到了打印的代码，
        // 因此只有到下一次循环的时候才会检测到中断信号，停止运行。
        //
        // 如果你多运行几次的话，会发现每次的结果都是不一样的。
    }

    @Test
    public void threadInterruptedTest2() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                //预期循环10次
                for (int i=0;i<10;i++) {
                    try {
                        Thread.sleep(4000);
                        System.out.println("自定义线程：当前时间：" + new Date().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("自定义线程收到中断信号，总循环了: " + i + "次");
                        // 接收到中断信时，停止运行
                        return;
                    }
                    // 可以看到自定义线程打印出两次当前时间后就停止了运行，
                    // 根本原因在于，我们在收到中断信号后，在catch代码中使用了return，
                    // 结束了方法。读者可以尝试将return去掉，这个时候，
                    // 即使收到了中断信号，也会继续打印10次！
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
