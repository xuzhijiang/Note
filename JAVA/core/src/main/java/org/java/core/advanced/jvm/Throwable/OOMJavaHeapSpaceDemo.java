package org.java.core.advanced.jvm.Throwable;

import java.util.Random;

/**
 * 引发: java.lang.OutOfMemoryError: Java heap space
 */
public class OOMJavaHeapSpaceDemo {

    public static void main(String[] args) {
        testOOMError0();
//        testOOMError1();
    }

    /**
     * VM参数：-Xms8m -Xmx8m -XX:+PrintGCDetails
     */
    public static void testOOMError0() {
        String str = "aaaaaaaaaaaaaaaaaa";
        while (true) {
            str += str + new Random().nextInt(88888888) + new Random().nextInt(9999999);
        }
    }

    /**
     * VM参数：-Xms8m -Xmx8m -XX:+PrintGCDetails
     */
    public static void testOOMError1() {
        // 40MB
        byte[] bytes = new byte[40 * 1024 * 1024];
    }

}
