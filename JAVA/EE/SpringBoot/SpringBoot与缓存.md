# SpringBoot与缓存

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