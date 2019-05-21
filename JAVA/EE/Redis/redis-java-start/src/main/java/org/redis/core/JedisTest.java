package org.redis.core;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 通过Jedis来操作Redis中的各种数据类型
 */
public class JedisTest {

    static Jedis jedis;

    @BeforeClass
    public static void beforeClass() {
        jedis = new Jedis("localhost");
    }

    /**
     * Redis字符串
     */
    @Test
    public void testString() {
        // set the data in redis string
        jedis.set("tutorial-name", "Redis Tutorial");
        // Get the stored data and print it
        System.out.println("Stored string in redis: " + jedis.get("tutorial-name"));
    }

    /**
     * Redis列表
     */
    @Test
    public void testList() {
        jedis.lpush("tutorial-list", "Redis");
        jedis.lpush("tutorial-list", "Mongodb");
        jedis.lpush("tutorial-list", "Mysql");
        // Get the stored data and print it
        List<String> list = jedis.lrange("tutorial-list", 0, 5);
        for(int i=0;i<list.size();i++){
            System.out.println("stored string in redis : " + list.get(i));
        }
    }

    @Test
    public void testKeys() {
        Set<String> keys = jedis.keys("*");
        for(String key : keys) {
            System.out.println("list of stored key: " + key);
        }
    }

    @Test
    public void testHash() {
        String key = "user";
        String field_name = "name";
        String filed_name_value = "xzj";
        String field_city = "city";
        String field_city_value = "bj";
        jedis.hset(key, field_name, filed_name_value);
        jedis.hset(key, field_city, field_city_value);

        Map<String, String> map = jedis.hgetAll(key);
        Set<Entry<String, String>> entrySet = map.entrySet();
        for(Entry<String, String> entry : entrySet) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    @Test
    public void testSet() {
        String key = "set_key";
        String[] members = new String[]{"a", "b", "c", "d", "a"};
        jedis.sadd(key, members);

        Set<String> smembers = jedis.smembers(key);
        for(String string : smembers) {
            System.out.println("testSet: " + string);
        }
    }

    @Test
    public void testZSet() {
        String key = "zset_key";
        Map<String, Double> scoreMembers = new HashMap<String, Double>();
        scoreMembers.put("xzj", 60.7);
        scoreMembers.put("aaa", 89.9);
        scoreMembers.put("bbb", 8.9);
        scoreMembers.put("ccc", 79.9);
        jedis.zadd(key, scoreMembers);

        // 获取索引[0, 2]内的元素,元素是按分数来排名的.
        Set<String> members = jedis.zrange(key, 0, 2);
        System.out.println("members count: " + members.size());
        for(String member : members) {
            System.out.println(member + " : " + jedis.zscore(key, member));
        }
    }

    @Test
    public void testSubscribe() {
        JedisPubSub jedisPubSub = new JedisPubSub() {
            // 覆盖JedisPubSub中的onMessage，用于回调
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("channel: " + channel);
                System.out.println("message: " + message);
            }
        };

        // 需要先执行订阅操作
        jedis.subscribe(jedisPubSub, "channel1");
    }

    /**
     * 订阅一个channel，需要先用一个client来订阅。然后再publish消息到这个channel中，
     * 所以我们需要先执行testSubsribe，再执行testPublish。需要注意的是，JedisPubSub是在后台运行的，
     * 因此可以重复不断的接受消息。
     *
     * 需要注意的是，因为发布订阅模型中，一条消息被所有订阅此channel的client接受到。
     * 有的业务场景下，可能不满足我们要的要求，例如处理订单，我们可能只需要一个Client来处理。
     * 此时我们需要使用到阻塞队列。
     */
    @Test
    public void testPublish() {
        String channel = "channel1";
        String message = "test publish subscribe";
        jedis.publish(channel, message);
    }

    /**
     * 阻塞队列:
     * 为了演示阻塞队列的功能，我们启动2个客户端，一个是java客户端，一个是redis-cli客户端，
     * 都从列表queue中使用brpop获取数据。
     *
     * redis-cli客户端:
     * 127.0.0.1:6379> BRPOP queue 0
     *
     * 以下是java客户端，运行testBlockedQueue这个测试方法，也会进入阻塞状态。
     */
    @Test
    public void testBlockedQueue() {
        List<String> brpop = jedis.brpop(0, "queue");
        for(String string : brpop) {
            System.out.println(string);
        }
    }

    // 往queue中放入消息
    // 执行testLpush，我们将会看到两个客户端(java 客户端和redis-cli)分别接受到了一条消息，而不是每条消息被共享。
    @Test
    public void testLpush() {
        jedis.lpush("queue", "queue-message-1", "queue-message-2");
    }

    // 需要注意的是，阻塞队列接受到一条消息之后，就会跳出阻塞状态。如果需要不断的循环监听，
    // 则要使用while(true)将这段代码包含起来
    @Test
    public void testBlockedQueueCycle() {
        while(true) {
            // 从现实的结果中，我们可以看出以下这段代码的返回值的组成:
            // List中的元素总是偶数个，其中奇数表示的从哪一个队列中监听到的消息，偶数表示消息的内容,
            // 例如:  queue
            //        queue_message1
            List<String> brpop = jedis.brpop(0, "queue");
            for(String string : brpop) {
                System.out.println(string);
            }
        }
    }

    /**
     * 如果希望一次发送一批 redis 命令，一种有效的方式是使用 pipeline
     */
    @Test
    public void testPipeline() {
        Pipeline p = jedis.pipelined();

        p.set("fool", "bar");
        p.zadd("foo", 1, "barowitch");
        p.zadd("foo", 0, "barinsky");
        p.zadd("foo", 0, "barikoviev");

        Response<String> pipeString = p.get("foo1");
        Response<Set<String>> resp = p.zrange("foo", 0, -1);
        p.sync();

        int size = resp.get().size();
        Set<String> setBack = resp.get();
        for(String s : setBack) {
            System.out.println("s: " + s);
        }
    }

    // 如果希望一些命令一起执行而不被干扰，可以通过 transaction 将命令打包到一起执行：
    @Test
    public void testTransactionWithoutRetureValue() {
        jedis.watch("key1", "key2");
        Transaction t = jedis.multi();
        t.set("foo", "bar");
        t.exec();
    }

    // 如果需要得到返回值
    @Test
    public void testTransactionWithRetureValue() {
        Transaction t = jedis.multi();
        t.set("fool", "bar");
        Response<String> result1 = t.get("fool");
        t.zadd("foo", 1, "barowitch"); t.zadd("foo", 0, "barinsky"); t.zadd("foo", 0, "barikoviev");
        Response<Set<String>> sose = t.zrange("foo", 0, -1); // get the entire sortedset
        t.exec(); // dont forget it
        String foolbar = result1.get(); // use Response.get() to retrieve things from a Response
        int soseSize = sose.get().size(); // on sose.get() you can directly call Set methods!
    }
}
