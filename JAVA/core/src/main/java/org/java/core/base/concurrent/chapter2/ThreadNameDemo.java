package org.java.core.base.concurrent.chapter2;

public class ThreadNameDemo {

    public static void main(String[] args) throws InterruptedException {
        // 主线程的名字是就是main，这是固定的。
        // 实时上，除了主线程，还有一些其他线程的名字也是固定的，例如垃圾回收线程的名字是：Finalizer，
        // 信号分发器线程名是Signal Dispatcher等
        System.out.println("主线程名称:"+Thread.currentThread().getName());

        //创建一个线程，不指定名字，JVM会自动给其分配一个名字
        new Thread(){
            @Override
            public void run() {
                // JVM自动给线程命名的策略：如果没有明确的线程指定名字，
                // JVM就会以Thread为前缀，按照线程创建的顺序，给其命名
                System.out.println("JVM自动分配的线程名:"+this.getName());
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("----线程名: " + Thread.currentThread().getName());
            }
        }, "xzj - runnable name: ").start();
        Thread.sleep(200L);
    }

}
