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
# 默认情况下，扫描的basePackage是spring boot的根目录(这里指的是应用启动类Application.java类所在的目录)，
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

    需要注意的是，一旦你自己提供了MapperScannerConfigurer，或者配置了MapperFactoryBean，
    那么mybatis-spring-boot-starter的自动配置功能将会失效。此时所有关于mybatis与spring进行整合的配置，都需要由你自行控制。 