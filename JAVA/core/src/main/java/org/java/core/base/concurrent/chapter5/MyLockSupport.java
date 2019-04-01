package org.java.core.base.concurrent.chapter5;

import java.util.concurrent.*;
import sun.misc.Unsafe;

// LockSupport源码分析(部分源码)

// 这个类提供的都是静态方法，且无法被实例化。
public class MyLockSupport {

    // 注意真实的LockSupport包是:package java.util.concurrent.locks;

    /**
     * 不可以实例化
     */
    private MyLockSupport() {} // Cannot be instantiated.

    // 在LockSupport中有两个私有的静态成员变量：unsafe和parkBlockerOffset
    // Hotspot implementation via intrinsics API

    // unsafe：是JDK内部用的工具类。它通过暴露一些Java意义上说“不安全”的功能给Java层代码，
    // 来让JDK能够更多的使用Java代码来实现一些原本是平台相关的、需要使用native语言（例如C或C++）
    // 才可以实现的功能。该类不应该在JDK核心类库之外使用。

    // java.util.concurrent.locks.LockSupport属于JDK信任的类，
    // 所以可以通过此getUnsafe()静态方法获得Unsafe实例
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    // 要先了解parkBlocker是干啥的.再理解偏移量
    //parkBlokcerOffset：parkBlocker的偏移量
    private static final long parkBlockerOffset;


    // 原来偏移量就算Thread这个类里面变量parkBlocker在内存中的偏移量。
    // 回顾上一节org.java.core.base.concurrent.chapter5.UnsafeDemo中直接通过内存偏移量给变量赋值的案例。
    // 但是，为什么要用偏移量来设置和获取对象？干吗不要直接写个get，set方法。多简单？
    // 仔细想想就能明白，这个parkBlocker就是在线程处于阻塞的情况下才会被赋值。
    // 线程都已经阻塞了，如果不通过这种内存的方法，而是直接调用线程内的方法，线程是不会回应调用的。
    static {
        try {
            parkBlockerOffset = unsafe.objectFieldOffset
                    (java.lang.Thread.class.getDeclaredField("parkBlocker"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private static void setBlocker(Thread t, Object arg) {
        // Even though volatile, hotspot doesn't need a write barrier here.
        unsafe.putObject(t, parkBlockerOffset, arg);
    }

    public static void unpark(Thread thread) {
        if (thread != null)
            unsafe.unpark(thread);
    }

    public static void park(Object blocker) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        unsafe.park(false, 0L);
        setBlocker(t, null);
    }

    public static void parkNanos(Object blocker, long nanos) {
        if (nanos > 0) {
            Thread t = Thread.currentThread();
            setBlocker(t, blocker);
            unsafe.park(false, nanos);
            setBlocker(t, null);
        }
    }

    public static void parkUntil(Object blocker, long deadline) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        unsafe.park(true, deadline);
        setBlocker(t, null);
    }

    public static Object getBlocker(Thread t) {
        return unsafe.getObjectVolatile(t, parkBlockerOffset);
    }

    public static void park() {
        unsafe.park(false, 0L);
    }

    public static void parkNanos(long nanos) {
        if (nanos > 0)
            unsafe.park(false, nanos);
    }

    public static void parkUntil(long deadline) {
        unsafe.park(true, deadline);
    }
}
