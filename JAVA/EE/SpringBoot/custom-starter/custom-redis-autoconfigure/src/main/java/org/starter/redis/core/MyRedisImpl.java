package org.starter.redis.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MyRedisImpl implements IMyRedis{

    private JedisPool jedisPool;

    public MyRedisImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            System.out.println(String.format("set key:%s,value:%s,error:%s", key, value, e));
            jedisPool.returnResource(jedis);
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            System.out.println(String.format("get key:%s,error:%s", key, e));
            jedisPool.returnResource(jedis);
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
}
