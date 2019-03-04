package com.xzj.quartz.quick_start;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 我们修改了HelloScheduler类，并传入了一些我们自定义的参数和值，
 * 如何在job中获取这些值，就使用到了JobDataMap对象。
 */
public class HelloScheduler2 {
    public static void main(String[] args) throws SchedulerException {
        // 创建一个JobDetail实例，将实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob2.class)
                .withIdentity("myJob", "ground1")
                .usingJobData("message", "Hello job")
                .usingJobData("doubleJobValue", 66.6).build();

        // 创建一个Trigger实例，定义该Job立即执行，并且每隔两秒钟重复执行一次
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .usingJobData("message", "Hello trigger")
                .usingJobData("doubleTrigglerValue", 88.8)
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2).repeatForever())
                .build();
        //创建Schedule实例
        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler = sFactory.getScheduler();
        scheduler.start();
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为："+sf.format(date));
        scheduler.scheduleJob(jobDetail,trigger);
    }

}