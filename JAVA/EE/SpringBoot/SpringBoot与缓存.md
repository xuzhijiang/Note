# SpringBoot与缓存

## 为什么引入缓存

往往数据库查询操作会成为影响用户使用体验的瓶颈，此时使用缓存往往是解决这一问题非常好的手段之一.

## 缓存注解

1. @CacheConfig：用于配置该类中会用到的共用的缓存配置。在这里@CacheConfig(cacheNames = "users")：配置了该数据访问对象中返回的内容将存储于名为users的缓存对象中，我们也可以不使用该注解，直接通过@Cacheable自己配置缓存集的名字来定义。
2. @Cacheable：配置了findByName函数的返回值将被加入缓存。同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问。该注解主要有下面几个参数：

    - value、cacheNames：两个等同的参数（cacheNames为Spring 4新增，作为value的别名），用于指定缓存存储的集合名,指定缓存组件的名字，CacheManager管理多个cache组件，对缓存的真正CRUD操作在Cache中，每一个缓存都有自己唯一的一个名字

    - key：缓存对象存储在Map集合中的key值，非必需，缺省按照函数的所有参数组合作为key值，若自己配置需使用SpEL表达式，比如：@Cacheable(key = "#p0")：使用函数第一个参数作为缓存的key值,@Cacheable(cacheNames = "emp" , key = "#id"),缓存的名字是emp，key是方法中参数id的值.
    
    - condition：缓存对象的条件，非必需，也需使用SpEL表达式，只有满足表达式条件的内容才会被缓存，比如：@Cacheable(key = "#p0", condition = "#p0.length() < 3")，表示只有当第一个参数的长度小于3的时候才会被缓存，若做此配置上面的AAA用户就不会被缓存.
    
    - unless：否定缓存，当unless指定的条件为true,该方法的返回值就不会被缓存，可以获取结果进行判断.它不同于condition参数的地方在于它的判断时机，该条件是在函数被调用之后才做判断的，所以它可以通过对result进行判断。// 使用unless判断结果为null就不缓存
    @Cacheable(cacheNames = "emp",condition = "#id > 0",unless = "#result == null ")
    
    - keyGenerator：用于指定key生成器，非必需。若需要指定一个自定义的key生成器，我们需要去实现org.springframework.cache.interceptor.KeyGenerator接口，并使用该参数来指定。需要注意的是：该参数与key是互斥的
    
    - cacheManager：指定缓存管理器，非必需。只有当有多个时才需要使用
    
    - cacheResolver：指定缓存解析器，非必需。需通过org.springframework.cache.interceptor.CacheResolver接口来实现自己的缓存解析器，并用该参数指定。

>除了这里用到的两个注解之外，还有下面几个核心注解：

3. @CachePut：配置于函数上，能够根据参数定义条件来进行缓存，它与@Cacheable不同的是，它每次都会调用函数，所以主要用于数据新增和修改操作上
4. @CacheEvict：配置于函数上，通常用在删除方法上，用来从缓存中移除相应数据。除了同@Cacheable一样的参数之外，它还有下面两个参数：

* allEntries：非必需，默认为false。当为true时，会移除所有数据
* beforeInvocation：非必需，默认为false，会在调用方法之后移除数据。当为true时，会在调用方法之前移除数据。

## 缓存配置

在Spring Boot中用@EnableCaching注解自动化配置合适的缓存管理器CacheManager，Spring Boot根据下面的顺序去侦测缓存提供者：

1. Generic
2. JCache (JSR-107)
3. EhCache 2.x
4. Hazelcast
5. Infinispan
6. Redis
7 .Guava
8. Simple

>除了按顺序侦测外，我们也可以通过配置属性spring.cache.type来强制指定。我们可以通过debug调试查看cacheManager对象的实例来判断当前使用了什么缓存。

### 使用EhCache

以常用的EhCache为例，看看如何配置来使用EhCache进行缓存管理。

在Spring Boot中开启EhCache非常简单，只需要在工程中加入ehcache.xml配置文件并在pom.xml中增加ehcache依赖，框架只要发现该文件，就会创建EhCache的缓存管理器。

```xml
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

完成上面的配置之后，再通过debug模式运行单元测试，观察此时CacheManager已经是EhCacheManager实例，说明EhCache开启成功了。

对于EhCache的配置文件也可以通过application.properties文件中使用spring.cache.ehcache.config属性来指定，比如：`spring.cache.ehcache.config=classpath:config/another-config.xml`

## 有了Ehcache，为啥还要使用Redis缓存

虽然EhCache已经能够适用很多应用场景，但是由于EhCache是进程内的缓存框架，在集群模式下时，各应用服务器之间的缓存都是独立的，因此在不同服务器的进程间会存在缓存不一致的情况.

在一些要求高一致性（任何数据变化都能及时的被查询到）的系统和应用中，就不能再使用EhCache来解决了，这个时候使用集中式缓存是个不错的选择,所以就使用Redis进行数据缓存。

>Spring Boot会在侦测到存在Redis的依赖并且Redis的配置是可用的情况下，使用RedisCacheManager初始化CacheManager。为此，我们可以单步运行我们的单元测试，可以观察到此时CacheManager的实例是org.springframework.data.redis.cache.RedisCacheManager

## JSR107

Java Caching定义了5个核心接口，分别是CachingProvider, CacheManager, Cache, Entry和Expiry。

- CachingProvider:定义了创建、配置、获取、管理和控制多个CacheManager。一个应用可以在运行期访问多个CachingProvider。
- CacheManager:定义了创建、配置、获取、管理和控制多个唯一命名的Cache，这些Cache存在于CacheManager的上下文中。一个CacheManager仅被一个CachingProvider所拥有。
- Cache:是一个类似Map的数据结构并临时存储以Key为索引的值。一个Cache仅被一个CacheManager所拥有。
- Entry:是一个存储在Cache中的key-value对。
- Expiry:每一个存储在Cache中的条目(Entry)有一个定义的有效期。一旦超过这个时间，条目为过期的状态。一旦过期，条目将不可访问、更新和删除。缓存有效期可以通过ExpiryPolicy设置。

**使用JSR107需要导入如下包**

```xml
<dependency>
	<groupId>javax.cache</groupId>
    <artifactId>cache-api</artifactId>
</dependency>
```

## Spring缓存抽象

Spring从3.1开始定义了org.springframework.cache.Cache和org.springframework.cache.CacheManager接口来统一不同的缓存技术；并支持使用JCache（JSR-107）注解简化我们开发；

- Cache接口是缓存的组件规范定义，包含缓存的各种操作集合；
- Cache接口下Spring提供了各种xxxCache的实现；如RedisCache，EhCacheCache , ConcurrentMapCache等；
- 每次调用需要缓存功能的方法时，Spring会检查检查指定参数的指定的目标方法是否已经被调用过；如果有就直接从缓存中获取方法调用后的结果，如果没有就调用方法并缓存结果后返回给用户。下次调用直接从缓存中获取。
- 使用Spring缓存抽象时我们需要关注以下两点；
  - 确定方法需要被缓存以及他们的缓存策略
  - 从缓存中读取之前缓存存储的数据

## 重要概念和缓存注解

| Cache          | 缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等 |
| -------------- | ------------------------------------------------------------ |
| CacheManager   | 缓存管理器，管理各种缓存（Cache）组件                        |
| @Cacheable     | 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存     |
| @CacheEvict    | 清空缓存(Evict:赶出)                                     |
| @CachePut      | 保证方法被调用，又希望结果被缓存                             |
| @EnableCaching | 开启基于注解的缓存                                           |
| keyGenerator   | 缓存数据时key生成策略                                        |
| serialize      | 缓存数据时value序列化策略                                    |

**简要说明：**

- @Cacheable注解加载方法中，那么该方法第一次会查询数据库，然后就会吧数据放在缓存中，使用Cache 进行数据的读取等操作。
- @CacheEvict删除缓存，例如根据id删除用户，那么也要删除缓存中的用户信息
- @CachePut更新缓存，例如更新用户信息后，同时也要更新缓存中的用户信息


**注解@CachePut使用**

既调用方法，又更新缓存数据，当修改了数据库的某一个数据，同时更新缓存

**service层代码如下**

```java
@CachePut(value = "emp")
public Employee updateEmp(Employee employee){
    employeeMapper.updateEmp(employee);
    return employee;
}
```

**Controller层代码如下**

```java
@GetMapping("emp")
public Employee update(Employee employee){
    Employee emp = employeeService.updateEmp(employee);
    return emp;
}
```

**测试说明**

- 先查询id为1的员工信息，第一次请求将查询数据库，然后放入缓存中
- 在执行更新id为1的员工信息，再查询id为1的员工信息，返回的是更新之前缓存中的员工信息
- 原因在于 @Cacheable中的key默认是参数，值是返回结果，查询缓存key是1，value是employee对象，更新方法中的@CachePut注解key是传入的employee对象，value是返回的employee对象，
- 更新之后查询应该返回的是更新的数据，也就是缓存中的数据没有更新，原因在于两次的key不一样导致，修改如下

```java
 @CachePut(value = "emp" ,key = "#employee.id")
public Employee updateEmp(Employee employee){
    employeeMapper.updateEmp(employee);
    return employee;
}
```

**注解@CacheEvict的使用**

注解@CacheEvict清除缓存，通过使用value，key属性清除指定缓存中指定key的缓存数据,有一个allEntries属性，默认是false，意思就是是否删除指定缓存中的所有key的缓存数据。beforeInvocation = false属性表示缓存的清除是否在方法执行之前执行，默认是在方法之后执行，如果出现异常就不会清除缓存，如果在方法之前执行，就是不管方法是否执行成功都会清除缓存数据

**services层代码如下**

```java
@CacheEvict(value = "emp",key = "#id")
public void deleteEmpById(Integer id){
    employeeMapper.deleteEmpById(id);
}
```

**controller层代码如下**

```java
@GetMapping("/delemp/{id}")
public void deleteEmp(@PathVariable("id")Integer id){
    employeeService.deleteEmpById(id);
}
```



**@Cacheable，@CachePut，@CacheEvict的区别**

- 注解@Cacheable是先调用缓存中的数据，如果没有在调用@Cacheable注解的方法
- 注解@CachePut是先调用目标方法，然后再将目标方法的返回结果放入缓存数据中
- 注解@CacheEvict的执行先后可以谁用属性配置改变

**注解@Caching复杂缓存配置的使用**

**Mapper层代码如下**

```java
@Select("select * from employee where lastName = #{lastName}")
Employee getEmpByLastName(String lastName);
```

**service层代码如下**

```java
 @Caching(
     cacheable = {
         @Cacheable(value = "emp",key = "#lastName")
     },
     put = {
         @CachePut(value = "emp",key = "#result.id"),
         @CachePut(value = "emp",key = "#result.email")
     }
 )
public Employee getEmpByLastName(String lastName){
    Employee emp = employeeMapper.getEmpByLastName(lastName);
    return emp;
}
```

上述定义的复杂缓存规则简单讲就是使用名字查询后，缓存中有了key为id的缓存信息，key为email的缓存信息

**Controller层代码如下**

```java
@GetMapping("/emp/lastName/{lastName}")
public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
    return employeeService.getEmpByLastName(lastName);
}
```

**注解@CacheConfig的使用**

在前面中我们对每一个方法都写了@CacheEvict(value = "emp",key = "#id")中的value属性，指定缓存到哪里。我们可以使用@CacheConfig注解指明一个类的所有方法都缓存到哪里，用什么key等信息

```java
@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeService {
```

**总结**

缓存默认使用的ConcurrentMapCacheManager == ConcurrentMapCache，将数据保存在ConcurrentMap,但是在开发中我们经常使用的缓存中间件：redis，memcached.ehcahe等