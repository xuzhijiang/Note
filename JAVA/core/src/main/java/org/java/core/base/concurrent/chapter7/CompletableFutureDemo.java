package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 运行一个异步的任务
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try { Thread.sleep(5000L); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t completableFuture1");
        });
        // 会阻塞main线程,等待异步任务执行完成
        System.out.println(completableFuture.get());

        System.out.println("**************************AAAAAAAAAA***************");

        // 异步回调,我们调用的方法需要Supplier供给型函数
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t completableFuture2");
            int a = 10 / 0;
            return 2019;
        });

        // 第一个参数t: 表示在没有异常抛出的情况下,异步返回的结果
        // 第二个参数u: 表示异步执行过程中,抛出的异常,如果没有异常,则为null
        Integer integer = completableFuture1.whenComplete((t, u) -> {
            System.out.println("********* result: " + t);
            System.out.println("********* exception: " + u);
        }).exceptionally(f -> {
            System.out.println("********* exception msg: " + f.getMessage());
            return 2006;
        }).get();

        System.out.println("异步回调的最终结果: " + integer);
    }
}
