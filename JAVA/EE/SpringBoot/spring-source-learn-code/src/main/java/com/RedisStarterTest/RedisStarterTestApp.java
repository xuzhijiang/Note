package com.RedisStarterTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.starter.redis.core.IMyRedis;

@SpringBootApplication
@RestController
public class RedisStarterTestApp {
    @Autowired
    private IMyRedis myRedis;

    public static void main(String[] args) {
        SpringApplication.run(RedisStarterTestApp.class, args);
    }

    @GetMapping("/testRedisStarter")
    public String testAutoCfgStarter() {
        myRedis.set("hahaha", "hahaha-value");
        return myRedis.get("hahaha");
    }

}
