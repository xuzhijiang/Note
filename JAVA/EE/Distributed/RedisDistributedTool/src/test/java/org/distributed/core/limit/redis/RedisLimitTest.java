package org.distributed.core.limit.redis;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.distributed.core.limit.redis.constant.RedisToolsConstant;
import org.distributed.core.limit.redis.limit.RedisLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.*;

public class RedisLimitTest {

    private static Logger logger = LoggerFactory.getLogger(RedisLimitTest.class);

    private static ExecutorService executorServicePool;

    private static RedisLimit redisLimit;

    private static JedisPool jedisPool;

    public static void main(String[] args) throws InterruptedException {
        init();

        for (int i = 0; i < 250; i++) {
            executorServicePool.execute(new Worker(i));
        }

        executorServicePool.shutdown();
        while (!executorServicePool.awaitTermination(1, TimeUnit.SECONDS)) {
            logger.info("worker running");
        }
        logger.info("worker over");

    }

    private static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(300);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config) ;
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("");
        jedisConnectionFactory.setTimeout(100000);
        jedisConnectionFactory.afterPropertiesSet();

        // HostAndPort hostAndPort = new HostAndPort("47.98.194.60", 6379);
        // Jedis jedis = new Jedis("47.98.194.60", 6379);
        //JedisCluster jedisCluster = new JedisCluster(hostAndPort);

        redisLimit = new RedisLimit.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .limit(50)
                .build();

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("current-thread-%d").build();
        executorServicePool = new ThreadPoolExecutor(350, 350, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(200), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    private static class Worker implements Runnable {

        private int index;

        public Worker(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            boolean limit = redisLimit.limit();
            if (!limit) {
                logger.info("限流了 limit={},index={}", limit, index);
            } else {
                logger.info("=======index{}===通过=====", index);
            }
        }
    }

}
