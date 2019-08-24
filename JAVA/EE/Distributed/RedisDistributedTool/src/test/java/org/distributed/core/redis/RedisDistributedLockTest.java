package org.distributed.core.redis;

import org.junit.Test;

public class RedisDistributedLockTest {

    public static void main(String[] args) {
        new RedisDistributedLockTest().redissonLockTest();
    }

    @Test
    public void redisLockTest() {
        for (int i = 0; i < 100; i++) {
            new Thread(new DistributedRedisLockRunnable(), "thread-" + i).start();
        }
    }

    /**
     *
     */
    @Test
    public void redissonLockTest() {
        for (int i = 0; i < 10; i ++) {
            new Thread(new DistributedRedissonLockRunnable(), "thread " + i).start();
        }
    }
}
