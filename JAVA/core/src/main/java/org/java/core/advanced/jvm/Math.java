package org.java.core.advanced.jvm;

import org.java.core.advanced.proxy.javassist.User;

public class Math {
    // Math.class这个字节码文件最终会被加载到方法区。
    // 方法区包含： 常量 + 静态变量 + 类元信息(类的修饰符，构造函数，字段，方法名等信息，统称为类元信息)
    public static int initData = 666;     // static静态常量是存放在方法区的

    // static静态变量也是存放在方法区的
    // new User()存放在堆中，user这个静态变量是存放在方法区，这个也就是方法区和堆的关联
    public static User user = new User();
    // new User()这个对象包含了对象头，对象头中有一个叫Klass pointer(类型指针)的，
    // 就是指向方法区中的类元信息，也就是把
    // User.class在方法区中的类元信息的具体地址存放在了堆中new User()对象中的Klass pointer中。

    public int compute() {// 每一个方法对应一个栈帧
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }
    public static void main(String[] args) {
        Math math = new Math();
        math.compute();
        System.out.println("test");
    }
}
