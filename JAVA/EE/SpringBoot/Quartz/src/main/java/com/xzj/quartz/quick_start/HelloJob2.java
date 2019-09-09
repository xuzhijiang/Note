package com.xzj.quartz.quick_start;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob2 implements Job{

    /**
     * - 在进行任务调度时JobDataMap存储在JobExecutionContext中，方便获取。
     *
     * - JobDataMap可以用来装载任何可序列化的数据对象，当job实例对象被执行时这些参数对象就会传递给他。
     *
     * - JobDataMap实现了JDK的Map接口，并且添加了一些方便的方法用来存取基本的数据类型。
     *
     * Job类中JobDataMap的获取方式如下，包含了如何获取trigger中的自定义的参数值
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为："+sf.format(date));

        //编写具体的业务逻辑
        JobKey key = context.getJobDetail().getKey();
        System.out.println("Job的name属性值和group属性值分别为："+key.getName()+":"+key.getGroup());

        TriggerKey trKey = context.getTrigger().getKey();
        System.out.println("Trigger的name属性值和group属性值分别为："+trKey.getName()+":"+trKey.getGroup());

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String jobMsg = dataMap.getString("message");
        double doubleJobValue = dataMap.getDouble("doubleJobValue");
        System.out.println("jobMsg："+jobMsg);
        System.out.println("doubleJobValue："+doubleJobValue);

        JobDataMap tDataMap = context.getTrigger().getJobDataMap();
        String tiggerMsg = tDataMap.getString("message");
        double doubleTrigglerValue = tDataMap.getDouble("doubleTrigglerValue");
        System.out.println("tiggerMsg："+tiggerMsg);
        System.out.println("doubleTrigglerValue："+doubleTrigglerValue);

    }

}