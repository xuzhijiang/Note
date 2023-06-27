# spring-boot-starter常见依赖模块说明

    Spring Boot将所有的功能场景都抽取出来，做成一个个的starters（启动器），
    只需要在项目里面引入这些starter相关场景的所有依赖都会导入进来。要用什么功能就导入什么场景的启动器

- spring-boot-starter-parent: 是一种特殊的starter，包含了一个 dependency-managemen部分，子项目中可以省略 版本号。而其他的starter提供了事实上的依赖。例如要搭建一个 web应用，需要我们提供：spring-boot-starter-web
- spring-boot-starter：核心模块，包括自动配置支持、日志和YAML
- spring-boot-starter-test：测试模块，包括JUnit、Hamcrest、Mockito
- spring-boot-starter：帮我们导入了web模块正常运行所依赖的组件；
- spring-boot-configuration-processor: 导入yml/properties配置文件处理器，导入后yml/properties配置文件进行绑定后就会有提示
- spring-boot-starter-web: web支持，SpringMVC， Servlet支持等
- mybatis-spring-boot-starter: SpringBoot的Mybatis启动器
- spring-boot-starter-data-jpa: spring data jpa支持
- spring-boot-starter-cache: Spring Boot缓存支持
- spring-boot-starter-data-redis: Spring Data Redis支持
- spring-boot-starter-actuator: 这个模块是图形化监控要用,多次强调过

# 父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
</parent>
<!-- spring-boot-starter-parent的父项目是spring-boot-dependencies -->

<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-dependencies</artifactId>
  <version>1.5.9.RELEASE</version>
  <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
<!-- spring-boot-dependencies是来真正管理Spring Boot应用里面所有依赖的版本号的；-->
```

    注意: spring-boot-starter-parent和spring-boot-dependencies都不直接添加依赖jar包
    
    以后我们自己项目的POM中导入依赖 默认是不需要写版本号的；
    （当然,如果没有在spring-boot-dependencies里面管理的依赖 自然需要声明版本号）