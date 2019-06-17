package org.redis.distributed.core.limit;

import org.redis.distributed.core.constant.RedisToolsConstant;
import org.redis.distributed.core.util.ScriptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
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

    /**
     * redis单机还是redis集群
     */
    private int type;

    /**
     * 每秒的流量阈值
     */
    private int limit = 200;

    private static final Integer FAIL_CODE = 0;

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
        Object result = limitRequest(connection);

        if (!FAIL_CODE.equals(Integer.valueOf(result.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    private Object limitRequest(Object connection) {
        Object result = null;
        // 1970年到现在的秒数
        // 下面也就是要限制每秒的流量，限制高并发
        String key = String.valueOf(System.currentTimeMillis() / 1000);

        if (connection instanceof Jedis) {
            // public static <T> Set<T> singleton(T o)
            // singleton(T) 方法用于返回一个不可变集只包含指定对象。
            // o: 这是将要存储在返回的集合的唯一对象。
            Jedis jedis = (Jedis) connection;
            // Redis Eval 命令使用 Lua 解释器执行脚本。
            // redis Eval 命令基本语法如下：
            // redis 127.0.0.1:6379> EVAL script numkeys key [key ...] arg [arg ...]
            // script： 参数是一段 Lua 5.1 脚本程序。脚本不必(也不应该)定义为一个 Lua 函数。
            // numkeys： 用于指定键名参数的个数。
            // key [key ...]： 从 EVAL 的第三个参数开始算起，表示在脚本中所用到的哪些 Redis 键(key)，
            // 这些键名参数可以在 Lua 中通过全局变量 KEYS 数组访问，用 1 为基址的形式访问( KEYS[1] ， KEYS[2] ，以此类推)。
            // arg [arg ...]： 附加参数，在 Lua 中通过全局变量 ARGV 数组访问，
            // 访问的形式和 KEYS 变量类似( ARGV[1] 、 ARGV[2] ，诸如此类)。

            // 示例:
            // redis 127.0.0.1:6379> eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 key2 first second
            //1) "key1"
            //2) "key2"
            //3) "first"
            //4) "second"
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

    /**
     * get Redis connection
     */
    private Object getConnection() {
        Object connection = null;
        if (type == RedisToolsConstant.SINGLE) {
            RedisConnection redisConnection = jedisConnectionFactory.getConnection();
            connection = redisConnection.getNativeConnection();
        } else if (type == RedisToolsConstant.CLUSTER) {
            RedisClusterConnection redisClusterConnection = jedisConnectionFactory.getClusterConnection();
            connection = redisClusterConnection.getNativeConnection();
        }
        return connection;
    }

    private void buildScript() {
        script = ScriptUtils.getScript("limit.lua");
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
