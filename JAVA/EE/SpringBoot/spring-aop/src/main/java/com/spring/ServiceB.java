package com.spring;

import org.springframework.stereotype.Service;

@Service
public class ServiceB {
    @CacheRedis(key = "count", expireTime = 500)
    public void add() {
        System.out.println("ServiceA的add方法被调用!!");
    }
}
