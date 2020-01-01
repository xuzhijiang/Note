package org.java.core.advanced.DesignPatterns.creational.singleton;

/**
 * 推荐使用这种
 */
public class SingletonDemo03 {
    // 弊端: 加载类的时候就要初始化这个实例,但是我感觉这种是可以结构的,不推荐使用DCL的方式.
    private static SingletonDemo03 instance = new SingletonDemo03();

    private SingletonDemo03() {}

    public static SingletonDemo03 getInstance() {
        return instance;
    }
}
