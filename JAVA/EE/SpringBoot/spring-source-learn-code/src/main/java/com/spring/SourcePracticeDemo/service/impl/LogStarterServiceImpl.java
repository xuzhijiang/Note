package com.spring.SourcePracticeDemo.service.impl;

import com.spring.SourcePracticeDemo.service.LogStarterService;
import org.springboot.starter.log.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * work方法没有加上@Log注解，所以不会被计算耗时
 * core方法虽然加上了@Log注解，但是在配置中被exclude了，也不会被计算耗时
 * 只有test方法可以正常计算耗时
 */
@Service
public class LogStarterServiceImpl implements LogStarterService {

    @Override
    @Log
    public void test(int num) {
        System.out.println("test method ...........log start test..............");
    }

    @Override
    @Log
    public void core(int num) {
        System.out.println("core method ...........log start test..............");
    }

    @Override
    public void work(int num) {
        System.out.println("work method ...........log start test..............");
    }
}
