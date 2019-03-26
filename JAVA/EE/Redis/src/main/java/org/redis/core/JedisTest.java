package org.redis.core;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 通过Jedis来操作Redis中的各种数据类型
 */
public class JedisTest {

    Jedis jedis;

    @Before
    public void beforeClass() {
        jedis = new Jedis("localhost");
    }

    /**
     * 	Redis和Java字符串实例
     */
    @Test
    public void testString() {
        // set the data in redis string
        jedis.set("tutorial-name", "Redis Tutorial");
        // Get the stored data and print it
        System.out.println("Stored string in redis: " + jedis.get("tutorial-name"));
    }

    /**
     * Redis和Java列表示例
     */
    @Test
    public void testList() {
        jedis.lpush("tutorial-list", "Redis");
        jedis.lpush("tutorial-list", "Mongodb");
        jedis.lpush("tutorial-list", "Mysql");
        // Get the stored data and print it

    }
}
