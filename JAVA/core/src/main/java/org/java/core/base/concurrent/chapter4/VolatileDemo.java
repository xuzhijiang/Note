package org.java.core.base.concurrent.chapter4;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {

    volatile static boolean flag = true;

    /**
     * 由于我们使用了volatile关键字，因此自定义线程每次修改flag时，
     * 主线程都可以实时的感知到,不会出现延时.(注意，volatile不能保证线程安全)
     * 这里如果没有用 volatile 来修饰 flag ，就有可能其中一个线程修改了 flag 的值
     * 并不会立即刷新到主内存中，导致主线程没有及时感知到，会出现延时感知.
     *
     * 这里主要利用的是 `volatile` 的内存可见性。
     */
    @Test
    public void test01() {
        // 线程每隔1毫秒，修改一次flag的值
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.currentThread().sleep(1);
                        flag=!flag;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 对于多个线程都依赖于同一个状态变量的值来判断是否要执行某段代码时，
        // 使用volatile关键字更为有用，其可以保证多个线程在任一时刻的行为都是一致的。而不会出现延时
        while(true){
            if(flag){
                System.out.println("do some thing...");
            }else{
                System.out.println(".....");
            }
        }
    }

    // 不能保证线程安全的示例

    //使用 volatile 修饰数据不能保证原子性，只能保证对每个线程的可见性
    private static volatile int count = 0 ;

    private static AtomicInteger atomicCount = new AtomicInteger() ;

    /**
     * 这里有个误区:对 `volatile` 修饰的变量进行并发操作是线程安全的。
     * 这里要重点强调，`volatile` 并**不能**保证线程安全性！只能保证
     * 对每个线程的可见性（每个线程拿到的值都是最新值），
     *
     * 当我们三个线程(t1,t2,main)同时对一个 `int` 进行累加时会发现最终的值都会小于3000000。
     *
     * 这是因为虽然 `volatile` 保证了内存可见性，但 `count ++` 这个操作并不是原子的，
     * 这里面涉及到获取值、自增、赋值的操作并不能同时完成。
     * 假如t1,t2,main都获取了主存中的同一个count值，然后分别做自增，赋值操作，就有问题.
     *
     * - 所以想达到线程安全可以使这三个线程串行执行(其实就是单线程，没有发挥多线程的优势)。
     * - 也可以使用 `synchronized` 或者是锁的方式来保证原子性。
     * - 还可以用 `Atomic` 包中 `AtomicInteger` 来替换 `int`，它利用了 `CAS` 算法来保证了原子性。
     */
    @Test
    public void volatileCanNotGuaranteeThreadSafe() throws InterruptedException {
        Runnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable, "t1----");
        Thread t2 = new Thread(runnable, "t2----");
        t1.start();
        t2.start();

        for (int i=0;i<1000000;i++) {
            count++;
            atomicCount.incrementAndGet();
        }
        t1.join();
        t2.join();
        System.out.println("count="+count);
        System.out.println("atomicCount="+atomicCount);
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("thread: " + Thread.currentThread().getName());
            for (int i=0;i<1000000 ;i++) {
                count++ ;
                atomicCount.incrementAndGet() ;
            }
        }
    }

    /***
     * Java 虽说是基于内存通信的(volatile关键字)，但也可以使用管道通信。
     *
     * 需要注意的是，输入流和输出流需要首先建立连接。这样线程 B 就可以收到线程 A 发出的消息了。
     *
     * 实际开发中可以灵活根据需求选择最适合的线程通信方式。
     */
    @Test
    public void piped() throws IOException, InterruptedException {
        // 面向于字符 PipedInputStream 面向于字节
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();

        //输入输出流建立连接
        writer.connect(reader);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(i + "---");
                        writer.write(i+"");
                        Thread.sleep(10);
                    }
                } catch (Exception e) {

                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running2");
                int msg;
                try {
                    while ((msg = reader.read()) != -1) {
                        System.out.println("msg : " + msg);
                    }
                } catch (Exception e) {

                }
            }
        });

        t2.start();

        t1.join();
        t2.join();
    }

}
