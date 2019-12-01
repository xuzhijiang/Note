package org.distributed.core.lock.zookeeperlock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import java.util.List;
import java.util.stream.Collectors;

// 工作中可能你没有使用过Zookeeper实现分布式锁,但是面试的时候动不动就问这些问题
// 面试官非常喜欢问zk是怎么进行主从选举的.节点类型也是比较喜欢问的.

// 这个示例是排它锁
public class ZookeeperLock {

    // 这个org.I0Itec.zkclient.ZkClient是org.apache.kafka中的.
    private ZkClient zkClient;

    public ZookeeperLock() {
        // 第二个参数是SessionTimeout的意思,也就是超时时间的意思
        // 如果我们创建的是临时节点,client断开和server的连接之后,这个临时节点不会马上被删除,而是要等到超过这个超时时间之后才会被删除
        // 第三个参数是连接超时时间
        zkClient = new ZkClient("ip:2181", 30000, 20000);
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
        // 获得这个path对应节点的所有子节点
        List<String> list = zkClient.getChildren("/tuling-lock") // 获得/tuling-lock的所有子节点
                .stream()   // 转成stream流 (java8 stream表达式的写法)
                .sorted()   // 再进行排序
                .map(p -> "/tuling-lock/" + p) // 排完序之后再把每个元素变成节点的完整路径
                .collect(Collectors.toList());

        // 获得第一个
        String firstPath = list.get(0);
        // 判断是否获得锁
        if (firstPath.equals(lockNode.getPath())) {
            // 说明获得锁了,把锁的状态设为激活
            lockNode.setActive(true);
        } else {
            // 添加对前一个节点的监听
            String upNodePath = list.get(list.indexOf(lockNode.getPath()) - 1); // 拿到前一个节点
            // 设置监听
            zkClient.subscribeDataChanges(upNodePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {}

                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    // 事件处理与心跳在同一线程,如果debug时占用太多时间,将导致本节点被删除,从而影响锁逻辑
                    System.out.println("删除节点: " + dataPath);
                    // 在前一个节点被删除的时候,再去尝试激活锁
                    Lock lock = tryActiveLock(lockNode);
                    synchronized (lockNode) {
                        if (lock.isActive()) {
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

    // 创建临时序号节点
    public Lock createLockNode(String lockId) {
        // 节点路径   w: 创建写锁
        String path = zkClient.createEphemeralSequential("/tuling-lock/" + lockId, "w");
        Lock lock = new Lock();
        lock.setActive(false);
        lock.setLockId(lockId);
        lock.setPath(path);
        return lock;
    }

}
