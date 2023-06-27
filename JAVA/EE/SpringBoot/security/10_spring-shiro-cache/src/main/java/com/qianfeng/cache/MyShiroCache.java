package com.qianfeng.cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * @ClassName MyShiroCache
 * @Description: 完成每个用户的 权限信息的redis缓存(注意: 这个Cache缓存的是用户的权限信息,而不是把session信息保存到redis,要区分开)
 * @Author 臧红久
 * @Date 2019/10/15
 * @Version V1.0
 **/
@Getter
@Setter
public class MyShiroCache implements Cache{

    private String name;// 权限缓存的标识
    private RedisTemplate<String,Object> template;
    public MyShiroCache(){}

    public MyShiroCache(String name) {// 在CacheManager中会调用此方法
        this.name = name;
    }


    // 以下为 Cache的主要逻辑

    /**
     * todo: 检查缓存，以用户凭证为key
     * @param o =key=用户凭证 ~= 用户名
     * @return
     * @throws CacheException
     */
    @Override
    public Object get(Object o) throws CacheException {
        System.out.println("检查权限缓存");
        Object data = template.opsForValue().get(o.toString());
        if (data == null) {
            System.out.println("权限信息，检查缓存，未命中");
            return null;
        }
        System.out.println("权限信息，检查缓存，命中");
        return data;
    }

    /**
     * todo: 将从数据库中查询出的该用户的权限信息 存入缓存(redis)
     * @param o =key=用户凭证 ~= 用户名
     * @param o2 = 权限数据
     * @return
     * @throws CacheException
     */
    @Override
    public Object put(Object o, Object o2) throws CacheException {
        // redis 王朝甲 ： xxxxxxx
        // redis ”权限缓存的固定标识：王朝甲"：xxxx
        template.opsForValue().set(o.toString(),o2);
        return null;
    }

    /**
     * todo: 可以移除某个用户的权限信息的缓存，用于 subject.logout();
     * @param o =key=用户凭证 ~= 用户名
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object o) throws CacheException {
        System.out.println(o+"的权限信息缓存被清除");
        template.delete(o.toString());
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
