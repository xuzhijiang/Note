package com.gmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RedisIncrService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @PostConstruct
    public void initRedisTestData() {
        redisTemplate.opsForValue().set("num", "0");
    }

    // 分布式下,这个自增会有问题
    public void incr(){
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String num = stringStringValueOperations.get("num");
        if(num != null){
            Integer i = Integer.parseInt(num);
            i = i+1;
            stringStringValueOperations.set("num",i.toString());
        }
    }
}
