package com.tuling.seckill.config;

import com.tuling.seckill.zookeeper.ZookeeperWatcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Configuration
public class SpringConfig {

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @Autowired
    private ZookeeperWatcher zookeeperWatcher;

    @Bean
    public ZooKeeper initZookeeper() throws IOException {
        return new ZooKeeper(zookeeperAddress, 60000, new ZookeeperWatcher());
    }

    @Bean
    public RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}
