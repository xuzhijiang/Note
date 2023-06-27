package com.gmall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedissonReadWriteLockTests {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testReadWriteLock() {
        MyShareData myShareData = new MyShareData(redissonClient);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myShareData.put(0, 0);
            }, String.valueOf(i)).start();
        }

        try { Thread.sleep(1000L); } catch (InterruptedException e) { e.printStackTrace();}

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myShareData.get(0);
            }, String.valueOf(i)).start();
        }

        try { Thread.sleep(50000L); } catch (InterruptedException e) { e.printStackTrace();}
    }

    private static class MyShareData {
        private Map<Integer, Integer> map = new HashMap<>();
        private RedissonClient redissonClient;

        public MyShareData(RedissonClient redissonClient) {
            this.redissonClient = redissonClient;
        }

        public void put(Integer key, Integer value) {
            RReadWriteLock rwLock = redissonClient.getReadWriteLock("haha");
            RLock wLock = rwLock.writeLock();
            wLock.lock();

            // 业务逻辑 START
            System.out.println(Thread.currentThread().getName() + " - " + "写入...");
            try { Thread.sleep(4000L); } catch (InterruptedException e) { e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + " - " + "写入完成");
            // 业务逻辑 END

            wLock.unlock();
        }

        public void get(Integer key) {
            RReadWriteLock rwLock = redissonClient.getReadWriteLock("haha");
            RLock rLock = rwLock.readLock();
            rLock.lock();

            // 业务逻辑 START
            System.out.println(Thread.currentThread().getName() + " - " + "读取...");
            try { Thread.sleep(7000L); } catch (InterruptedException e) { e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + " - " + "读取完成");
            // 业务逻辑 END

            rLock.unlock();
        }
    }
}
