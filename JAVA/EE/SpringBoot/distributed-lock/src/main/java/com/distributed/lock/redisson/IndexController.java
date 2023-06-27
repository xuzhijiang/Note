package com.distributed.lock.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

// 使用Apache JMeter进行压测
@RestController
public class IndexController {

    // https://redisson.org,很多大公司都使用
    @Autowired
    private Redisson redisson;

    // 现在大部分公司都是使用StringRedisTemplate来操作
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/")
    public String index() {
        return "index page: " + port;
    }

    @RequestMapping(value = "/deduct_stock/one")
    public String deductStockMethod1() {
        synchronized (this) {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));// jedis.get("stock");
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + ""); // jedis.set(key, value);
                System.out.println("扣减成功,剩余库存: " + realStock);
            } else {
                System.out.println("扣减失败,库存不足");
            }
        }
        return "end";
    }

    // 优化上面的method1
    @RequestMapping(value = "/deduct_stock/two")
    public String deductStockMethod2() {
        // 对商品001的操作的锁的key
        String lockKey = "product_001";
        String clientId = UUID.randomUUID().toString();

        // 加锁try--finally,防止由于抛异常导致的死锁
        try {

            // redis是单线程模型,会对到达redis的命令排队,达到串行执行
            //Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "zhuge");// jedis.setnx(key, value);
            //stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS); // 加锁超时时间

            // 高版本的spring-data-redis中有这个方法,这一行代码实现了上面2行代码的功能
            // 这行代码是原子的
            boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS); // 基于`SET resource_name my_random_value NX PX 30000`实现, 语法：jedis/jedisCluster.set(key, value, "nx", "ex/px", 3);
            // 还需要用一个后台的守护线程来续命.
            if (!result) {
                return "error";
            }

            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));// jedis.get("stock");
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + ""); // jedis.set(key, value);
                System.out.println("扣减成功,剩余库存: " + realStock);
            } else {
                System.out.println("扣减失败,库存不足");
            }
        } finally {
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                // 释放锁
                stringRedisTemplate.delete(lockKey);
            }
        }

        return "end";
    }

    // 使用redisson
    // 使用redis来实现分布式锁天生不是为了高并发而生的,它是为了解决你的业务问题而生的.
    @RequestMapping(value = "/deduct_stock/three")
    public String deductStockMethod3() {
        // 对商品001的操作的锁的key
        String lockKey = "product_001";

        String clientId = UUID.randomUUID().toString();
        RLock redissonLock = redisson.getLock(lockKey);

        try {
            // 默认时间就是30秒,推荐设置的时间就是30秒
            // redisson中是使用超时时间的1/3来续命(通过后台线程)
            redissonLock.lock(30, TimeUnit.SECONDS);

            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));// jedis.get("stock");
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + ""); // jedis.set(key, value);
                System.out.println("扣减成功,剩余库存: " + realStock);
            } else {
                System.out.println("扣减失败,库存不足");
            }
        } finally {
            redissonLock.unlock();
        }

        return "end";
    }

    // 当然redisson还是存在一些小问题: 比如你如果是redis主从架构,比如client1往master中设置了lockKey,但是这个时候
    // 如果master挂了,client1设置的这个lockKey还没有同步到slave上,这个时候从slave中选举出的master中就没有保存client1
    // 设置的这个lockKey,这样client1依然可以获取到锁,这就可能导致并发的问题.redlock在某种程度上可以解决这个问题,但是
    // redlock并不是非常好的实现手段,有很多争议,并不完善,所以不推荐使用redlock解决master-slave的主从锁的问题,
    // 这种问题出现的概率其实非常小,如果不是非常重要的场景,基本可以不用解决,可以容忍,但是如果你一定要解决,比如是资金或者支付的场景的话,
    // 你一定要解决的话,可以使用zk实现分布式锁来解决,因为zk会帮我们保证集群节点的数据一致性.zk天生就是为分布式准备的
    // 但是性能不如redis
}
