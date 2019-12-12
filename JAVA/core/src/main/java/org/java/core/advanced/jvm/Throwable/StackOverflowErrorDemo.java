package org.java.core.advanced.jvm.Throwable;

import org.junit.Test;

public class StackOverflowErrorDemo {

    /**
     * 引发Error: java.lang.StackOverflowError (属于错误Error,不是Exception异常)
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

}
