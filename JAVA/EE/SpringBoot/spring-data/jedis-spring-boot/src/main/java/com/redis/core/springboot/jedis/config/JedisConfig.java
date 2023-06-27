package com.redis.core.springboot.jedis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class JedisConfig {

    /**
     * 在springboot中,如果没有引入spring-boot-starter-data-redis原来,下面的这些属性,
     *  springboot不会帮你自动封装在RedisProperties
     * 也就是RedisProperties不会被springboot自动创建bean供你使用,需要我们手动@Value来用这些属性.
     */
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    // 从JedisPool中获得操作redis的jedis: Jedis jedis = jedisPool.getResource();
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    // 必须把redis的集群模式打开这个配置才不会报错
    @Bean
    public JedisCluster jedisCluster() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(30);
        config.setMaxWaitMillis(2000);
        Set<HostAndPort> nodes = new HashSet<>();
        // 集群中有多个实例,所以这里是一个set集合,
        // 其实可以不用放这么多redis实例的连接,只需要放一个host:port即可,因为集群中的实例可以自己重定向.
        nodes.add(new HostAndPort("192.168.32.150", 6391));
        nodes.add(new HostAndPort("192.168.32.150", 6392));
        nodes.add(new HostAndPort("192.168.32.150", 6393));
        nodes.add(new HostAndPort("192.168.32.150", 6394));
        nodes.add(new HostAndPort("192.168.32.150", 6395));
        nodes.add(new HostAndPort("192.168.32.150", 6396));
        // 可以添加多个redis实例,组成集群
        // nodes.add(new HostAndPort(host, port));
        return new JedisCluster(nodes, config);
    }

}
