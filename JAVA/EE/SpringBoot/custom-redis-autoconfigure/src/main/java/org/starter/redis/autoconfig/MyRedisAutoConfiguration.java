package org.starter.redis.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.starter.redis.core.IMyRedis;
import org.starter.redis.core.MyRedisImpl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@ConditionalOnClass(value = {Jedis.class, JedisPool.class, JedisPoolConfig.class}) // maven导入了redis相关的包,这个配置类才会起作用
@EnableConfigurationProperties(value = MyRedisProperties.class)
@Configuration
public class MyRedisAutoConfiguration {

    @Autowired
    private MyRedisProperties myRedisProperties;

    @Bean
    // 如何进行条件判断?
    // 这里是要检测 spring.my.redis.USEHA=false 这个键值是否存在.
    @ConditionalOnProperty(prefix = "spring.my.redis",name = "USEHA", havingValue = "false")
    public JedisPool jedisPool() {
        System.out.println("自定义redis starter 加载-->JedisPool");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(myRedisProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(myRedisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(myRedisProperties.getMinIdle());
        jedisPoolConfig.setTestOnBorrow(myRedisProperties.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(myRedisProperties.isTestOnReturn());

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, myRedisProperties.getHost(), myRedisProperties.getPort());
        return jedisPool;
    }

    @Bean
    @ConditionalOnBean(value = JedisPool.class)
    public IMyRedis iMyRedis(JedisPool jedisPool) {
        System.out.println("加载单机版的操作类--------> MyRedisImpl");
        MyRedisImpl myRedis = new MyRedisImpl(jedisPool);
        return myRedis;
    }

}
