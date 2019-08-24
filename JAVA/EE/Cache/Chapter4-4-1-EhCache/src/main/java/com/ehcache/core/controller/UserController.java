package com.ehcache.core.controller;

import com.ehcache.core.domain.User;
import com.ehcache.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 为了可以更好的观察，缓存的存储，我们可以在单元测试中注入cacheManager。
    // 使用debug模式运行单元测试，观察cacheManager中的缓存集users以及其中的User对象的缓存加深理解。
    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/api/users")
    public User create(){
        User user = new User("AAA", 10);
        userRepository.save(user);
        return user;
    }

    @GetMapping("/api/get/user")
    public User queryUserByName(@RequestParam("n") String name) {
        // 并通过findByName函数完成两次查询。
        User u1 = userRepository.findByName(name);
        // 可以通过看hibernate打印,看是否查询数据库了.
        User u2 = userRepository.findByName(name);
        System.out.println("第二次查询：" + u2.getAge());
        return u2;
    }

    @GetMapping("/api/user/{id:\\d+}/{a}")
    public User changeUser(@PathVariable("id") Long id, @PathVariable("a") int age) {
        User u = userRepository.findOne(id);
        u.setAge(age);
        userRepository.save(u);
        return u;
    }

}
