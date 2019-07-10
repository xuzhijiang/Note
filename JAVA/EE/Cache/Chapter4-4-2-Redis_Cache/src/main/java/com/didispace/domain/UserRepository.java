package com.didispace.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 由于EhCache是进程内的缓存框架，第一次通过select查询出的结果被加入到EhCache缓存中，
 * 第二次查询从EhCache取出的对象与第一次查询对象实际上是同一个对象,
 * 因此我们在更新age的时候，实际已经更新了EhCache中的缓存对象.
 *
 * Redis的缓存独立存在于我们的Spring应用之外，我们对数据库中数据做了更新操作之后，
 * 没有通知Redis去更新相应的内容，因此我们取到了缓存中未修改的数据，导致了数据库与缓存中数据的不一致.
 * 因此我们在使用缓存的时候，要注意缓存的生命周期
 *
 */
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(key = "#p0")
    User findByName(String name);

    // 我们只需要在更新age的时候，通过@CachePut来让数据更新操作同步到redis缓存中
    // 在redis-cli中flushdb，清空一下之前的缓存内容,然后再测试，发现调用save方法时，
    // 就已经将这条数据加入了redis缓存中了。
    @CachePut(key = "#p0.name")
    User save(User user);

    // 一定要注意缓存生命周期的控制，防止数据不一致的情况出现
}
