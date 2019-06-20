package org.java.core.advanced.jvm.classloader;

public class InitMain {
	public static void main(String[] args) {
        // 使用new关键字初始化Child, 可以通过注释掉此行，和不注释对比打印
        // new Child(); // new Child()的时候是先初始化Parent.class, 然后才是初始化Child.class

        System.out.println("======");
        System.out.println(Child.v); // 此时Child.class已经被加载，但未被初始化，因为只打印了Parent init，没有打印Child init
    }
}

class Parent {

    public static int v = 100;

    /**
     * 如果使用了 final 修饰的常量，再使用时父类也不会初始化
     */
     //public final static int v = 100;

    static {//Parent.class的初始化过程
        System.out.println("Parent init");
    }
}

class Child extends Parent {
    // 注意Child.class被加载但不一定被初始化
    static {// Child.class的初始化过程
        System.out.println("Child  init");
    }
}