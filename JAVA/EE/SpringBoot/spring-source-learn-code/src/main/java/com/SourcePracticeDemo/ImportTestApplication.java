package com.SourcePracticeDemo;

import com.SourcePracticeDemo.annotation.EnableContentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableContentService(policy = "core")
public class ImportTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImportTestApplication.class, args);
    }
}
