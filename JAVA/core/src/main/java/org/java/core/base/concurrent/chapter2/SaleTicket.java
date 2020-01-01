package org.java.core.base.concurrent.chapter2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目: 三个售票员    卖出      30张票
 *      多线程编程的企业级套路+模板
 *
 * 1.   在高内聚和低耦合的前提下,线程         操作(资源类对外暴露的方法)            资源类
 */
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
                try { Thread.sleep(50L); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
                try { Thread.sleep(50L); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
                try { Thread.sleep(50L); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }, "C").start();
    }

    /**
     * 资源类
     */
    private static class Ticket {
        private int number = 30;
        private Lock lock = new ReentrantLock();

        public void saleTicket() {
            lock.lock();
            try {
                if (number > 0) {
                    System.out.println(Thread.currentThread().getName() + "\t 卖掉第" + (number--) + "张票,剩余: " + number + "张票");
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
