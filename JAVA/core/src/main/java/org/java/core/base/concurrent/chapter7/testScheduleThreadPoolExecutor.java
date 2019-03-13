package org.java.core.base.concurrent.chapter7;

import org.java.core.base.multithreading.ScheduledThreadPoolExecutor.ScheduledThreadPool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class testScheduleThreadPoolExecutor {

    public static void main(String[] args){

//        testSchedule();

//        scheduleAtFixedRate();

        scheduleWithFixedDelay();
    }

    private static void scheduleWithFixedDelay() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(16);

        System.out.println("Submit time : " + new Date().toLocaleString());

        // scheduleWithFixedDelay：不考虑任务执行时间 ，总是在上一次任务执行完成之后，再延迟指定时间进行执行
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
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
        }, 10, 2, TimeUnit.SECONDS);
    }

    private static void scheduleAtFixedRate() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(16);

        System.out.println("Submit time : " + new Date().toLocaleString());

        // 考虑任务执行时间
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("ScheduledTask.run start -------"+new Date().toLocaleString());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ScheduledTask.run end-------"+new Date().toLocaleString());
            }
        }, 10, 2,TimeUnit.SECONDS);////延迟10秒执行，之后每2秒执行一次
        // 自动校正任务的执行时间，也就是说，如果任务执行了1秒，那么再过1秒就会执行，
        // 如果任务执行了1. 5秒，那么再过0. 5秒就会执行

    }

    private static void testSchedule() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor  = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(16);

        System.out.println("Submit time : " + new Date().toLocaleString());
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("ScheduledTask.run-------"+new Date().toLocaleString());
            }
        }, 5, TimeUnit.SECONDS);


    }

}
