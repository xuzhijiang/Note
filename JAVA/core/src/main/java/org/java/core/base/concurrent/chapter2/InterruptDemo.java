package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
//        threadSleepInterrupt();
//        synchronizedThreadInterrupt();
        threadInterruptFlag();
    }

    private static void threadSleepInterrupt() {
        System.out.println();
        System.out.println();
        System.out.println();
        Thread t1 = new Thread(() -> {
            try {
                /**
                 * sleep方法可能会抛出InterruptedException异常。这个异常是在线程sleep的时候，
                 * 如果其他的线程中断了这个线程的执行,就会抛出这个异常。
                 * 中断的作用是，如果一个线程在运行期，我们不想让这个线程继续运行下去了，就可以给其发送一个信号，让其停止运行
                 */
                // java.lang.InterruptedException: sleep interrupted
                Thread.sleep(2000L);
                System.out.println(Thread.currentThread().getName() + "\t 执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "\t 抛出了中断异常");
            }
            System.out.println(Thread.currentThread().getName() + "\t 继续做其他事情");
        }, "AAAA");

        t1.start();
        // 调用interrupt()来中断线程
        t1.interrupt();
        System.out.println("Main run over");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * 不能中断处于synchronized锁阻塞的线程.
     */
    private static void synchronizedThreadInterrupt() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (Object.class) {
                System.out.println(Thread.currentThread().getName() + "\t 拿到锁,可以继续执行");
                try {
                    TimeUnit.SECONDS.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t 运行完成");
            }
        }, "AAA");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 因为没有拿到锁,被阻塞");
            synchronized (Object.class) {
                System.out.println(Thread.currentThread().getName() + "\t 拿到锁,可以继续执行");
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().getName() + "\t 被中断了");
                }
                System.out.println(Thread.currentThread().getName() + "\t 运行完成");
            }
        }, "BBB");

        t1.start();
        TimeUnit.SECONDS.sleep(1L);
        t2.start();
        TimeUnit.MILLISECONDS.sleep(200L);

        System.out.println(Thread.currentThread().getName() + "\t 中断" + t2.getName());
        t2.interrupt();
    }

    /**
     * 在代码中如果没有调用可以抛出中断异常的方法(例如sleep)，那么需要不断的调用Thread的isInterrupted()来判断当前线程是否被中断.
     * 否则会一直运行下去
     */
    public static void threadInterruptFlag() throws InterruptedException {
        Thread t = new Thread(() -> {
            int i=0;
            while (true) {
                // 每次打印前都判断中断标记,,判断线程是否被中断了
                if(!Thread.currentThread().isInterrupted()){
                    i++;
                    System.out.println(Thread.currentThread().getName() + "\t 第"+i+"次打印");
                }else{
                    System.out.println(Thread.currentThread().getName() + "\t 被中断,i最终为: " + i);
                    return;
                }
            }
        });
        t.start();
        Thread.sleep(100);
        // 设置线程的中断标记 (Just to set the interrupt flag)
        // 并不是说调用了interrupt()就可以中断线程，如果线程不对中断标记进行判断处理,其实是没有什么作用,线程会一直执行下去
        // 如果抛出了 InterruptedException 异常，中断标记就会被 JVM 重置为 false
        t.interrupt();
    }

}
