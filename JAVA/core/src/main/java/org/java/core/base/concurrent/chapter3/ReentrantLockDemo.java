package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁示例,包括synchronized和ReentrantLock两种可重入锁
 */
public class ReentrantLockDemo {

    private static class Phone implements Runnable{
        public synchronized void sendSms() {
            System.out.println(Thread.currentThread().getName() + "\t 发送Sms");
            sendEmail();
        }

        public synchronized void sendEmail() {
            System.out.println(Thread.currentThread().getName() + "\t 发送Email");
        }

        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            get();
        }

        private void get() {
            lock.lock();
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t invoke GET");
                set();
            } finally {
                lock.unlock();
                // lock()和unlock()一定要配对
                // 如果一个线程lock了2次,那么释放的时候也要unlock2次.否则锁就不会被完全释放,会被当前线程一直占用着.
                lock.unlock();
            }
        }

        private void set() {
             lock.lock();
             try {
                System.out.println(Thread.currentThread().getName() + "\t invoke SET");
             } finally {
                 lock.unlock();
             }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendSms();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSms();
        }, "t2").start();

        TimeUnit.SECONDS.sleep(2L);
        System.out.println();
        System.out.println();

        new Thread(phone, "t3").start();
        new Thread(phone, "t4").start();
    }
}
