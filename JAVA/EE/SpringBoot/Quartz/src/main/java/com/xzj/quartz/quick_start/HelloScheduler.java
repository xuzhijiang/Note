package com.xzj.quartz.quick_start;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Quartz是一款强大的开源任务调度框架。
 *
 * 主要用到了Builder模式，factory模式，链式写法。
 *
 * 包括了三个核心概念，分别是调度器(Scheduler)，任务(Job)和触发器(Trigger)
 *
 * JobDetail: JobDetail为Job实例提供了许多属性，以及JobDataMap成员变量属性，它用来存储特定于Job实例的状态信息，
 *
 * 调度器(Scheduler)需要借助JobDetail对象来添加Job实例。
 *
 * 重要的属性如下：name、group、jobClass、jobDataMap。
 *
 * 监听器：JobListener,TriggerListener,ScheduerListener
 */
public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException {
        // 创建一个JobDetail实例，将实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "ground1").build();

        System.out.println("jobDetail.getJobClass(): " + jobDetail.getJobClass());
        System.out.println("jobDetail name: " + jobDetail.getKey().getName());
        System.out.println("jobDetail group: " + jobDetail.getKey().getGroup());
        System.out.println("jobDetail jobClass: " + jobDetail.getKey().getClass());
        System.out.println("jobDetail jobDataMap: " + jobDetail.getJobDataMap());

        // 创建一个Trigger实例，定义该Job立即执行，并且每隔两秒钟重复执行一次
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2).repeatForever())
                .build();
        //创建Schedule实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为："+sf.format(date));
        // 调度器开始调度某一个作业(Job),trigger定义了该Job执行的方式
        scheduler.scheduleJob(jobDetail,trigger);
    }

}