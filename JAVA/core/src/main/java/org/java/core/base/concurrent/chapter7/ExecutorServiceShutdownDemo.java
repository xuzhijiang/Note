package org.java.core.base.concurrent.chapter7;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池的关闭
 */
public class ExecutorServiceShutdownDemo {

    public static void main(String[] args) throws InterruptedException {
//        shutdownMethod();
        cancelTask();
    }

    /**
     * shutdown() 和 shutdownNow()的差异
     */
    public static void shutdownMethod() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5,
                5, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

        long start = System.currentTimeMillis();
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始执行");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "\t 被中断了");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 执行完成");
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始执行");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "\t 被中断了");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 执行完成");
        });

        try { Thread.sleep(2000L); } catch (InterruptedException e) { e.printStackTrace();}

        System.out.println(Thread.currentThread().getName() + "\t 关闭线程池");

        // threadPoolExecutor.shutdown();

        /**
         * 停止所有正在执行的任务, 并返回正在等待执行的任务列表这些任务被删除,此方法不会等待正在执行任务终止.
         */
        executorService.shutdownNow();
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println("正在等待的任务列表: " + runnables);

        // 每隔1秒去看下线程池中的线程是否都运行完成了(状态为TERMINATED)
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程池中依然有线程运行*******************");
        }

        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "\t 运行完成,一共耗时: " + (end - start) / 1000 + "秒");
    }

    /**
     * 如果只想中断 Executor 中的一个线程，可以通过使用 submit() 方法来提交一个任务，它会返回一个 Future<?> 对象，
     * 通过调用该对象的 cancel(true) 方法就可以中断线程。
     */
    public static void cancelTask() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(3, 5,
                5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

        Future<?> future = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始执行");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "\t 被中断了");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 执行完成");
        });

        try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace();}

        System.out.println(Thread.currentThread().getName() + "\t 要取消任务");
        // 相当于调用Thread的interrupt()方法
        boolean cancel = future.cancel(true);
        System.out.println(Thread.currentThread().getName() + "\t 是否取消了任务成功: " + cancel);

    }
}
