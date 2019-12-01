package org.distributed.core.lock.testlock;

// 订单
public class Order {

    public void createOrder() {
        System.out.println(Thread.currentThread().getName() +"创建订单");
    }
}
