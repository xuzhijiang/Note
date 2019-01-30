## 概述

本讲PPT介绍Spring Framework所提供一些高级特性，这些特性在实际开发中用得并不算多，但了解它们还是必要的。这些特性包括：

* Spring ApplicationEvent
* Spring Aware
* Spring异步调用
* Spring计划任务

> 本讲的所有示例代码，均来自于IntelliJ项目： springframeworkadvance

### Spring ApplicationEvent

#### Spring 的应用程序事件

1. 当一个Bean处理完一个任务之后，如果希望另外一个Bean知道并能做相应的处理，这时我们就需要让另外一个Bean监听当前Bean所发送的事件
2. Spring的事件（ApplicationEvent）为Bean与Bean之间的消息通信提供了支持。

#### Spring Bean事件驱动编程步骤：

1. 自定义事件类，派生自ApplicationEvent。
2. 定义事件监听器，让其实现ApplicationListener接口。
3. 使用容器发布事件

#### 事件对象

* 任何一种事件，都必然携带着特定的信息，我们应该将它封装为类。
* 在Spring Boot中，事件类应该派生自ApplicationEvent类，并且调用基类的构造方法。
* 不同的事件，携带不同的信息，必须为其设计不同的事件类。`参考DemoEvent`

#### 事件监听者

事件监听者用于响应事件，必须实现ApplicationListener接口，并且通过泛型参数指明它所响应的事件对象类型,参考: DemoEventListener

> 当特定的事件触发时，事件监听者的onApplicationEvent()方法被回调。

#### 事件发布者

1. 事件的发布通过ApplicationContext完成。
2. 当特定的事件发布后，ApplicationContext会将事件参数对象转发给所有的事件监听者。

> 参考DemoPublisher

### Spring Aware

Spring依赖注入的特点之一就是默认情况下， Bean对Spring容器的存在是没有意识的。

但是在实际项目中，不可避免的要用到Spring容器本身的功能资源，这时Bean必须要意识到Spring容器的存在，才能调用Spring所提供的资源，这就是所谓的Spring Aware。

Spring提供了好几个Aware接口

SpringAware的目的是为了让Bean获得Spring容器的服务。因为ApplicationContextAware接口集成了MessageSource接口、ApplicationEventPublisher接口和ResourceLoader接口，所以Bean继承ApplicationContextAware可以获得Spring容器的所有服务，但原则上还是用到什么接口，就实现什么接口。

### Spring异步调用

1. Spring通过任务执行器（TaskExecutor）来实现多线程和并发编程。
2. 使用ThreadPoolTaskExecutor可实现一个基于线程池的TaskExecutor。
3. 需要在配置类中通过@EnableAsync开启对异步任务的支持，并通过在实际执行的Bean的方法中使用@Async注解来声明其是一个异步任务.

### 计划任务

1. 首先通过在配置类注解 @EnableScheduling 来开启对计划任务的支持，然后在要执行计划任务的方法上注解@Scheduled，声明这是一个计划任务。
2. Spring通过@Scheduled支持多种类型的计划任务， 包含fixDelay、 fixRate等