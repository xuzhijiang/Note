package com.qianfeng.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @ClassName TestRedis
 * @Description: TODO
 * @Author 臧红久
 * @Date 2019/10/16
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_redis_cluster.xml")
public class TestRedis {

    @Resource(name="redisTemplate")
    private ValueOperations valueOps;

    @Resource(name="redisTemplate")
    private ListOperations listOps;
    @Test
    public void testValue(){
        valueOps.set("claz","gp04");
        listOps.leftPushAll("list04","hello","world");
    }
}
