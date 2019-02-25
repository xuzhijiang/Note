package org.java.core.base.concurrent.chapter2;

public class ThreadNameDemo {

    public static void main(String[] args) {
        //打印主线程的名称
        // 主线程的名字是就是main，这是固定的。实时上，除了主线程，
        // 还有其他线程的名字也是固定的，例如垃圾回收线程的名字是：Finalizer，
        // 信号转发器线程名是Signal Dispatcher等
        System.out.println("主线程名称:"+Thread.currentThread().getName());

        //创建一个线程，不指定名字，JVM会自动给其分配一个名字
        new Thread(){
            @Override
            public void run() {
                //如果运行时代码本身就是在一个线程对象中实现，
                // 那么我们可以通过this.getName获取其名称

                // JVM自动给线程命名的策略：如果没有明确的线程指定名字，
                // JVM就会以Thread为前缀，按照线程创建的顺序，给其命名
                System.out.println("JVM自动分配的线程名:"+this.getName());
            }
        }.start();

        //创建一个名字为new Thread的线程并启动
        new Thread(new MyRunnable(), "new thread").start();
    }
}
