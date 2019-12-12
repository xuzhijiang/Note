package org.java.core.advanced.jvm.reference;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {

    public static void main(String[] args) {
//        softRef_Memory_Enough();
        softRef_Memory_NotEnough();
    }

    /**
     * 如果内存足够，垃圾回收器就不会回收它
     */
    private static void softRef_Memory_Enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println("**************" + o1);
        System.out.println("**************" + softReference.get());

        o1 = null;
        System.gc();
        System.out.println();
        System.out.println("********after**********");
        System.out.println();

        System.out.println("**************" + o1);
        System.out.println("**************" + softReference.get());
    }

    /**
     * 故意产生大对象并且配置小的内存,让它内存不够用,导致OOM,看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    private static void softRef_Memory_NotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println("**************" + o1);
        System.out.println("**************" + softReference.get());

        o1 = null;

        try {
            byte[] byteArray = new byte[30 * 1024 * 1024];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println();
            System.out.println("********after**********");
            System.out.println();

            System.out.println("**************" + o1);
            System.out.println("**************" + softReference.get());
        }

    }
}
