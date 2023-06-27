package com.springboot.advanced.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 对于@Scheduled的使用可以总结如下几种方式：
 *
 * @Scheduled(fixedRate = 5000) ：每隔5秒执行
 * @Scheduled(fixedDelay = 5000) ：延时5秒再执行
 * @Scheduled(initialDelay=1000, fixedRate=5000) ：延时1秒再执行,
 * 之后按fixedRate的规则每5秒执行一次
 */
// @Scheduled(cron="*/5*****") ：通过cron表达式定义规则
@Service
public class ScheduledService {

    /**
     * second(秒), minute（分）, hour（时）, day of month（日）, month（月）, day of week（周几）.
     * 0 * * * * MON-FRI
     *  【0 0/5 14,18 * * ?】 每天14点整，和18点整，每隔5分钟执行一次
     *  【0 15 10 ? * 1-6】 每个月的周一至周六10:15分执行一次
     *  【0 0 2 ? * 6L】每个月的最后一个周六凌晨2点执行一次
     *  【0 0 2 LW * ?】每个月的最后一个工作日凌晨2点执行一次
     *  【0 0 2-4 ? * 1#1】每个月的第一个周一凌晨2点到4点期间，每个整点都执行一次；
     */
    // @Scheduled(cron = "0 * * * * MON-SAT")
    //@Scheduled(cron = "0,1,2,3,4 * * * * MON-SAT")
    // @Scheduled(cron = "0-4 * * * * MON-SAT")
    @Scheduled(cron = "0/4 * * * * MON-SAT")  //每4秒执行一次
    public void hello(){
        System.out.println("hello ... ");
    }

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

