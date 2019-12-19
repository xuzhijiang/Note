package org.java.core.base.concurrent.chapter5.ReentrantLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁代码示例
 */
public class ReentrantReadWriteLockDemo {

    /**
     * 资源类
     */
    public static class MyCache {
        /**
         * 保证可见性
         */
        private volatile Map<String, Object> map =  new HashMap<>();
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        /**
         * 写
         */
        public void put (String key, Object value) {
            rwLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 正在写入: " + key);
                // 模拟网络延时
                try { TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e) { e.printStackTrace(); }
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + "\t 写入完成");
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        /**
         * 读
         */
        public void get(String key) {
            rwLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 正在读取: " + key);
                // 模拟网络延时
                try { TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e) { e.printStackTrace(); }
                Object value = map.get(key);
                System.out.println(Thread.currentThread().getName() + "\t 完成读取: " + value);
            } finally {
                rwLock.readLock().unlock();
            }
        }

        public void clearCache() {
            map.clear();
        }
    }

    /**
     * 多个线程同时操作 一个资源类没有任何问题 所以为了满足并发量
     * 在没有写操作的时候，应该允许多个线程能同时读取共享资源
     *
     * 但是如果有一个线程想去写共享资源来  就不应该有其他线程可以对资源进行读或写
     * <p>
     * 小总结:
     * 读 读能共存: 一个线程读，其他线程也可以读同一资源
     * 读 写不能共存: 一个线程读资源，其他写线程就不能去写.(写线程会被阻塞)
     * 写 写不能共存: 一个线程去写资源，其他的线程不能去读，也不能去写.
     * 写操作 原子+独占 整个过程必须是一个完成的统一整体 中间不允许被分割 被打断
     **/
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int tmp = i;
            new Thread(() -> {
                myCache.put(String.valueOf(tmp), tmp);
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tmp = i;
            new Thread(() -> {
                myCache.get(String.valueOf(tmp));
            }, String.valueOf(i + 5)).start();
        }
    }
}
