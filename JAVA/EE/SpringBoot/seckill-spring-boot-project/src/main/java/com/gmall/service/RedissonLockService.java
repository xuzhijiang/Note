package com.gmall.service;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonLockService {

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    // 测试redisson分布式锁是否正确
    public void useRedissonForLock() {
        //1、获取一把锁。只要各个代码，用的锁名一样即可
        RLock lock = redisson.getLock("lock");
        try {
            // lock.lock();//如果别人已经上锁了,当前client则一直阻塞等待.
            // 等待的过程中,可以感知别人删锁,一旦删锁。赶紧尝试去加锁。类似于mq的发布订阅模式（实时感知）。
            lock.lock(3, TimeUnit.SECONDS);

            // 执行业务
            String num = redisTemplate.opsForValue().get("num");
            Integer i = Integer.parseInt(num);
            i = i+1;
            redisTemplate.opsForValue().set("num",i.toString());
            // 执行业务结束
        } finally {
            lock.unlock();
        }
    }

    public boolean lock() {
        RLock lock = redisson.getLock("lock");
        // lock.lock();//默认是阻塞的。
        // lock.tryLock()；是非阻塞的，尝试一下，拿不到就算了。
        // boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS); 等待100s内只要能获取到锁，这个锁就10秒超时，

        //这个线程的任意期间都要获取这把锁，是直接使用的。说明这个锁是可重入锁。
        lock.lock();
        System.out.println("第一次锁");
        lock.lock();
        System.out.println("第二次锁");
        lock.lock();
        System.out.println("第三次锁");

        // 哪个线程加的锁一定要在这个线程解
        return  lock.tryLock();
    }

    public void unlock() {
        redisson.getLock("lock").unlock();
    }

    private String value = "hello";

    /**
     * 写锁一个，排它锁（独占锁）
     *
     * 读锁是一个共享锁。多个读操作可以同时进行.
     *
     * 有写锁的时候，只有写锁释放才能读。
     * 如果有多个写操作，就必须竞争写锁。拿到锁的才可以写,其余的没有拿到写锁的会被阻塞.
     *
     * 有读锁的时候如果要进行写操作,必须等到释放了读锁才可以 进行写操作.
     *
     * 怎么使用? 很简单: 读加读锁，写加写锁。
     */
    public String read() {
        // 测试读写锁最好用不同的浏览器,或者ab测试工具来测试
        String retVal = null;
        RReadWriteLock rwlock = redisson.getReadWriteLock("anyRWLock");
        RLock rLock = rwlock.readLock();
        rLock.lock();
        try {
            System.out.println("线程******: " + Thread.currentThread().getId() + " - 读取数据.....");
            Thread.sleep(7000L);
            retVal = value; // 相当于从数据库读
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程*******: " + Thread.currentThread().getId() + " - 读取数据完成");
            rLock.unlock();
        }
        return retVal;
    }

    public String write() {
        RReadWriteLock rwlock = redisson.getReadWriteLock("anyRWLock");
        RLock wLock = rwlock.writeLock();
        wLock.lock();
        try {
            System.out.println("线程 -" + Thread.currentThread().getId() + " - 写入数据....");
            Thread.sleep(7000L);
            value = UUID.randomUUID().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程 - " + Thread.currentThread().getId() + " - 写入数据完成");
            wLock.unlock();
        }
        return value;
    }

    @PostConstruct
    public void initTestData() {
        redisTemplate.opsForValue().set("semaphore", "10"); // 设置10个车位

        redisTemplate.opsForValue().set("count", "10");
    }

    public Boolean park() {
        RSemaphore semaphore = redisson.getSemaphore("semaphore");
        try {
        semaphore.acquire(); // 获取许可(车位) ,因为初始化了10个,所以获取1个之后,redis中semaphore的值变成了9
//            semaphore.acquire(5); // 占用几个停车位(许可)
         // 注意: redis中semaphore的key必须有值,acquire()方法才可以继续进行,否则就会一直阻塞等待.
            // 调用 semaphore.acquire()之后,semaphore值减1, 调用semaphore.acquire(5)之后, semaphore值减5
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean unpark() {
        RSemaphore semaphore = redisson.getSemaphore("semaphore");
        semaphore.release();
//         semaphore.release(5); // 释放几个停车位(许可)
        // 调用semaphore.release(), semaphore的值加1,许可增加一个
        return true;
    }

    public String shutDoor() {
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("count");
        // countDownLatch.trySetCount(10);
        try {
            System.out.println("大家快走,领导要锁门了.........");
            countDownLatch.await();
            // 等大家都走完...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "领导最后锁门....";
    }

    public boolean staffLeave() {
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("count");
        countDownLatch.countDown();
        System.out.println("溜了....");
        return true;
    }
}
