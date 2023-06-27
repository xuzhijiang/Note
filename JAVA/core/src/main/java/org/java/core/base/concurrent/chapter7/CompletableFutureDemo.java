package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletableFuture<String> productBaseInfo = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品基本数据...");
            return "小米";
        }, executorService).whenComplete((r, e) -> {// 异步任务执行完成之后执行回调
            System.out.println("获取的结果是: " + r); // 每一个任务可以独立处理自己的结果
            if (e != null) System.out.println("exception: " + e);
        }).exceptionally((f) -> { // 如果远程服务调用失败之后,要返回一个容错的默认值
            System.out.println("exception msg: " + f.getMessage());
            return "商品基本数据容错值";
        });

        CompletableFuture<Integer> productAttrInfo = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品属性数据...");
            return 10;
        }, executorService).whenComplete((r, e) -> {
            System.out.println("获取的结果是: " + r);
            if (e != null) System.out.println("exception: " + e);
        }).exceptionally(f -> {
            System.out.println("exception msg: " + f.getMessage());
            return -1;
        });

        CompletableFuture<String> productSaleInfo = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品营销数据...");
            return "满199减100";
        }, executorService).whenComplete((r, e) -> {// 异步任务执行完成之后执行回调
            System.out.println("获取的结果是: " + r); 
            if (e != null) System.out.println("exception: " + e);
        }).exceptionally((f) -> { // 如果远程服务调用失败之后,要返回一个容错的默认值
            System.out.println("exception msg: " + f.getMessage());
            return "商品营销数据容错值";
        });

        ////阻塞，直到所有任务结束。
        CompletableFuture<Void> allOf = CompletableFuture.allOf(productBaseInfo, productAttrInfo, productSaleInfo);

        //Void aVoid = allOf.get(); // 相当于阻塞,等所有人完
        // allOf.join();//也是相当于 等allOf中的所有任务都执行完了,再往下执行

        try { Thread.sleep(1000L); } catch (InterruptedException e) { e.printStackTrace();}
        System.out.println(Thread.currentThread().getName() + " - 所有人都完成了...." + productAttrInfo.get());
    }
}
