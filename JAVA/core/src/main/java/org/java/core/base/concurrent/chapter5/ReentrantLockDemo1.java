package org.java.core.base.concurrent.chapter5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread("Thread A") {
            @Override
            public void run() {
                lock.lock();//加锁
                try{
                    work();//work
                } finally {
                    lock.unlock();
                }
            }
        }.start();

        new Thread("Thread B"){
            @Override
            public void run() {
                lock.lock();//加锁
                try{
                    work();//work
                }finally {
                    lock.unlock();//释放锁
                }

            }
        }.start();
        // 从输出结果中可以看到，A获取到锁，先执行，
        // 而必须等到A执行完成之后，B才能执行。因此Lock对象的实际上是一个独占锁。
    }


    public static void work() {
        try {
            System.out.println(Thread.currentThread().getName()+" started to work,currrentTime:"+System.currentTimeMillis());
            Thread.currentThread().sleep(1000);
            System.out.println(Thread.currentThread().getName()+" wnd work,currrentTime:"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
