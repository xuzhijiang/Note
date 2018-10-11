package org.java.core.advanced.jvm.classloader;

/**
 * 子类的初始化过程和主动引用：
 * 	子类初始化，先初始化父类
 *
 */
public class InitMain {
	public static void main(String[] args) {
//        new Child();// new关键字初始化 注释开启和未开启作比较
        System.out.println("======");
        System.out.println(Child.v); // 此时Child已经被加载，但未被初始化，因为只打印了Parent init，没有打印Child init
    }
}

class Parent {
    static {
        System.out.println("Parent init");
    }
    public static int v = 100;
}

class Child extends Parent {
    static {
        System.out.println("Child  init");
    }
}