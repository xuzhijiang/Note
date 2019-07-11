package com.didispace.controller;

import com.didispace.domain.User;
import com.didispace.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解配置与EhCache使用
 */
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
    public int get() {

        // 并通过findByName函数完成两次查询。
        User u1 = userRepository.findByName("AAA");
        System.out.println("第一次查询：" + u1.getAge());

        User u2 = userRepository.findByName("AAA");
        System.out.println("第二次查询：" + u2.getAge());

        u1.setAge(20);
        userRepository.save(u1);
        User u3 = userRepository.findByName("AAA");
        System.out.println("第三次查询：" + u3.getAge());

        return u3.getAge();
    }

}
