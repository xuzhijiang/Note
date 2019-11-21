package com.spring.autoconfiguration.config;

import com.spring.autoconfiguration.support.RedisCfgProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

// spring boot是如何做到不让我们配置以下这些东西,只需要在rediscfg.properties
// 中写上需要的属性就可以的呢?
@Configuration
@PropertySource(value = {"classpath:rediscfg.properties"})
public class SpringRedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Bean
    public RedisCfgProperties redisCfgProperties() {
        RedisCfgProperties redisCfgProperties = new RedisCfgProperties();
        redisCfgProperties.setHost(host);
        redisCfgProperties.setPort(port);
        redisCfgProperties.setMaxTotal(maxTotal);
        redisCfgProperties.setMaxIdle(maxIdle);
        System.out.println(redisCfgProperties);
        return redisCfgProperties;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        return jedisPoolConfig;
    }

    // Caused by: java.lang.NoClassDefFoundError: redis/clients/util/Pool
    // 问题原因:pom.xml中引入的两个redis相关jar不兼容.
    // 解决办法:降低redis.clients.jedis的版本:为2.9.1
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return redisTemplate;
    }

}
