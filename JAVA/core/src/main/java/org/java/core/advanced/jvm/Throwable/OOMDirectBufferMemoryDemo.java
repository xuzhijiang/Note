package org.java.core.advanced.jvm.Throwable;

import java.nio.ByteBuffer;

/**
 * 默认堆外内存(直接内存)为物理内存的1/4
 *
 * -XX:MaxDirectMemorySize用于设置直接内存的大小
 *
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 */
public class OOMDirectBufferMemoryDemo {

    public static void main(String[] args) throws InterruptedException {
        // 打印最大的直接内存
        System.out.println("当前的maxDirectMemory: " + (sun.misc.VM.maxDirectMemory() / (double)1024 / 1024) + "MB" );

        Thread.sleep(3000L);
        // -XX:MaxDirectMemorySize=5m 我们故意使坏,实际使用6M
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
        // Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
        // 这个错误说白了就是堆外内存不够用的错误
    }
}
