package com.springboot.advanced.beans.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(25);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.initialize();
        executor.setThreadNamePrefix("AsyncConfig Executor-");
        return executor;
    }

    //指定统一的异常处理程序
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
