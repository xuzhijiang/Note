package org.distributed.core.lock.zookeeperlock.two;

public interface ZkLock {
    void lock();
    void unlock();
}
