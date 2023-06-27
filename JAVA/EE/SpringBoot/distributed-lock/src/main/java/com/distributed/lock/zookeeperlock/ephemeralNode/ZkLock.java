package com.distributed.lock.zookeeperlock.ephemeralNode;

/**
 * 使用临时节点实现分布式锁的功能
 */
public interface ZkLock {
    void lock();
    void unlock();
}
