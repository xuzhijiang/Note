package com.gmall.controller;

import com.gmall.service.RedissonLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedissonLockController {

    @Autowired
    RedissonLockService redissonLockService;

    @GetMapping("/incr2")
    public String incr3(){
        redissonLockService.useRedissonForLock();
        return "ok";
    }

    // 测试redisson锁是可重入的.
    @GetMapping("/lock")
    public Boolean lock(){
        return redissonLockService.lock();
    }

    // redisson解锁必须是同一个线程id,所以这个方法解锁无效
    @GetMapping("/unlock")
    public String unlock(){
        redissonLockService.unlock();
        return "ok";
    }

    @GetMapping("/read")
    public String read(){
        return redissonLockService.read();
    }

    @GetMapping("/write")
    public String write(){
        return redissonLockService.write();
    }

    // 停车 (占停车位)
    @GetMapping("/park")
    public Boolean park()  {
        return redissonLockService.park();
    }

    @GetMapping("/unpark")
    public Boolean unpark() {
        return redissonLockService.unpark();
    }

    // 领导要锁门了
    @GetMapping("/leaderShutDoor")
    public String shutDoor() {
        return redissonLockService.shutDoor();
    }

    @GetMapping("/staffLeave")
    public Boolean StaffLeave() {
        return redissonLockService.staffLeave();
    }
}
