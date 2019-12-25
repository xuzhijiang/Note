package org.distributed.core.limit.redis.util;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionUtil {

    private static JedisPool jedisPool = null;

    private static final String REDIS_HOST_NAME = "REDIS_HOST_NAME";

    private static final String DEFAULT_REDIS_HOST = "127.0.0.1";

    static {
        String host = System.getenv(REDIS_HOST_NAME);
        if (StringUtils.isEmpty(host)) {
            host = DEFAULT_REDIS_HOST;
        }
        JedisPoolConfig config = new JedisPoolConfig();
        // 客户端最大连接数
        config.setMaxTotal(150);
        jedisPool = new JedisPool(config, host,6379, 5000);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}