package com.springboot.core.service;

import com.springboot.core.aspect.annotation.CacheRedis;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @CacheRedis(key = "count", expireTime = 500)
    public String add() {
        return "add";
    }
}
