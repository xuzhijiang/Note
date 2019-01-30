package com.jinxuliang.springframeworkadvance.beans.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//使用@Async指明特定方法应该采用异步调用模式
@Service
public class AsyncTaskService {
    @Async
    public void process(Integer i){
        System.out.println("在线程"+Thread.currentThread().getName()
                +"上执行异步任务："+i);
    }
}


