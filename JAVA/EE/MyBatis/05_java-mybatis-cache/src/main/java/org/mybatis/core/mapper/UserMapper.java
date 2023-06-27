package org.mybatis.core.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.mybatis.core.model.User;

// 添加这个注解,就可以不用在 XML 中添加 <cache/> 标签
// 这里的 readWrite 属性和 XML 中的 readOnly 属性一样，用于配置缓存是否为只读类型，
// 在这里 true 为读写，false为只读，默认为 true (相当于readOnly=false)
// 当同时使用注解方式和 XML 映射文件时，如果同时配置了二级缓存，就会抛出如下异常
// Cause: org.apache.ibatis.builder.BuilderException: Error parsing SQL Mapper Configuration.
// Cause: java.lang.IllegalArgumentException: Caches collection already contains value for com.mybatis.mapper.AnnotationUserMapper


//@CacheNamespace(
//        implementation = org.mybatis.caches.ehcache.EhcacheCache 相当于xml中 <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
//        默认为 PerpetualCache
//        eviction = FifoCache.class,
//        flushInterval = 60000,
//        size = 512,
//        readWrite = true
//)
// @CacheNamespaceRef(UserMapper.class) //相当于xml中的 <cache-ref namespace="org.mybatis.core.mapper.UserMapper"/>
public interface UserMapper {
    int insert(User user);
    public User selectById(Integer id);
}