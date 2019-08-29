package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Callable与Future的作用是,我们可以启动一个线程去执行某个任务，而另外一个线程等待获取这个线程执行完
 * 返回的结果，然后执行相应的操作.
 *
 * 例如: 我们可以启动A线程去执行某个任务，而主线程等待获取A线程执行的结果,假设线程A中进行某种运算，而主线程需要等待其运算结果，以便进行接下来的操作。
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

        // 自旋锁，就是不断进行循环，起到阻塞的作用
        // 自旋锁的逻辑，不是程序阻塞不运行，只是程序在运行，
        // 但是会一直做无用又不影响业务的逻辑起到自旋锁的作用
        while(sharedVariable == null){
            System.out.println(Thread.currentThread().getName() + " - " + sharedVariable);
        }
        System.out.println("sharedVariable: " + sharedVariable);
    }

    @Test
    public void CallableAndFutureTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //ExecutorService executor = Executors.newFixedThreadPool(10);

        // Future与Callable中的泛型，就是返回值的类型
        // Future提供cancel（）方法来取消关联的Callable任务。
        // 有isDone（）和isCancelled（）方法来查找关联的Callable任务的当前状态。
        Future<String> future = executorService.submit(new Callable<String>(){
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });

        System.out.println("isDone : " + future.isDone());
        // FutureTask.get(): 无限期等待未来的任务完成
        // 可以通过get方法获取执行结果，该方法会阻塞直到任务完成返回结果
        String result = future.get();
        System.out.println("result:  " + result);

        // 有一个get（）方法的重载版本，我们可以指定等待结果的时间，避免当前线程被阻塞更长时间是有用的,
        // 如果超时,会抛出java.util.concurrent.TimeoutException.
        //String result = future.get(200L, TimeUnit.MILLISECONDS);
        if (result != null) {
            System.out.println("FutureTask1 output=" + result);
        }

        executorService.shutdown();
    }

}
