package com.redis.core.springboot.redistemplate.service;

import com.alibaba.fastjson.JSON;
import com.redis.core.springboot.redistemplate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//  其实问题的本质在于"不存在User记录的情况下,进行User缓存"，可能会出现缓存脏数据，
//  但是存在User记录的情况下，肯定不会出现缓存脏数据。也就是如果只缓存已存在的记录，问题就解决了
@RestController
public class CreateCacheRightService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 因此，解决方案是给返回的记录加一个标识：
    // 这个缓存方案略微降低了第一次访问的性能，但是不需要考虑并发问题，也就不需要读写锁，
    // 因此代码简单，可靠性却很高。
    @Transactional
    public Integer getUser(Integer userId) {
        // 构建缓存的key
        String key = buildCacheKey(userId);
        Integer value = getFromCache(key);
        if (value == null) {
            User user = getOrCreateUser(userId);
            value = user.getId();
            if (!user.newlyCreated) {
                // 如果记录不是新创建的，就缓存:
                putIntoCache(key, user);
            }
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
        // 操作数据库从数据库中获取user
        User user = selectFromDb(userId);
        // 如果没有获取到user，说明原来没有此user，就创建
        if (user == null) {
            user = insertIntoDb(userId);
            user.newlyCreated = true; // 标记为新创建的
        } else {
            user.newlyCreated = false; //标记为不是新创建的，是从数据库中select的
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
