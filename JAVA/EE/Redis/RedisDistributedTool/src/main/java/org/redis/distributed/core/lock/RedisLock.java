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

import java.util.Collections;

/**
 * 分布式锁
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static final String LOCK_MSG = "OK";

    private static final Integer UNLOCK_MSG = 1;

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
     * Non-Blocking lock(非阻塞的锁,也就是无论上锁成功或者失败都立即返回,不阻塞线程)
     * @param key lock business type(锁的业务类型)
     * @param request value
     * @return 返回true表示上锁成功，false表示上锁失败.
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

        if (LOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * blocking lock阻塞的锁
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
               if (LOCK_MSG.equals(result)) { // 上锁成功才去关闭jedis
                   jedis.close();
               }
           } else if (connection instanceof JedisCluster) {
               JedisCluster jedisCluster = (JedisCluster) connection;
               result = jedisCluster.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
           }

           // 上锁成功后跳出for循环
           if (LOCK_MSG.equals(result)) {
               break;
           }
           // 上锁不成功就休眠一段时间
           Thread.sleep(sleepTime);
       }
    }

    /**
     * blocking lock, 自定义阻塞的时间,超过阻塞时间后，就返回
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

    /**
     * Non-blocking lock
     * @param key   lock business type
     * @param request
     * @param expireTime 自定义键的生存时间
     * @return true lock successfully, or false lock fail
     */
    public boolean tryLock(String key, String request, int expireTime) {
        Object connection = getConnection();
        String result = null;
        if (connection instanceof Jedis) {
            Jedis jedis = (Jedis) connection;
            result = jedis.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            jedis.close();
        } else if (connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            result = jedisCluster.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        }

        if (LOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解锁
     * @param key
     * @param request request must be the same as lock request
     * @return
     */
    public boolean unlock(String key, String request) {
        Object connection = getConnection();
        Object result = null;

        if (connection instanceof Jedis) {
            Jedis jedis = (Jedis) connection;
            result = jedis.eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(request));
            jedis.close();
        } else if (connection instanceof JedisCluster) {
            JedisCluster jedisCluster = (JedisCluster) connection;
            jedisCluster.eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(request));
        } else {
            return false;
        }
        // 删除成功返回1，删除失败返回0
        if (UNLOCK_MSG.equals(result)) {
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
