package com.redis.core.springboot.redistemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    @Qualifier("custom-redis-template")
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * Redis 5大数据类型
     * String, List, Hash, Set, ZSet(有序集合)
     */
    @Test
    public void test01() {
        // 操作字符串
//        stringRedisTemplate.opsForValue().append("foo", "hello");
//        stringRedisTemplate.opsForValue().append("foo", "world");
//        System.out.println(stringRedisTemplate.opsForValue().get("foo"));

        // 操作List
//        stringRedisTemplate.opsForList().leftPush("myList", "1");
//        stringRedisTemplate.opsForList().leftPush("myList", "2");
//        stringRedisTemplate.opsForList().leftPush("myList", "3");
//        System.out.println(stringRedisTemplate.opsForList().leftPop("myList"));
//        System.out.println(stringRedisTemplate.opsForList().leftPop("myList"));
//        System.out.println(stringRedisTemplate.opsForList().leftPop("myList"));
    }

    //测试保存对象
    @Test
    public void test02(){
        // 保存对象，默认使用jdk序列化机制,需要修改为以json的方式保存
        redisTemplate.opsForValue().set("emp-01", new User(1, 20, "name-01"));
    }
}
