缓存配置

完成了上面的缓存实验之后，可能大家会问，那我们在Spring Boot中到底使用了什么缓存呢？

在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：

Generic
JCache (JSR-107)
EhCache 2.x
Hazelcast
Infinispan
Redis
Guava
Simple

除了按顺序侦测外，我们也可以通过配置属性spring.cache.type来强制指定。我们可以通过debug调试查看cacheManager对象的实例来判断当前使用了什么缓存。

本文中不对所有的缓存做详细介绍，下面以常用的EhCache为例，看看如何配置来使用EhCache进行缓存管理。

在Spring Boot中开启EhCache非常简单，只需要在工程中加入ehcache.xml配置文件并在pom.xml中增加ehcache依赖，框架只要发现该文件，就会创建EhCache的缓存管理器。

在pom.xml中加入:

```xml
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

完成上面的配置之后，再通过debug模式运行单元测试，观察此时CacheManager已经是EhCacheManager实例，说明EhCache开启成功了。

对于EhCache的配置文件也可以通过application.properties文件中使用spring.cache.ehcache.config属性来指定，比如：`spring.cache.ehcache.config=classpath:config/another-config.xml`

