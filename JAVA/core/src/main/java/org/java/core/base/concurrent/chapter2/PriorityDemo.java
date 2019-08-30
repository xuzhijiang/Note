package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

/**
 * 演示优先级高的线程可能比一个优先级低线程获取到更多的运行机会：
 */
public class PriorityDemo {

    @Test
    public void printPriority() {
        System.out.println("最大优先级: " + Thread.MAX_PRIORITY);
        System.out.println("正常优先级: " + Thread.NORM_PRIORITY);
        System.out.println("最小优先级: " + Thread.MIN_PRIORITY);
        System.out.println("主线程优先级: " + Thread.currentThread().getPriority());
        Thread t = new Thread();
        System.out.println("创建一个线程默认的优先级: " + t.getPriority());
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建线程t1，设置优先级为10
        Thread t1 = new Thread("thread name - Priority 10") {
            @Override
            public void run() {
                for(int i=0;i<100000;i++) {
                    System.out.println(Thread.currentThread().getName() + "执行了: " + i + "次");
                }
            }
        };
        t1.setPriority(Thread.MAX_PRIORITY);

        // 创建线程t2，设置优先级为1
        Thread t2 = new Thread("thread name Priority 1") {
            @Override
            public void run() {
                for(int i = 0 ;i< 100000;i++){
                    System.out.println(this.getName() + "执行了: " + i + "次");
                }
            }
        };
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("main is over");
    }
}
