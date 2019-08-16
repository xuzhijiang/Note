# 整合八：事务

上述所有的配置，还没有涉及到mybatis与spring进行整合另一个核心要点，即事务。整合后，我们需要将事务委派给spring来管理。

spring使用PlatformTransactionManager接口来表示一个事务管理器。其有2个重要的实现类：

1. DataSourceTransactionManager：用于支持本地事务，简单理解，你可以认为就是操作单个数据库的事务，
其内部也是通过操作java.sql.Connection来开启、提交(commit)和回滚(rollback)事务。
2. JtaTransactionManager：用于支持分布式事务，其实现了JTA规范，使用XA协议进行两阶段提交。
在本文中，我们主要介绍的是DataSourceTransactionManager，绝大部分情况下， 我们使用的都是这个事务管理器。

# 整合九：springboot

>mybatis开发团队为Spring Boot 提供了 mybatis-spring-boot-starter。你需要引入如下依赖： 

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```

使用了该starter之后，只需要定义一个DataSource即可，它会自动利用该DataSource创建需要使用到的SqlSessionFactoryBean、SqlSessionTemplate、以及ClassPathMapperScanner来自动扫描你的映射器接口，并针对每个接口都创建一个MapperFactoryBean，注册到Spring上下文中。

>关于mybatis-spring-boot-starter如何实现自动配置的相关源码，参见：org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration类。

```yml
# 默认情况下，扫描的basePackage是spring boot的根目录，
# 且只会对添加了@Mapper注解的映射器接口进行注册。(扫描basePackage下使用@Mapper注解的接口)

# 因此，最简单的情况下，你只需要在application.yml中进行数据源的相关配置即可，以下配置依赖于spring-boot-starter-jdbc： 

spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatis
    username: root
    password: your password
```

>之后，你就可以直接在你的业务bean中注入映射器接口来使用了。

需要注意的是，一旦你自己提供了MapperScannerConfigurer，或者配置了MapperFactoryBean，那么mybatis-spring-boot-starter的自动配置功能将会失效。此时所有关于mybatis与spring进行整合的配置，都需要由你自行控制。 