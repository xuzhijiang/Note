### Java数据存取技术的历史变迁

1. JDBC: 冗余代码比较多，流程和资源比较难以控制
2. EJB: 配置极为烦琐
3. JPA与Hibernate: 高度封装，一度成为标配，但不太适应互联网时代的要求
4. MyBatis使用十分简单，而且能非常容易定制SQL，移动互联时代受到欢迎。

### Spring Data JPA

JPA(Java Persistence API，Java持久化API，是定义了对象关系映射（ORM）以及实体对象持久化的标准接口，属于Java EE规范的组成部分。Hibernate从3.2版本开始就已经对JPA实现了完全的支持。

Spring Data JPA是对Hibernate的再一层封装，提供了一组Repository接口，通过动态代理实现数据存取功能

> 官网文档： https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

### HelloWorld流程

在创建Spring Boot项目时，可以选择要访问的数据库类型和采用的数据存取技术， Spring Boot
会基于这些信息进行自动化配置。(勾选MySQL和JPA)

#### 基于项目依赖让Spring Boot自动配置数据源

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

>在添加了项目依赖Spring Boot的spring-boot-starter-data-jpa后,Spring Boot就会默认为你配置数据源，这些默认的数据源主要是内存数据库，如h2、hqldb和Derby等内存数据，具体是哪个，依赖于你在项目的Maven配置文件pom.xml中加入了对哪个数据库的依赖.

### 小结

1. 本讲介绍了Spring Data JPA的基础知识与基本编程套路。应该来说， Spring Data JPA还是相当强大与灵活的，设计者考虑得相当周到。
2. Spring Data JPA的问题是封装得过多，可控性没那么强，就像是一个“黑盒子”，学习曲线比较陡峭，并且主要用于关系型数据库，因此，在实际开发中的应用受到限制。
3. 对于Spring Data JPA，了解本讲所介绍的内容也就差不多了。需要的时候， 再去官网查询其技术文档，了解其技术细节。

>提醒一下，其实这个技术领域包容非常多的技术特性，许多特性初学者不易理解，学习曲线比较陡峭。