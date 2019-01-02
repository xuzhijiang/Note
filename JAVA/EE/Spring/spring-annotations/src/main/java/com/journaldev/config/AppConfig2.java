package com.journaldev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.journaldev.drivers.Computer;

@Configuration
public class AppConfig2 {
	
    @Bean(name = "comp", initMethod = "turnOn", destroyMethod = "turnOff")
    Computer computer(){
        return new Computer();
    }
    
}