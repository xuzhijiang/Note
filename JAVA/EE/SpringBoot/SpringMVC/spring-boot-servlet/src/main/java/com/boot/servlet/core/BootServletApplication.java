package com.boot.servlet.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.boot.servlet.core")
public class BootServletApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootServletApplication.class, args);
    }
}
