package org.java.core.base.concurrent.chapter1;

public class MainThreadDemo {

    public static void main(String[] args) {
        //用于打印主线程的名称
        System.out.println(Thread.currentThread().getName());

        // 传入参数为线程的名字
        Thread t1 = new Thread("myThread") {
            @Override
            public void run() {
                for(int i=0;i<1000000;i++) {
                    System.out.println(Thread.currentThread().getName() + " thread print i: " + i);
                }
            }
        };
        t1.start();

        for(int i=0;i<1000000;i++) {
            System.out.println(Thread.currentThread().getName() + " thread print i: " + i);
        }
        // 线程run方法和main方法交叉打印出来的内容很好的说明了线程是可以并行运行的。
        // 如果不能并行运行，肯定是一个方法执行完，才会让另一个方法执行。

        // 我们会发现，有的时候run方法或者main方法循环了好几次，才会到另一个方法运行。
        // 这是因为线程的运行是"抢占式"的。CPU在不同的线程切换的过程中，
        // CPU并不是按顺序来主义调度这些线程(不是交替运行的,是抢占式的)，线程的运行是抢占式的，
        // 意思是一个线程获得一次运行机会之后，下一次能继续请求CPU进行执行。
        // 如果CPU连续几次接受了某个线程的执行请求，
        // 那么就有可能出现在一个线程的方法中运行好几次的情况。

        // 同样还是因为线程的运行是"抢占式"的原因，我们不知道在每次运行的时候哪个线程会在
        // CPU上运行。在极端的情况下，可能会出现一个线程先运行完成后，
        // 再去执行另一个线程。
    }
}
