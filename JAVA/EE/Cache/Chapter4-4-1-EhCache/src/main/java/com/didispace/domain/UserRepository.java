package com.didispace.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames = "users")// 此注解作用于类，定义了此类中使用缓存的名字
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @Cacheable的作用是使findByName的返回值将被加入缓存(缓存名为users)。
     * 同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问
     */
    @Cacheable(key = "#p0", condition = "#p0.length() < 10")
    User findByName(String name);

}
