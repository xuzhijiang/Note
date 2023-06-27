package com.redis.core.springboot.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class JedisClusterTests {

    ///////////////////////////////////集群模式测试/////////////////////////////////

//    @Autowired
    JedisCluster jedisCluster;

    // 使用JedisCluster时，不需要手动释放连接；
    //在调用的过程中，会自动释放连接；
//    @After
//    public void closeJedis() {
//        if (jedisCluster != null) {
//            jedisCluster.close();
//        }
//    }

    @Test
    public void testRedisCluster() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(30);
        config.setMaxWaitMillis(2000);
        Set<HostAndPort> nodes = new HashSet<>();
        // 集群中有多个实例,所以这里是一个set集合,
        // 其实可以不用放这么多redis实例的连接,只需要放一个host:port即可,因为集群中的实例可以自己重定向.
        nodes.add(new HostAndPort("192.168.32.150", 6391));
        // 可以添加多个redis实例,组成集群
        // nodes.add(new HostAndPort(host, port));
        jedisCluster = new JedisCluster(nodes, config);
        // 直接使用JedisCluster对象操作redis
        jedisCluster.set("test", "abc");
        System.out.println(jedisCluster.get("test"));
        jedisCluster.del("test");
        jedisCluster.close();
    }
}
