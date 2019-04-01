package org.java.core.base.concurrent.chapter5;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 突破限制创建实例
 */
public class UnsafeDemo2 {

    private int i = 0;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); // Internal reference
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // 通过allocateInstance()方法，你可以创建一个类的实例，
        // 但是却不需要调用它的构造函数、初使化代码、各种JVM安全检查以及其它的一些底层的东西。即使构造函数是私有，
        // 我们也可以通过这个方法创建它的实例。

        // 这个对单例模式情有独钟的程序员来说将会是一个噩梦，它们没有办法阻止这种方式调用
        // This creates an instance of player class without any initialization
        Player p = (Player) unsafe.allocateInstance(Player.class);
        System.out.println(p.getAge()); // Print 0

        p.setAge(45); // Let's now set age 45 to un-initialized object
        System.out.println(p.getAge()); // Print 45
    }
}

class Player {
    private int age = 12;

    // 注意是private
    private Player() {
        this.age = 50;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}