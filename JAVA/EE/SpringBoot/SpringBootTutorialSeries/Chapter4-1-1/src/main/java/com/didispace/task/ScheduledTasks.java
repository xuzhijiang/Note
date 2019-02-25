package com.didispace.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Scheduled详解
 * 在上面的入门例子中，使用了@Scheduled(fixedRate = 5000)
 * 注解来定义每过5秒执行的任务，对于@Scheduled的使用可以总结如下几种方式：
 *
 * @Scheduled(fixedRate = 5000) ：每隔5秒执行
 * @Scheduled(fixedDelay = 5000) ：延时5秒再执行
 * @Scheduled(initialDelay=1000, fixedRate=5000) ：延时1秒再执行,
 * 之后按fixedRate的规则每5秒执行一次
 */
//@Scheduled(cron="*/5*****") ：通过cron表达式定义规则
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 实现每过5秒输出一下当前时间。
     */
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("当前时间：" + dateFormat.format(new Date()));
    }

    @Scheduled(initialDelay = 2000, fixedRate = 3000)
    public void checkHealth() {
        System.out.println("UP");
    }
}
