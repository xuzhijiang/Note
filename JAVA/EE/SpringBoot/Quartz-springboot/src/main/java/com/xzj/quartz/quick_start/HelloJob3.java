package com.xzj.quartz.quick_start;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 也可以使用成员变量的getter和setter获取
 * 修改job类
 */
public class HelloJob3 implements Job {

    private String message;
    private double doubleJobValue;
    private double doubleTrigglerValue;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getDoubleJobValue() {
        return doubleJobValue;
    }

    public void setDoubleJobValue(double doubleJobValue) {
        this.doubleJobValue = doubleJobValue;
    }

    public double getDoubleTrigglerValue() {
        return doubleTrigglerValue;
    }

    public void setDoubleTrigglerValue(double doubleTrigglerValue) {
        this.doubleTrigglerValue = doubleTrigglerValue;
    }

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

        System.out.println("Msg："+message);
        System.out.println("doubleJobValue："+doubleJobValue);

        System.out.println("doubleTrigglerValue："+doubleTrigglerValue);

    }
}
