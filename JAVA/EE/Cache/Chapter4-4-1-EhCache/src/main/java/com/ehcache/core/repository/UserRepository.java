package com.ehcache.core.repository;

import com.ehcache.core.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

// 此注解作用于类，定义了使用的缓存的名字
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @Cacheable的作用是使findByName的返回值User将被加入缓存(cacheNames为users)
     *
     * 同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问
     */
    @Cacheable(key = "#p0", condition = "#p0.length() < 10")
    User findByName(String name);

}
