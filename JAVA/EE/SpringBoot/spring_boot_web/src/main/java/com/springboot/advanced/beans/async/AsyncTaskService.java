package com.springboot.advanced.beans.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

@Service
public class AsyncTaskService {

    //使用@Async指明特定方法应该采用异步调用模式
    @Async
    public void process(Integer i){
        System.out.println("在线程"+Thread.currentThread().getName() +"上执行异步任务："+i);
    }

    public static Random random =new Random();

    @Async("taskExecutor")
    public Future<String> doTaskOne() throws Exception {
        System.out.println("开始做任务一:" + "在线程"+Thread.currentThread().getName() +"上执行异步任务");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务一完成");
    }

}


