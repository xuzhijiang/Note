package org.java.core.base.concurrent.chapter2;

/**
 * 以下的案例用于演示优先级高的线程可能比
 * 一个优先级低线程获取到更多的运行机会：
 */
public class PriorityDemo2 {

    public static void main(String[] args) {
        // 创建线程t1，设置优先级为10
        Thread t1 = new Thread("P10") {
            @Override
            public void run() {
                for(int i=0;i<10;i++) {
                    System.out.println(Thread.currentThread().getName() + "执行了: " + i + "次");
                }
            }
        };
        t1.setPriority(Thread.MAX_PRIORITY);

        // 创建线程t2，设置优先级为1
        Thread t2 = new Thread("P1") {
            @Override
            public void run() {
                for(int i = 0 ;i< 10;i++){
                    System.out.println(this.getName() + "执行了: " + i + "次");
                }
            }
        };
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
    }
}
