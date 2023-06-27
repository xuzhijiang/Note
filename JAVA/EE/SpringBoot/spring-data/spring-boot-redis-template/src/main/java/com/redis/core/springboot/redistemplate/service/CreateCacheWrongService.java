package com.redis.core.springboot.redistemplate.service;

import com.alibaba.fastjson.JSON;
import com.redis.core.springboot.redistemplate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

// 对于按需创建的数据库记录，如何进行缓存？在什么时候进行缓存?
// 是创建记录的时候缓存吗?还是其他时候?


//  例如，考察一个按需创建User, 并返回User ID的函数：
// 乍一看，好像没啥问题。本地测试，一切正常
@Service
public class CreateCacheWrongService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 但是，真实的环境下，代码可能是并发执行的，
    // 注意getAccount()方法使用了@Transactional注解，所以运行完此方法的时候，才会数据库事务提交。
    // 而只有数据库事务提交的时候，才会报错.
    // 这个时候，如果出现INSERT失败的情况.此时，缓存已经加进去了，只不过加的缓存记录是无效的，
    // 因为稍后的事务回滚，该记录并不会在数据库中存在。但是缓存中已经存在了这个数据,这个数据就是脏数据.
    @Transactional
    public Integer getUser(Integer userId) {
        // 构建缓存的key
        String key = buildCacheKey(userId);
        Integer value = getFromCache(key);
        if (value == null) {
            User user = getOrCreateUser(userId);
            value = user.getId();
            putIntoCache(key, user);
        }
        return value;
    }

    private void putIntoCache(String key, User user) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(user));
    }

    private Integer getFromCache(String key) {
        String jsonStr = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(jsonStr)) {
            User user = (User) JSON.parse(jsonStr);
            return user.getId();
        } else {
            return null;
        }
    }

    private String buildCacheKey(Integer userId) {
        return String.valueOf(userId);
    }

    User getOrCreateUser(Integer userId) {
        User user = selectFromDb(userId);
        if (user == null) {
            user = insertIntoDb(userId);
        }
        return user;
    }

    private User insertIntoDb(Integer userId) {
        User user = new User(1, 10, "aaaa");
        map.put(userId, user);
        return user;
    }

    private Map<Integer, User> map = new HashMap<>();

    private User selectFromDb(Integer userId) {
        return map.get(userId);
    }
}
