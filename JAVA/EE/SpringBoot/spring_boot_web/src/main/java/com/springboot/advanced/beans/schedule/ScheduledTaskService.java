package com.springboot.advanced.beans.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

// 定义一个支持定时调用的Bean:
@Service
public class ScheduledTaskService {

    private static final SimpleDateFormat dateFormat= new SimpleDateFormat("HH:mm:ss");

    // 每隔两秒执行一次
    @Scheduled(fixedRate = 2000)
    public void processInFixedRate(){
        //System.out.println("定时调用: " + Thread.currentThread().getName()+"  "+
        dateFormat.format(new Date());
    }

    /**
     * 以每隔10秒执行一次,初始延时6秒
     */
    @Scheduled(fixedRate = 10_000, initialDelay = 6_000)
    public void doFixedRateTask() throws Exception {
        //System.out.println("[FIXED RATE] scheduled at " + LocalDateTime.now().toString());
        Thread.sleep(2000);
    }

    /**
     * 以固定10秒的间隔执行（上一个任务结束后等待10秒）
     */
    @Scheduled(fixedDelay = 10_000, initialDelay = 7_000)
    public void doFixedDelayTask() throws Exception {
        System.out.println("[FIXED DELAY] scheduled at " + LocalDateTime.now().toString());
        Thread.sleep(2000);
    }

    /**
     * 周一至周五，**:**:03执行
     */
    @Scheduled(cron = "3 * * * * MON-FRI")
    public void doCronTask() throws Exception {
        //System.out.println("[CRON] scheduled at " + LocalDateTime.now().toString());
    }
}

