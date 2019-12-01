package org.java.core.advanced.jvm;

import org.junit.Test;

import java.util.Random;

public class ErrorAndException {

    /**
     * 引发Error: java.lang.StackOverflowError
     */
    @Test
    public void testStackOverflowError() {
        method1();
    }

    /**
     * 捕获Error: java.lang.StackOverflowError
     */
    @Test
    public void catchStackOverflowError()  {
        try {
            method1();
        } catch (Error e) {
            System.out.println("有Error发生");
            // 打印栈的追踪信息
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("i= " + i);
        }
    }

    /**
     * 一直递归把栈给撑爆了,就会导致StackOverflowError
     */
    public void method1() {
        method1();
    }


    public static void main(String[] args) {
        //testOOMError0();
        testOOMError1();
    }

    /**
     * VM参数：-Xms8m -Xmx8m -XX:+PrintGCDetails
     * 引发: java.lang.OutOfMemoryError: Java heap space
     */
    public static void testOOMError0() {
        String str = "aaaaaaaaaaaaaaaaaa";
        while (true) {
            str += str + new Random().nextInt(88888888) + new Random().nextInt(9999999);
        }
    }

    /**
     * VM参数：-Xms8m -Xmx8m -XX:+PrintGCDetails
     * 引发: java.lang.OutOfMemoryError: Java heap space
     */
    public static void testOOMError1() {
        // 40MB
        byte[] bytes = new byte[40 * 1024 * 1024];
    }

}
