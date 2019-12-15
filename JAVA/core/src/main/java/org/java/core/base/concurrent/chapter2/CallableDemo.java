package org.java.core.base.concurrent.chapter2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 思考: Thread和Runnable是怎么关联起来的?
        // 答: 通过Thread的构造器:
        //  public Thread(Runnable target, String name){}

        // 再思考: Thread和Callable怎么关联起来呢? 难道Thread也有这么一个构造器?
        // 答: Thread没有接受Callable参数的构造器
        // public Thread(Runnable target, String name) {}

        Callable<Integer> callable = new MyCallable();
        // public FutureTask(Callable<V> callable) {}
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        FutureTask<Integer> futureTask2 = new FutureTask<>(callable);

        // 使用的构造方法是:
        //  public Thread(Runnable target, String name){}
        // Thread不能直接接受Callable,但是可以接受Runnable,所以就要找一个中间人,这个中间人实现了Runnable,
        // 而且这个中间人可以接受Callable
        // 这个中间人就是FutureTask, FutureTask实现了Runnable接口
        new Thread(futureTask, "AA").start();
        // 多个线程执行同一个FutureTask对象,FutureTask中的Callable只会执行一次
        new Thread(futureTask, "BB").start();
        // 多个线程,想要把同一个Callable实例的call方法执行2次,就要传入不同的FutureTask对象,比如futureTask和futureTask2
        new Thread(futureTask2, "CC").start();

        //int result02 = futureTask.get(); // 不要放在这里,会阻塞main线程的执行

        int result01 = 100;

//        while (!futureTask.isDone()) {
//
//        }

        // 获得Callable线程的计算结果,如果没有计算完成就会导致阻塞,直到计算完成.
        // 所以FutureTask的get方法尽量放到最后,不要阻塞其他线程的执行.在这里就是阻塞main线程的执行.
         int result02 = futureTask.get();

        System.out.println("**********result: " + (result01 + result02));
    }

    private static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + "*************come in Callable***********");
            TimeUnit.SECONDS.sleep(3L);
            return 1024;
        }
    }

}
