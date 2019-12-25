package org.distributed.core.lock.testlock.two;

import org.distributed.core.lock.zookeeperlock.two.ZkDistributedLock;
import org.distributed.core.lock.zookeeperlock.two.ZkLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
