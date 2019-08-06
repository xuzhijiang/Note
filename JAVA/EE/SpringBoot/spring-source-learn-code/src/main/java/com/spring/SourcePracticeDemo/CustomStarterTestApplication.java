package com.spring.SourcePracticeDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomStarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportTestApplication.class, args);
    }
}
