### Spring技术的演化

1. Spring Framework - XML配置为主-->注解为辅
2. Spring Boot - 自动化配置--->注解为主
3. Spring Cloud - 单体应用--->分布式微服务系统

### Spring Framework的技术特点

> Spring使用简单的POJO(Plain Old Java Object，即无特殊要求的普通Java对象）来进行企业级开发。每一个被Spring管理的Java对象都称之为Bean；而Spring提供了一个IoC容器用来初始化Bean对象， 解决Bean对象间的依赖管理和对象的使用。

## Spring Framework中最重要的两个核心特性

* 依赖注入(DI： DependencyInjection）:Spring使用“依赖注入”这种手段来管理`各类Java资源`，降低了各种资源的耦合
* 面向切面编程(AOP： Aspect-OrientedProgramming）:Spring通过动态代理技术实现了面向切面的编程(AOP），避免了编写大量重复的代码

### 依赖注入

> 很多框架通过强迫应用继承它们的特定类或实现它们的特定接口从而导致应用与框架绑死。为了用某个框架，我自己写的MyClass类，必须继承自Base类，实现ICanDo接口。Java EE中这种情况非常常见，如下:

Spring使用依赖注入的方式解决了这个问题.MyClass类该怎么写就怎么写，你只需要告诉框架你“需要什么”就行了，框架会自动提供给你。无需继承特定的类，或实现特定的接口,如下:

1. Java依赖注入设计模式解决了硬编码依赖的问题，并使我们的应用程序松散耦合，并帮助我们使应用程序灵活且易于扩展,和可维护。 
2. 依赖注入使得依赖项的解析从编译时移动到运行时。
3. 在运行时而不是编译时绑定服务

#### 依赖注入的缺点(Disadvantages)

1.If overused, it can lead to maintenance issues because effect 
of changes are known at runtime.如果过度使用，则可能导致维护问题，因为更改的影响在运行时已知。

2. Dependency injection in java hides the service class dependencies 
that can lead to runtime errors that would have been caught at compile time.因为Java中的依赖注入是一种通过将对象绑定从编译时移到运行时来实现应用程序控制反转(IoC,Inversion of control)的方法,所以java中的依赖注入隐藏了可能导致在编译时捕获的运行时错误的服务类依赖项。

#### 依赖注入的实现

通过使用Java Reflection API和Java annotation来实现依赖注入的过程。我们所需要的只是annotation字段、构造函数、setter方法，并在配置xml文件或类中配置它们。

你是否想知道Spring Framework如何进行自动装配(autowiring)并调用对于Spring Framework是未知的方法么？这是通过大量使用Java Reflection来完成的，我们可以使用它来分析和修改类在运行时的行为。

是否使用基于 Constructor的依赖注入或基于setter method的依赖注入取决于您的要求。
例如,如果我的应用程序在没有服务类的情况下根本无法工作，
那么我会更喜欢基于构造函数的DI(依赖注入)，
否则我会选择基于setter方法的DI，只有在真正需要时才使用它。

> setter dependency injection依赖注入的最好例子之一是Struts2 Servlet API Aware interfaces

#### Spring IoC Container

Spring IoC是实现对象依赖关系之间松散耦合的机制。要在运行时实现对象的松散耦合和动态绑定，对象依赖项将由其他(assembler)组装器对象注入。 

Spring IoC容器是将依赖项(dependencies)注入对象并使其准备好可供我们使用的程序。

Spring IoC容器类是org.springframework.beans和org.springframework.context包的一部分。
Spring IoC容器为我们提供了 "解耦对象依赖关系" 的不同方法。

BeanFactory是根接口(root interface)，也就是最顶层接口,代表了Spring IoC容器。
ApplicationContext是BeanFactory接口的子接口，提供Spring AOP功能，i18n等。

ApplicationContext的一些有用的子接口是ConfigurableApplicationContext和
WebApplicationContext。 Spring Framework提供了许多有用的ApplicationContext实现类，
我们可以使用它们来获取spring上下文(context)，然后是获取Spring Bean。

> 我们使用的一些有用的ApplicationContext实现是:

1. AnnotationConfigApplicationContext：如果我们在独立的Java应用程序中使用Spring并using annotations for Configuration，那么我们可以使用它来初始化容器并获取bean对象。
2. ClassPathXmlApplicationContext：如果我们在独立应用程序中有spring bean配置xml文件，那么我们可以使用这个类加载文件并获取容器对象。
3. FileSystemXmlApplicationContext：这类似于ClassPathXmlApplicationContext，
除了可以从文件系统中的任何位置加载xml配置文件。
4. AnnotationConfigWebApplicationContext和XmlWebApplicationContext。

### AOP

面向切面编程(aspectoriented programming,AOP）有助于将遍布应用各处的功
能分离出来形成可重用的组件.

系统由许多不同的组件组成，每一个组件各负责一块特定功能，其职责不能也不应该重叠.诸如日志模块、事务管理和安全模块，等这样的系统服务为多个组件所需要，它们通常被称为“横切关注点”， AOP能方便让它们被多个组件所重用.

#### Spring Framework核心组件:SpEL，Context，Core，Beans

1. SpEL模块为执行期间操作对象提供了强大的表达式语言(expression language)
2. Context是在Beans和Core的基础上构建的，允许您访问设置中定义的任何对象。 Context模块的关键元素是ApplicationContext接口。
3. Core模块提供了框架的关键部分，包括IoC和DI属性。
4. Bean模块负责创建和管理Spring Beans  - 是应用程序上下文结构单元。

#### Spring Framework Web

Spring框架Web层由Web，Web-MVC，Web-Socket，Web-Portlet等组成。

1. Web模块提供下载文件，创建Web应用程序，rest web service等功能。
2. Web-MVC包含用于Web应用程序的Spring MVC实现。
3. Web-Socket使用Web应用程序中的Web-Sockets为客户端和服务器之间的通信提供支持
4. Web-Portlet通过portlet环境提供MVC实现

#### Spring Framework其他模块

Spring还包括许多其他重要的模块，例如AOP，Aspects，Instrumentation，Messaging和Test。

1. AOP实现面向方面的编程，并允许使用整个AOP功能库。
2. Aspects模块提供与AspectJ的集成，AspectJ也是一个功能强大的AOP框架。
3. Instrumentation负责支持在服务器应用程序中使用的类检测和类加载器。
4. Messaging模块提供STOMP支持。
5. 最后，Test模块使用TestNG或JUnit Framework提供测试。

#### Spring 5 Features

> Spring 5为Spring 4带来了大量的更新.Spring 5的一些重要特性是：

1. 支持Java 8，Java 9，Java EE 7，Java EE 8，Servlet 4.0，Bean Validation 2.0和JPA2.2。我很高兴看到Spring正在努力追赶所使用的主要技术的最新版本。
2. 使用新模块改进了日志记录 -  spring-jcl。
3. 文件操作使用NIO 2流，从而提高了性能。
4. 支持Reactor 3.1 Flux和Mono以及RxJava 1.3和2.1作为Spring MVC控制器方法的返回值。
5. 支持Kotlin，Project Lombok，JSON Binding API作为Jackson和GSON的替代品。
6. Spring WebFlux – Spring getting Reactive.
7. 支持JUnit 5
8. 通过Kotlin提供功能编程支持。

#### 小结： Spring Framework的主要特性

1. ①使用POJO进行轻量级和最小侵入式开发。
2. ②通过依赖注入和基于接口编程实现松耦合。
3. ③通过AOP和默认约定进行声明式编程，减少模式化的固定而重复的代码。
4. ④Spring是开放的，能很方便地整合其他开发框架。

## Spring Boot

### Spring Boot是什么？

从本质上来说， Spring Boot就是Spring，它做了那些没有它你自己也会去做的Spring Bean配置。简单地说，就是在早期技术的基础之上，实现了“自动化”配置。

> Spring Framework + 自动化配置 = Spring Boot

Spring Boot是当前Spring应用开发的主流。新项目不要再使用早期的Spring技术(比如使用XML定义Bean）。

#### Spring Boot的特点

1. Spring Boot提供了一些预先组织好的“起步依赖(spring-bootstarter）”，从而使开发者不再需要自行维护复杂的组件(jar包）依赖，而是只需要声明“我需要什么功能”就行了。
2. Spring Boot让Spring应用开发，从早期的以“组件为中心”，转换为“以功能为中心”。
3. Spring Boot 2.0与1.0有许多变化，并且只支持JDK 8及以上版本。
4. Spring Boot 在应用程序里嵌入了一个Servlet容器(Tomcat、 Jetty或Undertow），可独立运行，无需部署到外部的Servlet容器中。
5. Spring Boot 2.0还提供了支持响应式编程特性的容器(默认为Netty）， 因此，它就是一个jar包，可以直接使用java –jar命令来运行。
6. Spring Boot提供了一整套工具，称为Spring Boot Actuator，可以用于
监控Spring Boot应用程序运行的状态。
7. Spring技术家族的其他成员(比如Spring MVC），基本上都针对Spring
Boot进行了调整或重写，并且增加了新的成员，比如Web Flux。
8. Spring Boot还是Spring Cloud技术的基础，单个的微服务，可以使用Spring Boot来开发。
9. 要学习Spring，从Spring Boot起步！
10. Spring Boot 2.0基于Spring Framework 5.0构建，并提供了两个技术栈。
11. Servlet技术栈用于开发经典的Java Web应用，使用MVC框架，能很方便地访问各种数据
库，跑在Tomcat这种经典Web容器上。
12. Reactive技术栈是全新的，用于开发高性能的分布式软件系统系统，特别适合于开发微
服务.

##### Reactive Stack

Spring WebFlux is a non-blocking web framework built from the ground up 
to take advantage of multi-core, next-generation processors and handle
massive numbers of concurrent connections.

1. Netty, Servlet3.1+ Containers
2. Reactive Streams Adapters
3. Spring Security Reactive
4. Spring WebFlux
5. Spring Data Reactive Repositories Mongo, Cassandra, Redis, Couchbase

##### Servlet Stack

Spring MVC is built on the Servlet API and uses a synchronous blocking 
I/O architecture with a one-request-per-thread-model.

1. Servlet Containers
2. Servlet API
3. Spring Security
4. Spring MVC
5. Spring Data Repositories JDBC, JPA, NoSQL

### 学习指南

1. 学习Spring技术，要先学Spring Framework，再学Spring Boot，
2. Spring Boot学好之后，才能学Spring Cloud。这个顺序不能弄反了。
3. 早期的使用XML配置文件的Spring技术，可以跳过不学。
4. Spring Boot 2.0所引入的两个技术栈，各有其适合的应用场景，使用Servlet技术栈的，适合于开发传统Web项目。开发微服务，推荐使用Reactive技术栈。
6. 当前主流的Web技术方案是“前后端分离”，如果使用Java开发后端服务，可以使用Spring MVC或WebFlux开发RESTful Service，诸如Thymeleaf之类传统的MVC视图技术也可以不用了， Vue/React/Angular用起来更方便和强大。
