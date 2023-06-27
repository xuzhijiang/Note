package com.custom.starter.test.LogStarterTest;

import org.springboot.starter.log.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * work方法没有加上@Log注解，所以不会被计算耗时
 * core方法虽然加上了@Log注解，但是在配置中被exclude了，也不会被计算耗时
 * 只有test方法可以正常计算耗时
 */
@Service
public class LogStarterService {

    @Log
    public void test(int num) {
        System.out.println("test方法执行=============***********");
    }

    @Log
    public void core(int num) {
        System.out.println("core方法执行=============***********");
    }

    public void work(int num) {
        System.out.println("work方法执行=============***********");
    }
}
