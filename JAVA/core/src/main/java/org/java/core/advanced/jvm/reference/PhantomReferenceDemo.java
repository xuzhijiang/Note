package org.java.core.advanced.jvm.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class PhantomReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1, referenceQueue);

        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("===============");

        o1 = null;
        System.gc();
        Thread.sleep(200L);

        System.out.println(o1);
        System.out.println(phantomReference.get());

        while (true) {
            Thread.sleep(500L);
            // 软引用/弱引用/虚引用在被回收之前,可以放到引用队列里面,在这个引用队列里面做一些类似于spring的后置操作.
            // 注意: 只有虚引用必须要和引用队列联合使用,其他的引用不是强制的
            System.out.println(referenceQueue.poll());
        }
    }
}
