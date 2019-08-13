package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {
        //scheduleWithFixedDelay();

//        scheduleAtFixedRate();

//        testScheduleRunnable();

        testScheduleCallable();
    }

    public static void FourWayCreateScheduledThreadPoolExecutor() {
        int corePoolSize = 5;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // ExecutorsE类提供了4个工厂方法，可以帮助我们快速的创建ScheduledThreadPoolExecutor
        Executors.newScheduledThreadPool(corePoolSize);
        Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        Executors.newSingleThreadScheduledExecutor();
        Executors.newSingleThreadScheduledExecutor(threadFactory);
    }


    // 不考虑任务执行时间 ，总是在上一次任务执行完成之后，再延迟指定时间进行执行
    public static void scheduleWithFixedDelay() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(16);
        System.out.println("time: " + new Date().toLocaleString());

        scheduledThreadPool.scheduleWithFixedDelay(new ScheduledRunnable(), 10, 2, TimeUnit.SECONDS);
        System.out.println("main thread is over");
    }

    public static void scheduleAtFixedRate() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(16);

        System.out.println("Submit time : " + new Date().toLocaleString());

        // 考虑任务执行时间
        // 自动校正任务的执行时间，也就是说，如果任务执行了1秒，那么再过1秒就会执行，如果任务执行了1. 5秒，那么再过0. 5秒就会执行
        scheduledThreadPool.scheduleAtFixedRate(new ScheduledRunnable(), 10, 2,TimeUnit.SECONDS);

        System.out.println("StaticProxyTest thread is over");
    }

    /**
     * 任务提交时间，延迟5秒执行
     */
    public static void testScheduleRunnable() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(16);

        scheduledThreadPool.schedule(new ScheduledRunnable(), 5, TimeUnit.SECONDS);
    }

    public static void testScheduleCallable() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

        scheduledThreadPool.schedule(new ScheduledCallable(), 5, TimeUnit.SECONDS);

        System.out.println("main thread is over");
    }

    private static class ScheduledRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("ScheduledTask.run start -------"+new Date().toLocaleString());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ScheduledTask.run end -------"+new Date().toLocaleString());
        }

    }

    private static class ScheduledCallable implements Callable {

        @Override
        public Object call() throws Exception {
            System.out.println("call begin -------"+new Date().toLocaleString());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("call end -------"+new Date().toLocaleString());
            return "my call object";
        }
    }
}
