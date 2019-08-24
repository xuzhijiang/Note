package org.redis.distributed.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.redis.distributed.core.constant.RedisToolsConstant;
import org.redis.distributed.core.lock.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;

public class RedisLockTest {

    private static Logger logger = LoggerFactory.getLogger(RedisLockTest.class);

    public static void main(String[] args) throws InterruptedException {
        // 初始化一个线程工厂
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("current-thread-%d").build();
        // 初始化线程池
        ExecutorService executorServicePool = new ThreadPoolExecutor(350, 350, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(200), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        RedisLock redisLock = getRedisLock();

        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(new Worker(redisLock));
        }

        executorServicePool.shutdown();

        while (!executorServicePool.awaitTermination(1, TimeUnit.SECONDS)) {
            logger.info("worker running");
        }
        logger.info("worker over");
    }

    /**
     * redis分布式锁 单机测试
     */
    private static RedisLock getRedisLock() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(60);
        config.setMaxTotal(60);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config);
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setTimeout(100000);
        jedisConnectionFactory.afterPropertiesSet();

        RedisLock redisLock = new RedisLock.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .lockPrefix("lock_")
                .sleepTime(100)
                .build();

        return redisLock;
    }

    /**
     * redis分布式锁 cluster测试
     */
    private static RedisLock getRedisLockCluster() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.addClusterNode(new RedisNode("127.0.0.1", 6379));

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration) ;
        jedisConnectionFactory.setTimeout(100000);
        jedisConnectionFactory.afterPropertiesSet();
        RedisLock redisLock = new RedisLock.Builder(jedisConnectionFactory, RedisToolsConstant.CLUSTER)
                .lockPrefix("lock_")
                .sleepTime(100)
                .build();
        return redisLock;
    }

    private static class Worker implements Runnable {

        private  RedisLock mRedisLock;

        public Worker(RedisLock redisLock) { this.mRedisLock =  redisLock; }

        @Override
        public void run() {
            String key = "key";
            String value = UUID.randomUUID().toString();

            // 测试非阻塞锁
//            boolean limit = mRedisLock.tryLock(key, value);
//            if (limit) {
//                logger.info("加锁成功=========");
//                boolean unlock = mRedisLock.unlock(key, value);
//                logger.info("解锁结果===[{}]",unlock);
//            } else {
//                logger.info("加锁失败");
//            }

            // 测试非阻塞锁 + 键的超时时间
//            boolean limit = mRedisLock.tryLock(key, value,20000);
//            if (limit) {
//                logger.info("加锁成功=========");
//                boolean unlock = mRedisLock.unlock(key, value);
//                logger.info("解锁结果===[{}]",unlock);
//            } else {
//                logger.info("加锁失败");
//            }

            //测试阻塞锁
//            try {
//                mRedisLock.lock(key, value);
//                logger.info(Thread.currentThread().getName() + "获取到锁");
//                mRedisLock.unlock(key, value) ;
//                logger.info(Thread.currentThread().getName() + "解锁");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //测试阻塞锁 + 阻塞时间
            try {
                boolean limit = mRedisLock.lock(key, value, 100);
                if (limit) {
                    logger.info(Thread.currentThread().getName() + "获取到锁");
                    boolean unlock = mRedisLock.unlock(key, value);
                    logger.info(Thread.currentThread().getName() + "解锁");
                } else {
                    logger.info(Thread.currentThread().getName() + "加锁失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 在做这个项目的时候让我不得不想提一下**单测**。
     * 因为这个应用是强依赖于第三方组件的Redis cluster，但是在单测中我们需要排除掉这种依赖
     * 1. 有可能是 Redis 的 ip、端口和单测里的不一致。
     * 2. Redis 自身可能也有问题。
     * 3. 也有可能是该同学的环境中并没有 Redis。
     *
     * 所以最好是要把这些外部不稳定的因素排除掉，单测只测我们写好的代码。
     * 于是就可以引入单测利器 `Mock` 了。
     * 它的想法很简答，就是要把你所依赖的外部资源统统屏蔽掉。如：数据库、外部接口、外部文件等等。
     * 下面代码有问题,还需调试!
     */
    @Test
    public void tryLock() throws Exception {
        String key = "test";
        String value = UUID.randomUUID().toString();
        Set<HostAndPort> nodes = new HashSet<>();
//        nodes.add(new HostAndPort("127.0.0.1", 6379));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        /**
         * 这里我们所依赖的 JedisCluster 其实是一个 `cglib 代理对象`。所以也不难想到它是如何工作的。(可以打断点看)
         * 比如这里我们需要用到 JedisCluster 的 set 函数并需要它的返回值。
         * Mock 就将该对象代理了，并在实际执行 set 方法后给你返回了一个你自定义的值。
         * 这样我们就可以随心所欲的测试了，**完全把外部依赖所屏蔽了**。
         */
        Mockito.when(jedisCluster.set(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyLong())).thenReturn("OK");

        boolean result = getRedisLockCluster().tryLock(key, value);
        System.out.println("result: " + result);

        Assert.assertTrue(result);

        //check
        Mockito.verify(jedisCluster).set(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyLong());
    }
}