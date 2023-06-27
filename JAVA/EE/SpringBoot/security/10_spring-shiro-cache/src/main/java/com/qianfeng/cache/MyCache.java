package com.qianfeng.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName MyCache
 * @Description: 在mybatis完成查询后，将数据缓存起来；当查询数据时先检查缓存,需要在mapper xml文件中配置才可以使用这个Cache
 * @Author 臧红久
 * @Date 2019/10/15
 * @Version V1.0
 **/
public class MyCache implements Cache{

    private String id;//当前的Mapper的 namespace
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    RedisTemplate<String,Object> template;
    public MyCache(){}
    public MyCache(String id){//id=是mybatis创建此缓存组件的实例时，会传入的
        System.out.println("create cache instance id:"+id);
        this.id=id;
    }
    // 以上是自定义内容
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * todo: 当查询执行后，将结果缓存起来
     * @param key  此次查询的标识，其中包含sql语句 (String)
     * @param value  此次查询的结果数据 User List<User>
     */
    @Override
    public void putObject(Object key, Object value) {
        System.out.println("缓存数据,"+key+" : "+value);
        template.opsForValue().set(key.toString(),value);//将查询结果，存入redis，缓存起来备用
    }

    @Override
    public Object getObject(Object key) {
        template = (RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate");
        System.out.println("检查缓存，key:"+key.getClass());
        Object cache = template.opsForValue().get(key.toString());//通过sql语句从redis检查是否有缓存可用
        if (cache != null) {
            return cache;
        }else{
            System.out.println("检查缓存，但未命中！");
            return null;
        }
    }

    /**
     * 删除某一个缓存数据
     * @param key
     * @return
     */
    @Override
    public Object removeObject(Object key) {
        template = (RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate");
        template.delete(key.toString());
        return null;
    }

    /**
     * 当一个mapper中，触发了任意一个 写操作，该mapper下的所有缓存都删除
     */
    @Override
    public void clear() {
        System.out.println("namespace:"+this.getId()+" 发生了写操作，清空所有相关缓存数据");
        template = (RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate");
        // 获取当前mapper下的所欲缓存key
        Set<String> keys = template.keys("*" + this.getId() + "*");
        // 删除这些key
        template.delete(keys);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }
}
