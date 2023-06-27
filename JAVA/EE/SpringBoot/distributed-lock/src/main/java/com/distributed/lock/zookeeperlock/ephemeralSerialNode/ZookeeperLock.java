package com.distributed.lock.zookeeperlock.ephemeralSerialNode;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ZookeeperLock {

    private ZkClient zkClient;

    public ZookeeperLock() {
        // 第二个参数是SessionTimeout的意思,也就是超时时间的意思
        // 如果我们创建的是临时节点,client断开和server的连接之后,这个临时节点不会马上被删除,而是要等到超过这个超时时间之后才会被删除
        // 第三个参数是连接超时时间
        zkClient = new ZkClient("192.168.32.150:2181", 30000, 20000);
        // 到服务器上创建tuling-lock这个节点
    }

    // 1. 获得锁
    public Lock lock(String lockId, long timeout) {
        Lock lockNode = createLockNode(lockId);
        // 创建完成后,要激活锁
        lockNode = tryActiveLock(lockNode);
        if (!lockNode.isActive()) {
            // 没有激活就要等待timeout
            // 这个时候会去排队等待,节点的创建时有顺序的,注意这个超时时间要足够,
            // 否则超时时间一到,线程就会被唤醒,可能造成锁失效.
            try {
                synchronized (lockNode) {
                    log.info(Thread.currentThread().getName() + "没有获取到锁,进行休眠======>>>>: " + lockNode.getPath());
                    lockNode.wait(timeout);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lockNode;
    }

    // 2. 激活锁
    public Lock tryActiveLock(Lock lockNode) {
        // 获得/tuling-lock这个path对应节点的所有子节点
        List<String> list = zkClient.getChildren("/tuling-lock")
                .stream()   // 转成stream流 (java8 stream表达式的写法)
                .sorted()   // 再进行排序
                .map(p -> "/tuling-lock/" + p) // 排完序之后再把每个元素变成节点的完整路径
                .collect(Collectors.toList());
        // 判断list的大小
        // 获得第一个
        String firstPath = list.get(0);
        // 判断是否获得锁
        if (firstPath.equals(lockNode.getPath())) {
            log.info(Thread.currentThread().getName() + "====>> 激活节点 ======>>>>: " + firstPath);
            // 说明获得锁了,把锁的状态设为激活
            lockNode.setActive(true);
        } else {
            // 获取到前一个节点
            String upNodePath = list.get(list.indexOf(lockNode.getPath()) - 1); // 拿到前一个节点
            log.info(Thread.currentThread().getName() + "没有获取到锁===>{}, 进行监听===>{}======>>>>: ", lockNode.getPath(), upNodePath);
            // 添加对前一个节点的监听
            zkClient.subscribeDataChanges(upNodePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {}

                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    // 事件处理与心跳在同一线程,如果debug时占用太多时间,将导致本节点被删除,从而影响锁逻辑
                    log.info(Thread.currentThread().getName() + "====>收到 {} 被删除了的事件===", dataPath);
                    // 在前一个节点被删除的时候,再去尝试激活锁
                    Lock lock = tryActiveLock(lockNode);
                    synchronized (lockNode) {
                        if (lock.isActive()) {
                            log.info(Thread.currentThread().getName() + "唤醒======>>>>: " + lockNode.getPath());
                            lockNode.notify();
                        }
                    }
                    zkClient.unsubscribeDataChanges(upNodePath, this);
                }
            });
        }

        return lockNode;
    }

    // 3. 释放锁
    public void unlock(Lock lock) {
        if (lock.isActive()) {
            zkClient.delete(lock.getPath());
        }
    }

    public Lock createLockNode(String lockId) {
        // 创建临时序号节点
        // 节点路径   w: 创建写锁
        // 返回值是节点的完整路径: /tuling-lock/nana0000000000
        String path = zkClient.createEphemeralSequential("/tuling-lock/" + lockId, "w");
        log.info(Thread.currentThread().getName() + " 创建临时序号节点: " + path);
        Lock lock = new Lock();
        lock.setActive(false);
        lock.setLockId(lockId);
        lock.setPath(path);
        return lock;
    }

    public void createParentNode(String parentNode) {
        boolean exists = zkClient.exists(parentNode);
        if (!exists) {
            zkClient.createPersistent(parentNode);
        }
    }
}
