package org.distributed.core.limit.redis.lock;

import org.distributed.core.limit.redis.constant.RedisToolsConstant;
import org.distributed.core.limit.redis.util.ScriptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * 分布式锁
 *
 * 使用 Redis 可以保证性能。
 * 利用key的expire机制解决了死锁。
 * Redis 支持集群部署提高了可用性。
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    /**
     * 加锁成功的消息
     */
    private static final String LOCK_SUCCESSFULLY_MSG = "OK";

    /**
     * 成功解锁的消息
     */
    private static final Integer UNLOCK_SUCCESSFULLY_MSG = 1;

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private String lockPrefix;

    private int sleepTime;

    private JedisConnectionFactory jedisConnectionFactory;

    private int type;

    /**
     * unit: millisecond
     */
    private static final int TIME = 1000;

    /**
     * lua脚本可以保证原子性
     */
    private String script;

    private RedisLock(Builder builder) {
        this.jedisConnectionFactory = builder.jedisConnectionFactory;
        this.type = builder.type;
        this.lockPrefix = builder.lockPrefix;
        this.sleepTime = builder.sleepTime;

        buildScript();
    }

    /**
     * get redis connection
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

    /**
     * 非阻塞的锁,无论上锁成功或者失败都立即返回,不阻塞线程
     * @return true表示上锁成功，false表示上锁失败.
     */
    public boolean tryLock(String key, String value) {
        Object connection = getConnection();
        String result = null;
        if (connection instanceof Jedis) {
            Jedis jedis = (Jedis) connection;
            // key 拥有默认的超时时间10秒
            result = jedis.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            jedis.close();
        } else if (connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            result = jedisCluster.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
        }

        if (LOCK_SUCCESSFULLY_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 阻塞锁,没有获取到锁就一直阻塞
     */
    public void lock(String key, String value) throws InterruptedException {
       Object connection = getConnection();
       String result = null;
       for (;;) {
           if (connection instanceof Jedis) {
               Jedis jedis = (Jedis) connection;
               // 键默认超时时间为10秒
               result = jedis.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
               if (LOCK_SUCCESSFULLY_MSG.equals(result)) { // 上锁成功才去关闭jedis
                   jedis.close();
               }
           } else if (connection instanceof JedisCluster) {
               JedisCluster jedisCluster = (JedisCluster) connection;
               result = jedisCluster.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
           }

           // 上锁成功后跳出for循环
           if (LOCK_SUCCESSFULLY_MSG.equals(result)) {
               break;
           }
           // 上锁不成功就休眠一段时间,防止一直消耗 CPU
           Thread.sleep(sleepTime);
       }
    }

    /**
     * 自定义阻塞的时间,超过阻塞时间后，就返回
     */
    public boolean lock(String key, String value, int blockTime) throws InterruptedException {
        Object connection = getConnection();
        String result = null;
        while (blockTime >= 0) {
            if (connection instanceof Jedis) {
                Jedis jedis = (Jedis) connection;
                result = jedis.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
                if (LOCK_SUCCESSFULLY_MSG.equals(result)) {
                    jedis.close();
                }
            } else if (connection instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) connection;
                result = jedisCluster.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            }
            if (LOCK_SUCCESSFULLY_MSG.equals(result)) {
                return true;
            }
            blockTime -= sleepTime;

            Thread.sleep(sleepTime);
        }
        return false;
    }

    /**
     * 非阻塞的锁,无论上锁成功或者失败都立即返回,不阻塞线程
     * @param expireTime 自定义键的生存时间
     * @return true表示上锁成功，false表示上锁失败.
     */
    public boolean tryLock(String key, String value, int expireTime) {
        Object connection = getConnection();
        String result = null;
        if (connection instanceof Jedis) {
            Jedis jedis = (Jedis) connection;
            result = jedis.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            jedis.close();
        } else if (connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            result = jedisCluster.set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        }

        if (LOCK_SUCCESSFULLY_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解锁,在每次解锁时都需要判断锁**是否是自己**的
     * @param value value must be the same as lock value
     */
    public boolean unlock(String key, String value) {
        Object connection = getConnection();
        Object result = null;

        if (connection instanceof Jedis) {
            Jedis jedis = (Jedis) connection;
            result = jedis.eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(value));
            jedis.close();
        } else if (connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            jedisCluster.eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(value));
        } else {
            return false;
        }
        // 删除成功返回1，删除失败返回0
        if (UNLOCK_SUCCESSFULLY_MSG.equals(Integer.valueOf(result.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get lua script
     */
    private void buildScript() {
        script = ScriptUtils.getScript("lock.lua");
    }

    public static class Builder {

        private static final String DEFAULT_LOCK_PREFIX = "lock_";

        private static final int DEFAULT_SLEEP_TIME = 100;

        private JedisConnectionFactory jedisConnectionFactory;

        private int type;

        private String lockPrefix;

        private int sleepTime;

        public Builder(JedisConnectionFactory jedisConnectionFactory,int type) {
            this.lockPrefix = DEFAULT_LOCK_PREFIX;
            this.sleepTime = DEFAULT_SLEEP_TIME;
            this.jedisConnectionFactory = jedisConnectionFactory;
            this.type = type;
        }

        public Builder lockPrefix(String lockPrefix) {
            this.lockPrefix = lockPrefix;
            return this;
        }

        public Builder sleepTime(int sleepTime) {
            this.sleepTime = sleepTime;
            return this;
        }

        public RedisLock build() {
            return new RedisLock(this);
        }

    }

}
