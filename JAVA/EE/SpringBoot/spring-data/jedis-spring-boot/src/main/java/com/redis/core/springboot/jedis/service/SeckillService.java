package com.redis.core.springboot.jedis.service;

import com.redis.core.springboot.jedis.constants.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Service
public class SeckillService {

    @Autowired(required = false)
    JedisPool jedisPool;

    /**
     *
     * @param productId 要秒杀的商品的id
     * @param userId 参与秒杀的用户Id
     * @return
     */
    public boolean doSeckill(String productId, Integer userId) {
        // 秒杀商品的库存对应的key
        String stockKey = RedisConstant.SECKILL_PRODUCT_STOCK_PREFIX + productId;
        // 存放秒杀成功的用户对应的key (秒杀成功的用户的id存储到这个key中)
        String userKey = RedisConstant.SECKILL_PRODUCT_USER_PREFIX + productId;

        Jedis jedis = jedisPool.getResource();

        //监视库存(一定要在获取库存之前watch)
        jedis.watch(stockKey);
        // 获取库存
        String stock = jedis.get(stockKey);

        // 库存为null,表示秒杀还没开始
        if (stock == null) {
            System.out.println("秒杀还没有开始");
            jedis.close();
            return false;
        }

        // 已经秒杀成功，表示set中已经存在该userId
        if (jedis.sismember(userKey, String.valueOf(userId))) {
            System.out.println("你已经秒杀成功");
            jedis.close();
            return false;
        }

        // 若小于等于0，秒杀结束
        if (Integer.valueOf(stock) <= 0) {
            System.out.println("商品已售罄,秒杀结束");
            jedis.close();
            return false;
        }

        // 开启一个事务,进行减库存操作
        Transaction transaction = jedis.multi();
        transaction.decr(stockKey);
        transaction.sadd(userKey, String.valueOf(userId));
        List<Object> result = transaction.exec();

        if (result == null || result.size() == 0) {
            System.out.println("秒杀失败");
            jedis.close();
            return false;
        }

        System.out.println("秒杀成功");
        jedis.close();
        return true;
    }

    static String secKillScript ="local userid=KEYS[1];\r\n" +
            "local prodid=KEYS[2];\r\n" +
            "local qtkey='Seckill:Stock:'..prodid;\r\n" +
            "local usersKey='Seckill:User:'..prodid;\r\n" +
            "local userExists=redis.call('sismember',usersKey,userid);\r\n" +
            "if tonumber(userExists)==1 then \r\n" +
            " return 2;\r\n" +
            "end\r\n" +
            "local num= redis.call(\"get\" ,qtkey);\r\n" +
            "if tonumber(num)<=0 then \r\n" +
            "   return 0;\r\n" +
            "else \r\n" +
            "   redis.call(\"decr\",qtkey);\r\n" +
            "   redis.call(\"sadd\",usersKey,userid);\r\n" +
            "end\r\n" +
            "return 1" ;

    private String seckillScript;

    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("seckilll.lua");
        StringBuffer sb = new StringBuffer();
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf)) > 0) {
            sb.append(new String(buf));
        }
        seckillScript = sb.toString();
        System.out.println("seckillScript: ***************" + seckillScript);
    }

    public boolean doSeckillByLuaScript(String productId, Integer userId) {
        Jedis jedis = jedisPool.getResource();

        String sha1=  jedis.scriptLoad(secKillScript);
        // 参数2: 参数的个数
        Object result= jedis.evalsha(sha1, 2, String.valueOf(userId), productId);

        String reString=String.valueOf(result);
        if ("0".equals(reString)) {
            System.err.println("已抢空！！");
        }else if("1".equals( reString )  )  {
            System.out.println("抢购成功！！！！");
        }else if("2".equals( reString )  )  {
            System.err.println("该用户已抢过！！");
        }else{
            System.err.println("抢购异常！！");
        }

        jedis.close();
        return true;
    }

}
