package com.redis.core.springboot.jedis.controller;

import com.redis.core.springboot.jedis.constants.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 验证码发送和校验
 */
@RestController
public class VerifyPhoneCodeController {

    @Autowired(required = false)
    JedisPool jedisPool;

    /**
     * 发送手机验证码
     * @param phoneNumber 接受验证码的手机号
     */
    @PostMapping("/send/verifyCode")
    public ResponseEntity<String> sendVerifyCode(@RequestParam(value = "phoneNumber") String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 11) {
            return new ResponseEntity<String>("手机号输入有问题", HttpStatus.BAD_REQUEST);
        }

        Jedis jedis = jedisPool.getResource();

        // 获取这个手机号今日验证码的发送次数
        String count = jedis.get(RedisConstant.PHONE_VERIFY_CODE_COUNT + phoneNumber);
        if (count == null) {
            // 第一次发送
            jedis.setex(RedisConstant.PHONE_VERIFY_CODE_COUNT+phoneNumber, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) < 3) {
            jedis.incr(RedisConstant.PHONE_VERIFY_CODE_COUNT + phoneNumber);
        } else {
            jedis.close();
            return new ResponseEntity<String>("今天发送验证码次数过多,请明天再试", HttpStatus.BAD_REQUEST);
        }

        // 获取验证码
        String code = getCode();

        // 把验证码存入redis,并且设置验证码的有效期为3分钟
        jedis.setex(RedisConstant.PHONE_VERIFY_CODE_KEY + phoneNumber, 3 * 60, code); // ex是设置过期时间

        // 调用接口,把验证码发送给手机,可以通过mq发送.
        // 省略....

        // 通知前端验证已发送
        jedis.close();
        return new ResponseEntity<String>("验证码发送成功", HttpStatus.OK);
    }

    private static String getCode() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < RedisConstant.VERIFY_CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @PostMapping("/validate/verifyCode")
    public ResponseEntity<String> validateVerifyCode(@RequestParam(value = "phoneNumber") String phoneNumber, @RequestParam("code") String code) {
        Jedis jedis = jedisPool.getResource();
        String serverCode = jedis.get(RedisConstant.PHONE_VERIFY_CODE_KEY + phoneNumber);

        if (serverCode == null || !serverCode.equals(code)) {
            return new ResponseEntity<String>("验证码不正确,请重新输入", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("验证码验证成功", HttpStatus.OK);
    }

    public static void main(String[] args) {
        System.out.println(getCode());
    }
}
