package com.springboot.advanced.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.Future;

@Service
public class AsyncService {

    @Async // 告诉Spring这是一个异步方法,应该采用异步调用模式(在spring给我们开的线程池中运行)
    public void sendMsg(){
        int i = 0;
        while (true) {
            try {
                System.out.println("在线程"+Thread.currentThread().getName() +"上执行异步任务");
                System.out.println("发送短信中...");
                Thread.sleep(1000);
                if (i > 3) {
                    break;
                } else {
                    i++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Random random =new Random();

    @Async("taskExecutor") // 指定使用的线程池
    public Future<String> doTaskOne() throws Exception {
        System.out.println("开始做任务一:" + "在线程"+Thread.currentThread().getName() +"上执行异步任务");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务一完成");
    }
}


