package org.java.core.advanced.jvm;

/**
 * 哪些对象可以作为GCRoots根节点
 */
public class GCRootsDemo {

    private  byte[] byteArray = new byte[100 * 1024 * 1024];

    // 方法区中的静态变量
    private static Object o2;
    // 方法区中的静态常量引用的对象
    private final static Object o3 = new Object();

    public static void main(String[] args) {
        m1();
    }

    private static void m1() {
        // 虚拟机栈中引用的对象
        GCRootsDemo o1 = new GCRootsDemo();
    }
}
