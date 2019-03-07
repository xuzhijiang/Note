package org.java.core.base.concurrent.chapter7;

import java.util.Date;

/**
 * 在没有使用Callable与Future之前，我们要实现
 *
 * "线程A中进行某种运算，而主线程需要等待其运算结果，以便进行接下来的操作"
 *
 * 这样的效果，通常需要通过共享变量和自旋锁实现(或者使用wait、notify)
 */
public class SpinLock {
    public static String sharedVariable;//共享变量
    public static void main(String[] args) {
        //启动一个线程执行运行
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//进行运算操作，以休眠代替
                    sharedVariable = "Hello";
                    System.out.println(Thread.currentThread().getName() + " - " + sharedVariable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();

        System.out.println("开始时间："+new Date());

        // 自旋锁，就是不断进行循环，起到阻塞的作用
        // 自旋锁的逻辑，不是程序阻塞不运行，只是程序在运行，
        // 但是会一直做无用又不影响业务的逻辑起到自旋锁的作用
        while(sharedVariable == null){//这块代码有问题
            System.out.println(Thread.currentThread().getName() + " - " + sharedVariable);
        }
        System.out.println(sharedVariable);

        System.out.println("结束时间:"+new Date());
    };

}