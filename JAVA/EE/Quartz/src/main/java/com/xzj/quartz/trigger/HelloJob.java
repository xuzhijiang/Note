package com.xzj.quartz.trigger;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为："+sf.format(date));

        // HelloJob中获取了开始执行时间和结束执行时间。
        Trigger currentTrigger = context.getTrigger();
        System.out.println("开始时间为："+currentTrigger.getStartTime());
        System.out.println("结束时间为："+currentTrigger.getEndTime());

        // JobKey：表示job实例的标识，触发器被触发时，该指定的job实例会执行。
        JobKey jobKey = currentTrigger.getJobKey();
        System.out.println("jobKeyName:"+jobKey.getName()+"jobGroup:"+jobKey.getGroup());
    }
}