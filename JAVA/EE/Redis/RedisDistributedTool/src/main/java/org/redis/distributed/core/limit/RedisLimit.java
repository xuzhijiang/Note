package org.redis.distributed.core.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Collections;

/**
 * 限流工具
 */
public class RedisLimit {

    private static Logger logger = LoggerFactory.getLogger(RedisLimit.class);

    private JedisConnectionFactory jedisConnectionFactory;

    private int type;

    private int limit = 200;

    private static final int FAIL_CODE = 0;

    /**
     * lua script
     */
    private String script;

    private RedisLimit(Builder builder) {
        this.limit = builder.limit;
        this.jedisConnectionFactory = builder.jedisConnectionFactory;
        this.type = builder.type;
        buildScript();
    }

    /**
     * limit traffic
     */
    public boolean limit() {
        Object connection = getConnection();

    }

    private Object limitRequest(Object connection) {
        Object result = null;
        // 1970年到现在的秒数
        String key = String.valueOf(System.currentTimeMillis() / 1000);

        if (connection instanceof Jedis) {
            // public static <T> Set<T> singleton(T o)
            // singleton(T) 方法用于返回一个不可变集只包含指定对象。
            // o: 这是将要存储在返回的集合的唯一对象。
            Jedis jedis = (Jedis) connection;
            result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
            jedis.close();
        } else if(connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            result = jedisCluster.eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
            try {
                jedisCluster.close();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
        return result;
    }

    public static class Builder {

        private JedisConnectionFactory jedisConnectionFactory;

        private int limit = 200;

        private int type;

        public Builder(JedisConnectionFactory jedisConnectionFactory, int type) {
            this.jedisConnectionFactory = jedisConnectionFactory;
            this.type = type;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public RedisLimit build() {
            return new RedisLimit(this);
        }

    }
}
