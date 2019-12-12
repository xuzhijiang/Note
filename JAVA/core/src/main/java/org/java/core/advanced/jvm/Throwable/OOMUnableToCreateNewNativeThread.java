package org.java.core.advanced.jvm.Throwable;

/**
 * native thread异常和平台有关系(OS)
 *
 * ulimit -u
 *
 * 不同的用户(root or 其他用户),允许创建的线程是不同的
 */
public class OOMUnableToCreateNewNativeThread {
    public static void main(String[] args) {
        for (int i = 0;  ; i++) {
            System.out.println("******************** i = " + i);
            new Thread(() -> {
                try { Thread.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) { e.printStackTrace(); }
            }, "Thread " + i).start();
        }
    }
}
