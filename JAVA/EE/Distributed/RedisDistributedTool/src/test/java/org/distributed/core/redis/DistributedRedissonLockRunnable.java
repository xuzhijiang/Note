package org.distributed.core.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Redisson的分布式可重入锁RLock实现了java.util.concurrent.locks.Lock接口，同时还支持自动过期解锁。
 */
public class DistributedRedissonLockRunnable implements Runnable {

    @Override
    public void run() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //config.useClusterServers().addNodeAddress():
        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = redissonClient.getLock("LockA");
        try {
            // 支持过期解锁功能
            // 10秒钟以后自动解锁,无需调用unlock方法手动解锁
            // lock.lock(10, TimeUnit.SECONDS);

            // 尝试加锁，最多等待10秒，上锁以后3秒自动解锁
            // 第一个参数是等待时间,第二个时间是锁的租期,也就是过期时间(leaseTime)
            lock.tryLock(10, 3, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + " 获得锁 do something ");
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " 释放锁 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            redissonClient.shutdown();
        }
    }
}
