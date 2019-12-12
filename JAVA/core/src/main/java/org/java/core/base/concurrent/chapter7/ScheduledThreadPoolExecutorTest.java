package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * // 调度的线程池,每隔一段时间调度一次
 * 因此如果您将period指定为1秒并且您的线程运行5秒，那么下一个线程将在第一个工作线程完成它的执行后立即开始执行。
 */
public class ScheduledThreadPoolExecutorTest {

    @Test
    public void FourWayCreateScheduledThreadPoolExecutor() {
        int corePoolSize = 5;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // ExecutorsE类提供了4个工厂方法，可以帮助我们快速的创建ScheduledThreadPoolExecutor
        Executors.newScheduledThreadPool(corePoolSize);
        Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        Executors.newSingleThreadScheduledExecutor();
        Executors.newSingleThreadScheduledExecutor(threadFactory);
    }

    /**
     * 任务提交时间，延迟2秒执行
     */
    @Test
    public void testScheduleWithRunnable() throws InterruptedException {
        // Java通过实现ScheduledExecutorService接口的ScheduledThreadPoolExecutor类提供预定的线程池实现.
        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(5);
        // 单线程池
        //ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newSingleThreadScheduledExecutor();

        // 延时2秒去执行任务,不会周期性执行.
        // 请注意，所有schedule（）方法都返回ScheduledFuture的实例，我们可以使用它来获取线程的线程状态信息和延迟时间。
        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("test ScheduledThreadPoolExecutor schedule");
            }
        }, 2, TimeUnit.SECONDS);
        System.out.println(scheduledFuture.isDone());
        System.out.println(scheduledFuture.isCancelled());
        Thread.sleep(10000);

        scheduledThreadPoolExecutor.shutdown();
    }

    @Test
    public void testScheduleWithCallable() throws InterruptedException {
        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(5);
        // 延时2秒去执行任务,不会周期性执行.
        ScheduledFuture<String> scheduledFuture = scheduledThreadPoolExecutor.schedule(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("callable .......");
                return "return value";
            }
        }, 2, TimeUnit.SECONDS);
        System.out.println(scheduledFuture.isDone());
        Thread.sleep(10000);

        scheduledThreadPoolExecutor.shutdown();
    }

    // 考虑任务执行时间
    // 自动校正任务的执行时间，也就是说,假如period为2，如果任务执行了1秒，那么再过1秒就会执行，如果任务执行了1. 5秒，那么再过0. 5秒就会执行
    // 如果任务要5秒,那么执行完成后,立刻执行下一个任务.
    @Test
    public void testScheduleAtFixedRate() throws InterruptedException {
        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("test ScheduledThreadPoolExecutor testScheduleAtFixedRate: " + new Date());
            }
            // 初始 延时为0, 固定的频率2秒一次执行,
        }, 0, 2, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);

        scheduledThreadPoolExecutor.shutdown();
    }

    // 不考虑任务执行时间 ，总是在上一次任务执行完成之后，再延迟指定时间进行执行
    @Test
    public void testScheduleWithFixedDelay() throws InterruptedException {
        ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(5);

        // 初始延迟启动定期执行，然后以给定的延迟执行。 延迟时间是从线程完成执行的时间开始
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("test fixedDelay: " + new Date());
            }
        }, 1, 4, TimeUnit.SECONDS);

        Thread.sleep(10000);

        scheduledThreadPoolExecutor.shutdown();
        System.out.println(".......: " + scheduledThreadPoolExecutor.isTerminated());
        while (!scheduledThreadPoolExecutor.isTerminated()) {
            // wait for all tasks to finish
            System.out.println("等待所有任务完成......");
        }
        System.out.println("Finished all threads");
    }

}
