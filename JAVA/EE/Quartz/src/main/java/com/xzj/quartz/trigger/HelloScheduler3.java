package com.xzj.quartz.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 距离当前时间4秒后首次执行，距离当前时间后6秒停止执行
 */
public class HelloScheduler3 {

    public static void main(String[] args) throws SchedulerException {
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为：" + sf.format(date));
        // 创建一个JobDetail实例，将实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob1.class)
                .withIdentity("myJob", "ground1").build();

        // 距离当前时间4秒后首次执行，距离当前时间后6秒停止执行
        date.setTime(date.getTime() + 4000);
        Date endDate = new Date();
        endDate.setTime(endDate.getTime()+6000);

        // 重复次数withRepeatCount：可以是0，正整数或是SimpleTrigger.REPEAT_INDEFINITELY常量值
        //重复执行间隔withIntervalInSeconds：必须为0或者长整数
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(date)
                .endAt(endDate)
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
