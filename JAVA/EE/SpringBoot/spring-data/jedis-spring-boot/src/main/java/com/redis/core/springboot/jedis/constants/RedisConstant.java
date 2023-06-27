package com.redis.core.springboot.jedis.constants;

public class RedisConstant {
    public static final String PHONE_VERIFY_CODE_KEY = "verifyCode:code:"; // 验证码前缀
    public static final String PHONE_VERIFY_CODE_COUNT = "verifyCode:count:"; // 验证码发送次数计数器前缀
    public static final int VERIFY_CODE_LENGTH = 6; // 验证码长度

    // 格式: Seckill:Stock:productId
    public static final String SECKILL_PRODUCT_STOCK_PREFIX = "Seckill:Stock:"; // 秒杀商品的库存对应的key的前缀
    // 格式: Seckill:User:productId (秒杀成功的用户的id的key对应的前缀)
    public static final String SECKILL_PRODUCT_USER_PREFIX = "Seckill:User:"; // 存放秒杀成功的用户对应的key的前缀
}
