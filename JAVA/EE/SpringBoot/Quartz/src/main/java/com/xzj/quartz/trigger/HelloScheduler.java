package com.xzj.quartz.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloScheduler {

    public static void main(String[] args) throws SchedulerException {
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为："+sf.format(date));

        // 创建一个JobDetail实例，将实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "ground1")
                .build();

        //获取距离当前时间3秒后的时间
        date.setTime(date.getTime()+3000);
        //获取距离当前时间6秒后的时间
        Date endDate = new Date();
        endDate.setTime(endDate.getTime()+6000);

        // 创建一个Trigger实例，定义该Job立即执行，并且每隔两秒钟重复执行一次
        // Quartz中的Trigger是用来告诉调度程序Scheduler作业JobDetail什么时候触发，
        // 即Trigger对象是用来触发执行Job的。
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(date)//开始执行时间为当前时间的后3秒,表示触发器的时间表首次触发的时间，类型为util.Date
                .endAt(endDate)//停止执行的时间为当前时间的后6秒,指定触发器的不再被触发的时间，类型为util.Date。
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2).repeatForever())
                .build();
        //创建Schedule实例
        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler = sFactory.getScheduler();
        scheduler.start();

        scheduler.scheduleJob(jobDetail,trigger);
    }
}
