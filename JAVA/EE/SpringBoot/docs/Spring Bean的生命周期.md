### 概述

1. Spring所管理的对象，称为“Spring Bean”，它与普通Java对象的区别，在于它的创建是由Spring框架所负责的。
2. 对于不同的应用场景，需要不同的对象创建策略，比如在某个场景下，可以由一个对象来向整个应用提供服务，而在另一个场景下，则需要“按需创建”多个对象。
3. Spring提供了几种典型的对象创建策略，称为“Scope”，可以很方
便地配置特定类对象的创建策略。

### Spring Bean定义

通常，如果您正在使用Spring MVC应用程序并且您的应用程序使用Spring Framework配置的.则Spring IoC容器会在应用程序启动时初始化Spring bean，并且在请求Bean时，会自动注入依赖项。

Spring Bean没什么特别之处(Spring Bean is nothing special,)，
我们通过Spring container初始化的Spring framework中的任何对象都称为Spring Bean。

任何普通的Java POJO类如果它被配置为通过容器通过提供配置元数据信息来初始化都可以是Spring Bean

### Bean的Scope

Scope决定了Spring容器如何新建和管理Bean的实例，分为五个范围(Scopes)。

1. 适合于所有项目的Scope：

* Singleton：一个Spring容器中只有一个Bean的实例，这是spring bean的默认范围，每个容器只创建一个bean实例,全容器共享一个实例。使用此范围时，请确保bean没有共享实例变量，否则可能导致数据不一致问题
* Prototype：每次调用bean时都会新建一个Bean的实例。

2. 仅适合于Web项目：
* Request： 这与原型范围相同，但它意味着用于Web应用程序,将为每个HTTP请求创建一个新的bean实例.
* Session： 它意味着用于Web应用程序，将通过容器为每个HTTP session创建一个新bean
* GlobalSession： 用于为Portlet应用程序创建全局session bean.(现在很少用的技术).

> Spring Framework是可扩展的，我们也可以创建自己的范围。但是，
大多数情况下，我们对框架提供的范围很满意。

#### SingleTon示例：

```java
// 设定Bean的Scope是Singleton
@Component
@Scope("singleton")
public class Singleton {
}

// 测试代码：
private static void testSingletonBean(ApplicationContext context) {
        //Singleton模式，始终只有一个实例
        System.out.println("Singleton模式的两个Bean");
        Singleton singleton1 = context.getBean(Singleton.class);
        Singleton singleton2 = context.getBean(Singleton.class);
        System.out.println(singleton1 == singleton2); //输出：true
}
```

#### Prototype示例

```java
@Component
@Scope("prototype")
public class Prototype {
}

private static void testPrototypeBean(ApplicationContext context) {
    //Prototype Scope模式，每次请求都实例化一个对象。
    System.out.println("Prototype模式的两个Bean");
    Prototype prototype1 = context.getBean(Prototype.class);
    Prototype prototype2 = context.getBean(Prototype.class);
    System.out.println(prototype1 == prototype2); //输出：false
}
```

### Bean的初始化和销毁

在我们实际开发的时候，经常会遇到在Bean在使用之前或者之后做些必要的操作，有两种方式可以做到这一点：

* 使用注解方式 

```java
//使用注解定义初始化与销毁时自动调用的方法
@Component
public class InitAndDestoryAnnotationBean {

    //对象构造完毕时调用
    @PostConstruct
    public void init(){
        System.out.println("InitAndDestoryAnnotationBean's PostConstruct method.");
    }

    //对象销毁前调用
    @PreDestroy
    public void destory(){
        System.out.println("InitAndDestoryAnnotationBean's PreDestroy method.");
    }
}

```

* 使用配置类方式

```java
// POJO作为Bean，使用配置类

//这是一个普通的POJO类
//希望在其初始化时，调用init()方法
//在其销毁时，调用destory()方法
public class InitAndDestoryBean {
    public void init(){
        System.out.println("InitAndDestoryBean's init method.");
    }

    public void destory(){
        System.out.println("InitAndDestoryBean's destory method.");
    }
}

@Configuration
public class BeanConfig {
    //指定POJO类作为Bean的初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destory")
    InitAndDestoryBean initAndDestoryBean() {
        return new InitAndDestoryBean();
    }
}
```

### Spring Bean Configuration:

> Spring Framework提供了三种配置bean方法,以便在application中使用:

1. Annotation Based Configuration (基于注释的配置 )- 使用@Service或@Component annotations。
可以使用@Scope annotations提供范围详细信息。

2. XML Based Configuration基于XML的配置 - 通过创建Spring配置XML文件来配置bean。
如果您使用的是Spring MVC框架，则可以通过在web.xml文件中编写一些样板代码来自动加载基于xml的配置。

3. (Java Based Configuration)基于Java的配置 - 从Spring 3.0开始，我们可以使用java程序配置Spring bean。
用于基于java的配置的一些重要注释是@Configuration，@ ComponentScan和@Bean。

### 思考题

向一个Singleton Bean注入一个Prototype Bean，后者会被实例化几次？

### 小结

### Spring Bean生命周期

Spring Bean是任何Spring应用程序中最重要的部分。

1. Spring ApplicationContext负责初始化spring bean配置文件中定义的Spring Beans。

2. Spring Context还负责bean中的注入依赖，可以通过setter或构造函数方法，
也可以通过spring autowiring(自动装配)实现依赖注入.

有时我们想要初始化bean类中的资源，例如在任何客户端请求之前初始化时创建数据库连接或验证第三方服务。 Spring框架提供了不同的方法，通过它我们可以在springbean生命周期中提供后初始化和预破坏方法。
(post-initialization and pre-destroy methods)

#### Spring初始化Bean的过程

1. 资源定位(例如@ComponentScan所定义的扫描包)
2. Bean定义(将Bean的定义保存到BeanDefinition的实例中)
3. 发布Bean定义(IoC容器装载Bean定义)
4. 实例化(创建Bean的实例对象)
5. 依赖注入(DI)(例如@Autowired注入的各类资源)

#### Spring IoC容器中的Bean生命周期(示例SpringBeanLifeCycle)

1. 初始化
2. 依赖注入
3. setBeanName方法(接口BeanNameAware)
4. setBeanFactory方法(接口BeanFactoryAware)
5. setApplicationContext方法(接口ApplicationContextAware(需要容器实现ApplicationContext接口才会被调用))
6. postProcessBeforeInitialization方法(BeanPostProcessor的预初始化方法(注意，它是针对全部bean生效))
7. 自定义初始化方法(@PostConstruct标注方法)
8. afterPropertiesSet方法(接口InitializingBean)
9. postProcessAfterInitialization方法(BeanPostProcessor的后初始化方法(注意，它是针对全部bean生效))
10. 生存期
11. 自定义销毁方法(@PostDestroy标注方法)
12. destroy方法(接口DisposableBean)