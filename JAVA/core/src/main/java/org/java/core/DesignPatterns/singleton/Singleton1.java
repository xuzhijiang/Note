package org.java.core.DesignPatterns.singleton;

/**
 * 推荐使用这种方式
 *
 * 饿汉式：
 * 	在类初始化时直接创建实例对象，不管你是否需要这个对象都会创建
 * 	(饥不可耐,不管用不用这个实例,反正一加载类我就创建)
 *
 * （1）构造器私有化
 * （2）自行创建，并且用静态变量保存
 * （3）向外提供这个实例
 * （4）强调这是一个单例，我们可以用final修改
 */
public class Singleton1 {

    // 加载类的时候就要初始化这个实例,但是我感觉这种是可以接受的,不推荐使用DCL的方式.
    public static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {}

    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}
