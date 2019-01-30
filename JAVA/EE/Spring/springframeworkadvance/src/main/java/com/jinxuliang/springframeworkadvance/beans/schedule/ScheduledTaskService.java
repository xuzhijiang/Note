package com.jinxuliang.springframeworkadvance.beans.schedule;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

// 定义一个支持定时调用的Bean:
@Service
public class ScheduledTaskService {

    private static final SimpleDateFormat dateFormat=
            new SimpleDateFormat("HH:mm:ss");

    //每隔两秒执行一次
    @Scheduled(fixedRate = 2000)
    public void processInFixedRate(){
        System.out.println(Thread.currentThread().getName()+"  "+
        dateFormat.format(new Date()));
    }
}

