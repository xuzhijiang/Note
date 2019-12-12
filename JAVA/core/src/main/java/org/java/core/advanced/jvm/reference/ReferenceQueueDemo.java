package org.java.core.advanced.jvm.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * ReferenceQueue用来配合引用工作的,没有ReferenceQueue一样可以运行(除了虚引用必须要联合ReferenceQueue来使用)
 *
 * 创建引用的时候可以指定关联ReferenceQueue,当gc释放内存的时候,会将引用加入ReferenceQueue,
 * 如果程序发现某个虚引用被加入了ReferenceQueue,那么就在所引用的对象的内存被回收之前采取必要的行动,
 * 这相当于是一种通知机制.
 *
 * 相当于引用在死之前,要存放到ReferenceQueue里面的,在队列中留点遗言.
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("===============");

        o1 = null;
        System.gc();
        Thread.sleep(200L);

        System.out.println(o1);
        System.out.println(weakReference.get());

        while (true) {
            Thread.sleep(500L);
            // 软引用/弱引用/虚引用在被回收之前,可以放到引用队列里面,在这个引用队列里面做一些类似于spring的后置操作.
            // 注意: 只有虚引用必须要和引用队列联合使用,其他的引用不是强制的
            System.out.println(referenceQueue.poll());
        }

    }
}
