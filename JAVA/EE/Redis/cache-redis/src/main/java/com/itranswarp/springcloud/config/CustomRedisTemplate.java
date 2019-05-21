package com.itranswarp.springcloud.config;

import com.itranswarp.springcloud.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class CustomRedisTemplate {

    /**
     * 序列化配置类
     * @param redisConnectionFactory
     * @return
     */
    @Bean("custom-redis-template")
    public RedisTemplate<Object, User> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, User> redisTemplate = new RedisTemplate<Object, User>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 保存对象，使用的是Json序列化机制，序列化后的数据保存到redis中
        Jackson2JsonRedisSerializer<User>  ser = new Jackson2JsonRedisSerializer<User>(User.class);
        redisTemplate.setValueSerializer(ser);
        return  redisTemplate;
    }

}
