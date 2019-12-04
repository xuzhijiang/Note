package com.autoconfiguration;

import com.autoconfiguration.config.RedisAutoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

//@Slf4j
public class TestBaseJavaConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RedisAutoConfig.class);
        RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
        redisTemplate.opsForValue().set("smlz", "司马");
        System.out.println("从redis中 获取: " + redisTemplate.opsForValue().get("smlz"));
    }
}
