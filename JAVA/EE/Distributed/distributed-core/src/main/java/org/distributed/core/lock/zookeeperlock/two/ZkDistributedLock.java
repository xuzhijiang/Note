package org.distributed.core.lock.zookeeperlock.two;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * 实现分布式锁的类
 */
public class ZkDistributedLock extends ZkAbstractTemplateLock {

    @Override
    protected boolean tryLock() {
        try {
            zkClient.createEphemeral(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void waitLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };
        zkClient.subscribeDataChanges(path, iZkDataListener);

        if (zkClient.exists(path)) {
            // 只能等待,不能往下走
            countDownLatch = new CountDownLatch(1);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        zkClient.unsubscribeDataChanges(path, iZkDataListener);
    }
}
