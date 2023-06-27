package org.distributed.core.lock.zookeeper.ephemeralNode;

public class Client {
    public static void main(String[] args) {
        for (int i = 1; i <= 300; i++) {
            new Thread(() -> {
                new OrderService().getOrderNumber();
            }, String.valueOf(i)).start();
        }
    }
}
