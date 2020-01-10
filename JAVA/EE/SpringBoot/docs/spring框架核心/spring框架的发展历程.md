# Spring框架技术的演化

1. Spring Framework - XML配置为主-->注解为辅
2. Spring Boot - 自动化配置--->注解为主
3. Spring Cloud - 单体应用--->分布式微服务系统

- [查看spring和springboot最新版本](https://spring.io/projects/spring-boot#learn)

# spring和springboot的jar包命名区别?

    spring的groupId命名: org.springframework
    springboot的groupId命名: org.springframework.boot

# Spring 5重要特性

- 使用新模块改进了日志记录 -  spring-jcl。
- 文件操作使用NIO 2流，从而提高了性能。
- 支持Reactor 3.1 Flux和Mono以及RxJava 1.3和2.1作为Spring MVC控制器方法的返回值。
- 支持Kotlin，Project Lombok，JSON Binding API作为Jackson和GSON的替代品。
- Spring WebFlux – Spring getting Reactive.

# JDK Version Range

- Spring Framework 5.1.x: JDK 8-12
- Spring Framework 5.0.x: JDK 8-10
- Spring Framework 4.3.x: JDK 6-8

# Spring Boot 2.0的特点

Spring Boot 2.x will no longer support Java 7 and below, being Java 8 the minimum requirement.Spring Boot 2.0 要求 Java 8 作为最低版本，许多现有的 API 已更新，以利用 Java 8 的特性

Spring Boot 2.0 基于 Spring Framework 5 构建,Spring的新特性均可以在Spring Boot 2.0中使用

>Spring Boot 2.0提供了两个技术栈: 同时支持传统的Servlet技术栈和新增的Reactive技术栈，现在两者都可以用于开发当前流行的“前后端分离”的Web应用，另外，后者更适合于开发微服务.

## Servlet Stack

Servlet技术栈用于开发经典的Java Web应用，使用MVC框架，能很方便地访问各种数据库，跑在Tomcat这种经典Web容器上。

Spring MVC is built on the Servlet API and uses a synchronous blocking I/O architecture with a one-request-per-thread-model.

1. Servlet Containers
2. Servlet API
3. Spring Security
4. Spring MVC
5. Spring Data Repositories JDBC, JPA, NoSQL

## Reactive Stack

Reactive技术栈是全新的，用于开发高性能的分布式软件系统系统，特别适合于开发微服务.

Spring Boot 2.0还提供了支持响应式编程特性的容器: 嵌入式 Netty 服务器(作为默认的响应式容器),由于 WebFlux 不依赖于 Servlet API，我们首次可以使用Netty 作为嵌入式服务器,该 spring-boot-starter-webflux 启动 POM 将拉取 Netty 4.1 和 Ractor Netty。注意：你只能将 Netty 用作响应式服务器，不提供 Servlet API 支持.

Spring WebFlux is a non-blocking web framework built from the ground up to take advantage of multi-core, next-generation processors and handle
massive numbers of concurrent connections.

1. Netty, Servlet3.1+ Containers
2. Reactive Streams Adapters
3. Spring Security Reactive
4. Spring WebFlux
5. Spring Data Reactive Repositories Mongo, Cassandra, Redis, Couchbase

## Servlet和Reactive技术栈的对比

1. Spring Boot 2.0所引入的两个技术栈，各有其适合的应用场景，使用Servlet技术栈的，适合于开发传统Web项目。开发微服务，推荐使用Reactive技术栈。
2. 当前主流的Web技术方案是“前后端分离”，如果使用Java开发后端服务，可以使用Spring MVC或WebFlux开发RESTful Service，诸如Thymeleaf之类传统的MVC视图技术也可以不用了， Vue/React/Angular用起来更方便和强大.

# Spring Framework最重要的两个核心特性

* 依赖注入(DI： DependencyInjection）:Spring使用“依赖注入”这种手段来管理`各类Java资源`，降低了各种资源的耦合
* 面向切面编程(AOP： Aspect-OrientedProgramming）:Spring通过动态代理技术实现了面向切面的编程(AOP），避免了编写大量重复的代码

# spring framework高级特性

- Spring ApplicationEvent(Spring的应用程序事件)
- Spring Aware-用到Spring容器本身
- Spring异步调用-@EnableAsync开启对异步任务的支持,使用@Async注解来声明其是一个异步任务.
- Spring计划任务 -@EnableScheduling 来开启对计划任务的支持，然后在要执行计划任务的方法上注解@Scheduled，声明这是一个计划任务。
