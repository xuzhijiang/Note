package com.distributed.lock.zookeeperlock.ephemeralNode;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * 模板模式抽象类(模板模式)
 */
public abstract class ZkAbstractTemplateLock implements ZkLock{

    private static final String ZK_SERVER = "192.168.32.150:2181";

    private static int CONNECTION_TIMEOUT = 45 * 1000;

    protected String path = "/zk_lock_0401";

    protected ZkClient zkClient;

    protected CountDownLatch countDownLatch;

    public ZkAbstractTemplateLock() {
        zkClient = new ZkClient(ZK_SERVER, CONNECTION_TIMEOUT);
    }

    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName() + "\t 成功获取到锁");
        } else {
            waitLock();
            lock();
        }
    }

    protected abstract boolean tryLock();

    protected abstract void waitLock();

    @Override
    public void unlock() {
        if (zkClient != null) {
            // 相当于在zkClient上输入quit,这样临时节点就会被自动删除
            zkClient.close();
        }
        System.out.println(Thread.currentThread().getName() + "\t 释放锁");
        System.out.println();
        System.out.println();
    }

}
