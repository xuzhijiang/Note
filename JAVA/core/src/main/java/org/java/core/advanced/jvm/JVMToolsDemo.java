package org.java.core.advanced.jvm;

import java.util.concurrent.TimeUnit;

public class JVMToolsDemo {
    // VM Options: -Xms10m -Xmx10m
    // 把参数调小是为了可以频繁触发gc
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("XXXXXXXXX");
            TimeUnit.SECONDS.sleep(1L);
        }
    }
}
