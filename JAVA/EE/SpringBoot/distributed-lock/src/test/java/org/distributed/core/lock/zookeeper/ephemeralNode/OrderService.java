package org.distributed.core.lock.zookeeper.ephemeralNode;

import com.distributed.lock.zookeeperlock.ephemeralNode.ZkDistributedLock;
import com.distributed.lock.zookeeperlock.ephemeralNode.ZkLock;

public class OrderService {

    private OrderNumCreateUtil orderNumCreateUtil = new OrderNumCreateUtil();

    private ZkLock zkLock = new ZkDistributedLock();

    public void getOrderNumber() {
        zkLock.lock();
        try {
            System.out.println("获得订单编号---------> " + orderNumCreateUtil.getOrdNumber());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkLock.unlock();
        }
    }
}
