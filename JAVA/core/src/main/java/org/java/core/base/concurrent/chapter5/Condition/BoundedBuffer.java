package org.java.core.base.concurrent.chapter5.Condition;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    final Lock lock = new ReentrantLock();

    // Condition 实例实质上被绑定到一个锁上。
    // 要为特定 Lock 实例获得 Condition 实例，可以使用其 newCondition() 方法。
    final Condition canPut = lock.newCondition();

    // 我们喜欢在单独的等待 set 中保存 put 线程和 take 线程，
    // 这样就可以在缓冲区中的项或空间变得可用时利用最佳规划，一次只通知一个线程。
    // 可以使用两个 Condition 实例来做到这一点。
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
     * 作为一个示例，假定有一个有界的缓冲区，它支持 put 和 take 方法。
     * 如果试图在空的缓冲区上执行 take 操作，则在某一个项变得可用之前，线程将一直阻塞；
     * @return
     * @throws InterruptedException
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
        for(int i=0;i<8;i++){

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
