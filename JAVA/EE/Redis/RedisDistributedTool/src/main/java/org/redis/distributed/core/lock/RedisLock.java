package org.redis.distributed.core.lock;

import org.redis.distributed.core.constant.RedisToolsConstant;
import org.redis.distributed.core.util.ScriptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * 分布式锁
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static final String LOCK_MSG = "OK";

    private static final Long UNLOCK_MSG = 1L;

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
     * lua script
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
     * Non-Blocking lock
     * @param key lock business type(锁的业务类型)
     * @param request value
     * @return return true if lock successfully, or false lock failure
     */
    public boolean tryLock(String key, String request) {
        Object connection = getConnection();
        String result = null;
        if (connection instanceof Jedis) {
            Jedis jedis = (Jedis) connection;
            result = jedis.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            jedis.close();
        } else if (connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            result = jedisCluster.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
        }

        if (LOCK_MSG.equals(request)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * blocking lock
     *
     * @param key
     * @param request
     */
    public void lock(String key, String request) throws InterruptedException {
       Object connection = getConnection();
       String result = null;
       for (;;) {
           if (connection instanceof Jedis) {
               Jedis jedis = (Jedis) connection;
               result = jedis.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
               if (LOCK_MSG.equals(result)) {
                   jedis.close();
               }
           } else if (connection instanceof JedisCluster) {
               JedisCluster jedisCluster = (JedisCluster) connection;
               result = jedisCluster.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
           }

           if (LOCK_MSG.equals(result)) {
               break;
           }

           Thread.sleep(sleepTime);
       }
    }

    /**
     * blocking lock, 自定义阻塞的时间
     *
     * @param key
     * @param request
     * @param blockTime 阻塞的超时时间,过了超时时间后就返回
     * @return
     */
    public boolean lock(String key, String request, int blockTime) throws InterruptedException {
        Object connection = getConnection();
        String result = null;
        while (blockTime >= 0) {
            if (connection instanceof Jedis) {
                Jedis jedis = (Jedis) connection;
                result = jedis.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
                if (LOCK_MSG.equals(result)) {
                    jedis.close();
                }
            } else if (connection instanceof JedisCluster) {
                JedisCluster jedisCluster = (JedisCluster) connection;
                result = jedisCluster.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            }

            if (LOCK_MSG.equals(result)) {
                return true;
            }

            blockTime -= sleepTime;

            Thread.sleep(sleepTime);
        }
        return false;
    }

    public boolean tryLock(String key, String request, int expireTime) {

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
