package com.xzj.quartz.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 认识CronTrigger
 *
 * 基于日历的作业调度器，而不是像SimpleTrigger那样精确的指定时间间隔，比SimpleTrigger更常用。
 *
 * 用于配置CronTrigger实例，是由7个子表达式组成的字符串，描述了时间表的详细信息，格式为：
 *
 * [秒] [分] [小时] [日] [月] [周] [年]
 *
 * 通配符说明: https://i.imgur.com/zHJFs5I.png
 * 表达式说明: https://i.imgur.com/W1oTyxC.png
 * 每个字段说明:https://i.imgur.com/FQlxFOc.png
 *
 * ?: 表示不指定值，使用的场景为不需要关心当前设置这个字段值
 * *:表示所有值，例如在分钟的字段设置*表示每一分钟都会触发
 * /:用于递增触发:如在秒的字段上设置5/15表示从5秒开始，每增加15秒触发,
 * 在月字段上设置1/3，表示每月1号开始触发，每隔3天触发一次
 *
 * 举例：* * * * * ? *    ---》  每秒钟触发一次任务（表达式中的空格是英文状态）
 *
 * 2017年内的每天10点15分触发一次: 0 15 10 ？ * * 2017
 *
 * 每天的14点整至14点59分55秒，以及18点整至18点59分55秒，每5秒钟触发一次: 0/5 * 14,18 * * ?
 *
 * L和W可以一起使用，例如LW表示每个月的最后一个工作日
 */
public class HelloScheduler4 {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为：" + sf.format(date));
        // 创建一个JobDetail实例，将实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob").build();
        // 每秒执行一次任务
        CronTrigger trigger = (CronTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("* * * * * ? *"))
                .build();
        // 创建Schedule实例
        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler = sFactory.getScheduler();
        scheduler.start();//启动Scheduler

        // 绑定jobDetail和trigger，将它注册进Scheduler中，
        // 将trigger中的时间来触发jobDetail中的业务逻辑，返回的时间是最近一次的执行时间
        scheduler.scheduleJob(jobDetail, trigger);

        //scheduler执行两秒后挂起
        //Thread.sleep(2000L);

        //scheduler.standby();//暂停Scheduler
        //scheduler挂起三秒后重新执行

//        Thread.sleep(3000L);
//        scheduler.start();

        // scheduler.shutdown(); // 关闭, 方法中可以传入一个布尔类型的参数，
        // true表示等待所有正在执行的job执行完毕后，再关闭scheduler，false表示直接关闭scheduler。
    }
}
