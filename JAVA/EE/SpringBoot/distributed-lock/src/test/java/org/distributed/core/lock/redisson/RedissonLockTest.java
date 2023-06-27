package org.distributed.core.lock.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redisson的分布式可重入锁RLock实现了java.util.concurrent.locks.Lock接口，同时还支持自动过期解锁。
 */
public class RedissonLockTest {

    static RLock lock;

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.32.150:6379");
        //config.useClusterServers().addNodeAddress():
        RedissonClient redissonClient = Redisson.create(config);
        lock = redissonClient.getLock("lock");
    }

    private static int count = 200;

    private static CountDownLatch countDownLatch = new CountDownLatch(count);

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
            Thread.currentThread().join(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("计算了 " + atomicInteger.get() + " 次");
        System.out.println("========>结果: " + num);
    }

    public static void testHighCurrent() {
        try {
            // 支持过期解锁功能
            // 10秒钟以后自动解锁,无需调用unlock方法手动解锁
            // lock.lock(10, TimeUnit.SECONDS);

            // 尝试加锁，最多等待10秒，上锁以后3秒自动解锁
            // 第一个参数是等待时间,第二个时间是锁的租期,也就是过期时间(leaseTime)
//            lock.tryLock(10, 3, TimeUnit.SECONDS);
            lock.lock();
            System.out.println();
            System.out.println(Thread.currentThread().getName() + " ========>获得锁 ");
            calculate();
            System.out.println(Thread.currentThread().getName() + " ========>释放锁 ");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static int num = 0;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void calculate() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
        atomicInteger.getAndIncrement();
    }

}
