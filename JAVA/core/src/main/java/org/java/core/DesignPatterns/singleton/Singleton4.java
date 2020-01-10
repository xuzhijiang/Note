package org.java.core.DesignPatterns.singleton;

/*
 * 懒汉式：
 * 	延迟创建这个实例对象
 *
 * (1)构造器私有化
 * (2)用一个静态变量保存这个唯一的实例
 * (3)提供一个静态方法，获取这个实例对象
 */
public class Singleton4 {
    private static Singleton4 INSTANCE;

    private Singleton4() {}

    public static Singleton4 getInstance() {
        if (INSTANCE == null) {
            // 这种方式有线程安全问题
            try { Thread.sleep(2000L); } catch (InterruptedException e) { e.printStackTrace();}

            INSTANCE = new Singleton4();
        }
        return INSTANCE;
    }
}
