package org.distributed.core.lock.redislock.simpleImpl;

import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// 执行lua脚本是原子性的.
public class SimpleRedisLock implements Lock {

    private static String LOCK_NAME = "LOCK";

    // 这个可以是动态传入的,这里为了方便写成固定的.
    private static String REQUEST_ID = "request_id";

    ThreadLocal<Jedis> jedis = new ThreadLocal<>();

    @Override
    public void lock() {
        jedis.set(new Jedis("localhost"));

        while (true) {
            if (tryLock()) {
                return;
            } else {
                System.out.println(Thread.currentThread().getName() + "等待锁....");
            }
        }
    }

    @Override
    public boolean tryLock() {
        // setnx如果key不存在,则创建成功返回1,如果key已经存在,则返回0
        //jedis.set(LOCK_NAME, REQUEST_ID, "NX", "PX", 3000);
        Long value = jedis.get().setnx(LOCK_NAME, REQUEST_ID);
        // 这里没有设置超时时间,而且还有很多其他问题,这个例子只是简单的演示
        // 可能的问题就是虽然可以给这个key设置超时时间,如果超时时间过了,任务还没有执行完成怎么办?
        // 这个时候其他线程还是可以拿到锁,这样并发还是有问题的.

        if (value == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        String value = jedis.get().get(LOCK_NAME);
        if (REQUEST_ID.equals(value)) {
            jedis.get().del(LOCK_NAME);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
