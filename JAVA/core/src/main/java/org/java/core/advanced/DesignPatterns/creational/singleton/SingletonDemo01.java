package org.java.core.advanced.DesignPatterns.creational.singleton;

public class SingletonDemo01 {

    private static SingletonDemo01 instance = null;

    private SingletonDemo01() {
        System.out.println("线程" + Thread.currentThread().getName() + "\t 初始化 SingletonDemo01");
    }

    // 高并发模式下,加上synchronized太重了,会把整个方法都锁了,会降低并发性能
    public synchronized static SingletonDemo01 getInstance() {
        if (instance == null) {
            // 真正需要控制的代码是这行
            instance = new SingletonDemo01();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                SingletonDemo01.getInstance();
            }, String.valueOf(i)).start();
        }
    }

}
