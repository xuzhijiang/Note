package org.java.core.advanced.DesignPatterns.creational.singleton;

/**
 * instance 采用 volatile 关键字修饰也是很有必要的， instance = new Singleton(); 
 * 这段代码其实是分为三步执行：
 *
 * 1. 为 instance 分配内存空间
 * 2. 初始化 instance
 * 3. 将 instance 指向分配的内存地址
 *
 * 但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。指令重排在单线程环境下不会出现问题，
 * 但是在多线程环境下会导致一个线程获得还没有初始化的实例。例如，线程 T1 执行了 1 和 3，
 * 此时 T2 调用 getInstance() 后发现 instance 不为空，因此返回 instance，
 * 但此时 instance 还未被初始化,此时使用instance就会出错.
 *
 * 使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。
 */
public class SingletonDemo02 {

    // 加上volatile是为了禁止指令重排,防止对象还没有初始化完成就开始使用
    // 防止当一条线程访问instance不为null时,可能由于instance实例未必完成初始化,就开始使用,以致于使用的时候报错
    private static volatile SingletonDemo02 instance = null;

    private SingletonDemo02() {
        System.out.println("线程" + Thread.currentThread().getName() + "\t 初始化 SingletonDemo01");
    }

    // DCL(Double Check Lock-双重锁检查机制/双重检查懒加载的单例模式)
    public static SingletonDemo02 getInstance() {
        if (instance == null) {
            // 多个线程有可能都等在这里，这个时候singleton有可能还是null，
            // 所以内部又加了一层判断是否为null的if判断
            synchronized (SingletonDemo02.class) { // 类对象加锁保证只创建一个实例
                if (instance == null) {
                    instance = new SingletonDemo02();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                SingletonDemo02.getInstance();
            }, String.valueOf(i)).start();
        }
    }

}
