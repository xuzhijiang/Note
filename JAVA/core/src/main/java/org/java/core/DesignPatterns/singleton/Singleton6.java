package org.java.core.DesignPatterns.singleton;

// 懒汉式：
public class Singleton6 {

    private static Singleton6 instance = null;

    private Singleton6() {
        System.out.println("线程" + Thread.currentThread().getName() + "\t 初始化 SingletonDemo01");
    }

    // 高并发模式下,加上synchronized太重了,会把整个方法都锁了,会降低并发性能
    public synchronized static Singleton6 getInstance() {
        if (instance == null) {
            // 真正需要控制的代码是这行
            instance = new Singleton6();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                Singleton6.getInstance();
            }, String.valueOf(i)).start();
        }
    }

}
