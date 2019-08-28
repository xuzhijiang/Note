package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Callable与Future的作用是,我们可以启动一个线程去执行某个任务，而另外一个线程等待获取这个线程执行完
 * 返回的结果，然后执行相应的操作.
 *
 * 例如: 我们可以启动A线程去执行某个任务，而B线程等待获取A线程执行的结果,假设线程A中进行某种运算，而主线程需要等待其运算结果，以便进行接下来的操作。
 */
public class CallableAndFuture {

    public static String sharedVariable;// 共享变量

    /**
     * 传统实现方式(自旋锁+共享变量):
     * 在没有使用Callable与Future之前，我们要实现刚刚上面的需求:
     * "线程A中进行某种运算，而主线程需要等待其运算结果，以便进行接下来的操作"
     * 这样的效果，通常需要通过共享变量和自旋锁实现(或者使用wait、notify)
     */
    @Test
    public void spinLock() {// 自旋锁
        //启动一个线程执行运行
        new Thread(){
            @Override
            public void run() {
                try {
                    // 进行运算操作(以休眠代替)
                    Thread.sleep(2000);
                    sharedVariable = "Hello";
                    System.out.println(Thread.currentThread().getName() + " - " + sharedVariable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("start time："+new Date());

        // 自旋锁，就是不断进行循环，起到阻塞的作用
        // 自旋锁的逻辑，不是程序阻塞不运行，只是程序在运行，
        // 但是会一直做无用又不影响业务的逻辑起到自旋锁的作用
        while(sharedVariable == null){//这块代码有问题
            System.out.println(Thread.currentThread().getName() + " - " + sharedVariable);
        }

        System.out.println("sharedVariable: " + sharedVariable);

        System.out.println("end time:"+new Date());
    }

    /**
     * 需要说明的是Callable<T>和Future<T>都有一个泛型参数，
     * 这指的是线程运行返回值结果类型。调用future.get()方法，会进行阻塞，直到线程运行完并返回结果。
     */
    @Test
    public void CallableAndFutureTest() throws ExecutionException, InterruptedException {
        System.out.println("start time: " + new Date());

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Future与Callable中的泛型，就是返回值的类型
        Future<String> future = executorService.submit(new Callable<String>(){
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });

        // Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。
        // 必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
        // 该方法会阻塞运行，等待执行完成
        System.out.println("isDone : " + future.isDone());
        String result = future.get();

        System.out.println("result:  " + result);

        System.out.println("end time: " + new Date());
        executorService.shutdown();
    }
}
