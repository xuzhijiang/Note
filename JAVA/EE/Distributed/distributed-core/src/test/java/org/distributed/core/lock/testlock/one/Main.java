package org.distributed.core.lock.testlock.one;

import org.distributed.core.lock.redislock.simpleImpl.SimpleRedisLock;
import org.distributed.core.lock.zookeeperlock.two.ZkLock;
import org.distributed.core.lock.zookeeperlock.two.ZkDistributedLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        // 多个用户去下单
        new Thread(new UserThread(), "user1").start();
        new Thread(new UserThread(), "user2").start();
    }

    static Lock lock = new ReentrantLock(); // 锁没问题
    // static Lock lock = new MysqlLock(); // 锁有问题
    //static Lock lock = new MySqlForUpdateLock(); // 锁有问题
//    static Lock lock = new SimpleRedisLock();

    static class UserThread implements Runnable {

        @Override
        public void run() {
            // 下单
            new Order().createOrder();
            lock.lock();
            // 减库存
            boolean result = new Stock().reduceStock();
            lock.unlock();
            if (result) {
                System.out.println(Thread.currentThread().getName() + "减库存成功");
                // 支付
                new Payment().pay();
            } else {
                System.out.println(Thread.currentThread().getName() +"减库存失败");
            }
        }
    }
}
