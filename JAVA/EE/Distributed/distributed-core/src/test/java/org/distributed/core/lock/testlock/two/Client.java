package org.distributed.core.lock.testlock.two;

public class Client {
    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                new OrderService().getOrderNumber();
            }, String.valueOf(i)).start();
        }
    }
}
