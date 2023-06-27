package org.distributed.core.lock.testlock;

import com.distributed.lock.mysqllock.MySqlForUpdateLock;
import com.distributed.lock.redislock.simpleImpl.SimpleRedisLock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {

    private static int count = 20;

    private static CountDownLatch countDownLatch = new CountDownLatch(count);

    static Lock lock = new ReentrantLock();
//    static MySqlForUpdateLock lock = new MySqlForUpdateLock(); // 性能太差!!
//    static Lock lock = new SimpleRedisLock();

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    testHighCurrent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println("=========>发令枪减一===> " + countDownLatch.getCount());
            countDownLatch.countDown();
        }

        try {
            Thread.currentThread().join(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("计算了 " + atomicInteger.get() + " 次");
        System.out.println("========>结果: " + num);
    }

    public static void testHighCurrent() {
//        System.out.println(Thread.currentThread().getName() + "开始抢锁计算!!!");
        lock.lock();
        calculate();
        lock.unlock();
    }


    private static int num = 0;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void calculate() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
        atomicInteger.getAndIncrement();
//        System.out.println("第" + atomicInteger.get() + "次计算=====>");
    }
}
