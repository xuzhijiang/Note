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
 *
 * Builder 构建器
 *
 * 在设计这个组件时想尽量的提供给使用者清晰、可读性、不易出错的 API。
 *
 * > 比如第一步，如何构建一个限流对象。
 *
 * 最常用的方式自然就是构造函数，如果有多个域则可以采用重叠构造器的方式:
 *
 * ```java
 * public A(){}
 * public A(int a){}
 * public A(int a,int b){}
 * ```
 *
 * 缺点也是显而易见的：如果参数过多会导致难以阅读，甚至如果参数类型一致的情况下客户端颠倒了顺序，但不会引起警告从而出现难以预测的结果。
 *
 * 第二种方案可以采用 JavaBean 模式，利用 `setter` 方法进行构建:
 *
 * ```java
 * A a = new A();
 * a.setA(a);
 * a.setB(b);
 * ```
 *
 * 这种方式清晰易读，但却容易让对象处于不一致的状态，使对象处于线程不安全的状态。
 *
 * 所以这里采用了第三种创建对象的方式，构建器：
 *
 * 这样客户端在使用时:
 *
 * ```java
 * RedisLimit redisLimit = new RedisLimit.Builder<>(jedisCluster)
 *                 .limit(limit)
 *                 .build();
 * ```
 *
 * 更加的简单直接，并且避免了将创建过程分成了多个子步骤。
 *
 * 这在有多个构造参数，但又不是必选字段时很有作用。
 *
 * 因此顺便将分布式锁的构建器方式也一并更新了：
 */
public class RedisLimit {

    //---------------------------如何使用RedisLimit-------------------------
    /**
     * 为了减少代码侵入性，也为了简化客户端使用,提供了两种注解方式:
     *
     * 1. @ControllerLimit该注解可以作用于 `@RequestMapping` 修饰的接口中，并会在限流后提供限流响应。
     *
     * 2. 当然也可以在普通方法中使用。实现原理则是 Spring AOP (SpringMVC 的拦截器本质也是 AOP)。
     */
    //-----------------------------------------------------------------------------------

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
     * 如果要限制当前请求,返回false,否则返回true
     */
    public boolean limit() {
        Object connection = getConnection();
        // 只需要在需要限流的地方调用limitRequest,然后对返回值进行判断即可达到限流的目的
        Object result = limitRequest(connection);
        if (FAIL_CODE.equals(Integer.valueOf(result.toString()))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * - 每次请求时将当前时间(精确到秒)作为 Key 写入到 Redis 中，超时时间设置为 2 秒，Redis 将该 Key 的值进行自增。
     * - 当达到阈值时返回错误。这种方法是利用 Redis 做了一个粗暴的计数器.
     *
     * - 写入 Redis 的操作用 Lua 脚本来完成，利用 Redis 的单线程机制可以保证每个 Redis 请求的原子性。
     */
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
