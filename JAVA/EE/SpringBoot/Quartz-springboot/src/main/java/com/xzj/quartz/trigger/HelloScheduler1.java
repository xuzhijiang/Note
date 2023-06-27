package com.xzj.quartz.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 距离当前时间4秒后执行且执行一次
 */
public class HelloScheduler1 {

    public static void main(String[] args) throws SchedulerException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间是: " + sdf.format(date));

        JobDetail jobDetail = JobBuilder.newJob(HelloJob1.class)
                .withIdentity("myJob", "ground1")
                .build();

        // 距离当前时间4秒后执行且执行一次
        date.setTime(date.getTime() + 4000);
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(date)
                .build();

        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler = sFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
