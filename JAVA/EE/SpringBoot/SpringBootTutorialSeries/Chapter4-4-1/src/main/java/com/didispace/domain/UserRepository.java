package com.didispace.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 配置了findByName函数的返回值将被加入缓存。
     * 同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问
     * @param name
     * @return
     */
    @Cacheable(key = "#p0", condition = "#p0.length() < 10")
    User findByName(String name);

}
