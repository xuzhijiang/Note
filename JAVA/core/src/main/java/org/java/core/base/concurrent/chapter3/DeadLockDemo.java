package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.TimeUnit;

/**
 * 死锁如果出现,如果没有外力干涉,永远解决不了,因为你的代码有问题,需要把程序停下来.找代码的问题.
 */
public class DeadLockDemo {

    public static void main(String[] args) throws Exception {
        Object obj1 = new Object();
        Object obj2 = new Object();

        new Thread(new HoldLock(obj1, obj2), "AAA").start();

        new Thread(new HoldLock(obj2, obj1), "BBB").start();

        /**
         * linux ps -ef | grep xxx
         * 通过jps -l获得进程号,然后jstack 进程号 查看线程状态
         */
    }

    private static class HoldLock implements Runnable{
        private Object lockA;
        private Object lockB;

        public HoldLock(Object lockA, Object lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有: " + lockA + "\t 尝试获得: " + lockB);

                try { TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB) {}
            }
        }
    }
}
