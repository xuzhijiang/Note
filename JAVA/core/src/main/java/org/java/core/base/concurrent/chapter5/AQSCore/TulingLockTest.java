package org.java.core.base.concurrent.chapter5.AQSCore;

public class TulingLockTest {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    warehouse.deductStock();
                }
            }, "thread-" + i +"----").start();
        }
    }

    /**
     * 仓库
     */
    private static class Warehouse {
        // 商品的数量
        private int count = 10;

        private TulingLock lock = new TulingLock();

        /**
         * 减库存
         * @return
         */
        public void deductStock() {
            lock.lock();
            if (count > 0) {
                System.out.println("获得第 " + count + " 件商品");
                count--;
            } else {
                System.out.println("商品已卖完");
            }
            lock.unlock();
        }
    }

}
