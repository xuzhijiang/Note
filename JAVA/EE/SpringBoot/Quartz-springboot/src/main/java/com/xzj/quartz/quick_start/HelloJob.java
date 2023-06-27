package com.xzj.quartz.quick_start;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Job：实现任务逻辑的任务接口。
 */
public class HelloJob implements Job {

    /**
     *
     * @param context 当Scheduler调用一个job，就会将JobExecutionContext传递给Job的execute方法
     *                Job可以通过JobExecutionContext对象访问到Quartz运行
     *                时候的环境以及Job本身的明细数据
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 打印当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob-当前时间为: " + sdf.format(date));
    }
}
