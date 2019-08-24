package org.distributed.core.redis;

import java.util.concurrent.TimeUnit;

public class DistributedRedisLockRunnable implements Runnable{

    @Override
    public void run() {
        String lockName = "name";
        DistributedLock lock = new DistributedLock();
        System.out.println(Thread.currentThread().getName() + " 开始抢夺锁 ");
        String identifier = lock.acquireLock(lockName, 30000, 5000);
        if (null != identifier) {
            System.out.println(Thread.currentThread().getName() + " 获得锁成功!! " + identifier);
            try {
                TimeUnit.SECONDS.sleep(1);
                //boolean isRelease = lock.releaseLock(lockName, identifier);
                boolean isRelease = lock.releaseLockBylua(lockName, identifier);
                if (isRelease) {
                    System.out.println(Thread.currentThread().getName() + " 释放锁成功--- " + identifier);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
