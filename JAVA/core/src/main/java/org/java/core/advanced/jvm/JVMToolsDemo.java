package org.java.core.advanced.jvm;

import java.util.concurrent.TimeUnit;

public class JVMToolsDemo {
    // VM Options: -Xms10m -Xmx10m
    // 把参数调小是为了可以频繁触发gc
    public static void main(String[] args) throws InterruptedException {
        // array对应jmap的: [Lorg.java.core.advanced.jvm.JVMToolsDemo
        // 10个具体的对象用这个表示: org.java.core.advanced.jvm.JVMToolsDemo
        JVMToolsDemo[] array = new JVMToolsDemo[10];
        for (int i = 0; i < 10; i++) {
            array[i] = new JVMToolsDemo();
        }
        while (true) {
            System.out.println("XXXXXXXXX");
            TimeUnit.SECONDS.sleep(3L);
        }
    }
}
