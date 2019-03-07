package com.xzj.quartz.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 距距离当前时间4秒后首次执行任务之后每隔两秒重复执行一次，在第一次执行再连续执行三次
 */
public class HelloScheduler2 {

    public static void main(String[] args) throws SchedulerException {
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为：" + sf.format(date));
        // 创建一个JobDetail实例，将实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob1.class)
                .withIdentity("myJob", "ground1").build();

        // 距距离当前时间4秒后首次执行任务之后每隔两秒重复执行一次，在第一次执行再连续执行三次
        date.setTime(date.getTime() + 4000);
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(date)
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(2)
                                .withRepeatCount(3))
                .build();
        // 创建Schedule实例
        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler = sFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
