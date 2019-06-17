package org.redis.distributed.core.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.redis.distributed.core.constant.RedisToolsConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.concurrent.*;

public class RedisClusterLockTest {

    private static Logger logger = LoggerFactory.getLogger(RedisClusterLockTest.class);

    private static ExecutorService executorServicePool;

    private static RedisLock redisLock;


    public static void main(String[] args) throws InterruptedException {
        init();

        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(new Worker(i));
        }

        executorServicePool.shutdown();
        while (!executorServicePool.awaitTermination(1, TimeUnit.SECONDS)) {
            logger.info("worker running");
        }
        logger.info("worker over");

    }

    private static void init() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.addClusterNode(new RedisNode("127.0.0.1", 6379));

        //集群
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration) ;

        jedisConnectionFactory.setTimeout(100000);
        jedisConnectionFactory.afterPropertiesSet();
        //jedisConnectionFactory.setShardInfo(new JedisShardInfo("47.98.194.60", 6379));
        //JedisCluster jedisCluster = new JedisCluster(hostAndPort);

//        HostAndPort hostAndPort = new HostAndPort("10.19.13.51", 7000);
//        JedisCluster jedisCluster = new JedisCluster(hostAndPort);
        redisLock = new RedisLock.Builder(jedisConnectionFactory, RedisToolsConstant.CLUSTER)
                .lockPrefix("lock_")
                .sleepTime(100)
                .build();

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("current-thread-%d").build();
        executorServicePool = new ThreadPoolExecutor(350, 350, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(200), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    }

    private static class Worker implements Runnable {

        private int index;

        public Worker(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            //测试非阻塞锁
            boolean limit = redisLock.tryLock("abc", "12345" + index);
            if (limit) {
                logger.info("加锁成功=========");
                boolean unlock = redisLock.unlock("abc", "12345" + index);
                logger.info("解锁结果===[{}]",unlock);
            } else {
                logger.info("加锁失败");

            }

            //测试非阻塞锁 + 超时时间
            //boolean limit = redisLock.tryLock("abc", "12345",1000);
            //if (limit) {
            //    logger.info("加锁成功=========");
            //    boolean unlock = redisLock.unlock("abc", "12345");
            //    logger.info("解锁结果===[{}]",unlock);
            //} else {
            //    logger.info("加锁失败");
            //
            //}


            //测试阻塞锁
            //try {
            //    redisLock.lock("abc", "12345");
            //    logger.info("加锁成功=========");
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            //redisLock.unlock("abc","12345") ;



            //测试阻塞锁 + 阻塞时间
            //try {
            //    boolean limit = redisLock.lock("abc", "12345", 100);
            //    if (limit) {
            //        logger.info("加锁成功=========");
            //        boolean unlock = redisLock.unlock("abc", "12345");
            //        logger.info("解锁结果===[{}]",unlock);
            //    } else {
            //        logger.info("加锁失败");
            //
            //    }
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }


}