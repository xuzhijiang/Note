package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

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
        Thread t1 = new Thread(() -> {
                for(int i=0;i<100;i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "执行了: " + i + "次");
                }
        }, "AAA");
        // 设置优先级为10
        t1.setPriority(Thread.MAX_PRIORITY);

        Thread t2 = new Thread(() -> {
            for(int i = 0 ;i< 100000;i++){
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t 执行了: " + i + "次");
            }
        }, "BBB");
        // 设置优先级为1
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
    }

    /**
     * 可以看到在windows操作系统上，线程优先级好像是有作用的，P1和P10线程对应的操作系统优先级是不一样的
     * 但是对于CentOS 6.5，可以看到对应的线程优先级都是0，说明，线程的优先级设置被忽略了
     */
    /*
    "BBB" #12 prio=1 os_prio=-2 tid=0x000000001dfc3000 nid=0x39e4 waiting on condition [0x000000001e93e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at org.java.core.base.concurrent.chapter2.PriorityDemo.lambda$main$1(PriorityDemo.java:39)
        at org.java.core.base.concurrent.chapter2.PriorityDemo$$Lambda$2/1854731462.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)

"AAA" #11 prio=10 os_prio=2 tid=0x000000001dfc2800 nid=0x368c waiting on condition [0x000000001e83f000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at org.java.core.base.concurrent.chapter2.PriorityDemo.lambda$main$0(PriorityDemo.java:26)
        at org.java.core.base.concurrent.chapter2.PriorityDemo$$Lambda$1/1922154895.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)
     */
}
