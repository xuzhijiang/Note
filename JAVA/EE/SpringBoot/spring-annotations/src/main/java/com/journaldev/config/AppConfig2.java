package com.journaldev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.journaldev.drivers.Computer;

@Configuration
public class AppConfig2 {

    // @Bean注释也可以与name，initMethod和destroyMethod等参数一起使用。
    // name  - 允许你给bean命名(allows you give name for bean)
    // initMethod  - 允许您选择将在context register(注册)时调用的方法
    // destroyMethod  - 允许您选择将在context关闭时调用的方法
    @Bean(name = "comp", initMethod = "turnOn", destroyMethod = "turnOff")
    Computer computer(){
        return new Computer();
    }
    
}