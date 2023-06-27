package com.redis.core.springboot.jedis.controller;

import com.redis.core.springboot.jedis.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * 秒杀
 *
 * 测试之前先在redis中添加: Seckill:Stock:101 100件商品: set Seckill:Stock:101 800
 *
 * 测试非lua脚本: ab -n 1000  -c 200  -p seckill.txt -T  "application/x-www-form-urlencoded" 192.168.32.1:8080/seckill
 * 测试lua脚本秒杀: ab -n 1000  -c 200  -p seckill.txt -T  "application/x-www-form-urlencoded" 192.168.32.1:8080/seckillByScript
 * 测试lua脚本秒杀: ab -n 1000  -c 400  -p seckill.txt -T  "application/x-www-form-urlencoded" 192.168.32.1:8080/seckillByScript
 * 测试lua脚本秒杀: ab -n 1000  -c 800  -p seckill.txt -T  "application/x-www-form-urlencoded" 192.168.32.1:8080/seckillByScript
 *
 * seckill.txt内容: productId=101&
 */
@Controller
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    @GetMapping("/seckill")
    public String seckill() {
        return "seckill";
    }

    /**
     * 秒杀接口
     * @param productId 要秒杀的商品的id
     * @return 秒杀成功返回true,否则返回false
     */
    @PostMapping("/seckill")
    @ResponseBody
    public boolean doSecKill(@RequestParam("productId") String productId) {
        // 模拟生成一个用户Id
        Integer userId = new Random().nextInt(200000);
        // 要秒杀肯定要传一个用户Id和秒杀的商品的Id
        boolean result = seckillService.doSeckill(productId, userId);
        return result;
    }

    /**
     * 秒杀接口
     * @param productId 要秒杀的商品的id
     * @return 秒杀成功返回true,否则返回false
     */
    @PostMapping("/seckillByScript")
    @ResponseBody
    public boolean doSecKillByScript(@RequestParam("productId") String productId) {
        // 模拟生成一个用户Id
        Integer userId = new Random().nextInt(200000);
        // 要秒杀肯定要传一个用户Id和秒杀的商品的Id
        boolean result = seckillService.doSeckillByLuaScript(productId, userId);
        return result;
    }
}
