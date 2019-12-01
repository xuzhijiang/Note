package org.distributed.core.lock.testlock;

// 支付系统
public class Payment {

    public void pay() {
        System.out.println(Thread.currentThread().getName() +"支付成功");
    }
}
