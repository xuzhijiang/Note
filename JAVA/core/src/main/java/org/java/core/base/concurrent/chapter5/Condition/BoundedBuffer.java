package org.java.core.base.concurrent.chapter5.Condition;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现Java 阻塞队列
 *
 * 在自己实现之前先搞清楚阻塞队列的几个特点：
 * 基本队列特性：先进先出。
 * 写入队列空间不可用时会阻塞。
 * 获取队列数据为空时将阻塞。
 *
 * 实现队列的方式多种，总的来说就是数组和链表；其实我们只需要搞清楚其中一个即可，不同的特性主要表现为数组和链表的区别。这里是使用数组实现.
 *
 * 注意要实现循环队列.以及put和take都要改变同一个count变量,所以要用同一把锁,否则会出现count数据不一致.
 *
 * 错误的实现案例: https://segmentfault.com/a/1190000020005820, 这个案例会出现count数据不一致以及死锁.因为put和take使用了2个不同的锁.
 */
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();

    // Condition 实例实质上被绑定到一个锁上。
    // 要为特定 Lock 实例获得 Condition 实例，可以使用其 newCondition() 方法。
    final Condition canPut = lock.newCondition();

    final Condition canTake = lock.newCondition();

    final Object[] items = new Object[100];
    // count: 缓冲区中元素的个数
    // putptr: put操作的指针
    // takeptr: take操作的指针
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException{
        lock.lock();
        try{
            // 如果试图在满的缓冲区上执行 put 操作，则在有空间变得可用之前，当前线程将一直阻塞。
            while(count == items.length){
                canPut.await();
            }

            items[putptr] = x;//往缓存区中存放数据
            if (++putptr == items.length) {
                putptr = 0;//修改下一次存储数据的存放位置,类似于循环队列
            }
            count++;//修改缓存区中数据的数量
            canTake.signal();//通知可以take数据了，也就是有新元素可以被take了
        } finally {
            lock.unlock();
        }
    }

    /**
     * 假定有一个有界的缓冲区，它支持 put 和 take 方法。
     * 如果试图在空的缓冲区上执行 take 操作，则在某一个项变得可用之前，线程将一直阻塞；
     */
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0){
                canTake.await();
            }

            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;//修改下一次获取元素的位置,类似于循环队列
            }
            count--;
            canPut.signal();//通知可以put数据了，也就是有空间了
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final BoundedBuffer boundedBuffer = new BoundedBuffer();

        for(int i=0;i<100;i++){
            // 偶数put,奇数take
            if((i%2) == 0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boundedBuffer.put("aaaa" + new Random().nextInt(100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, "Thread - " + i + " - ").start();
            }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("-------" + boundedBuffer.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, "Thread - " + i + " - ").start();
            }

        }
    }
}
