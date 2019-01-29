### 概述

1. Spring所管理的对象，称为“Spring Bean”，它与普通Java对象的区别，在于它的创建是由Spring框架所负责的。
2. 对于不同的应用场景，需要不同的对象创建策略，比如在某个场景下，可以由一个对象来向整个应用提供服务，而在另一个场景下，则需要“按需创建”多个对象。
3. Spring提供了几种典型的对象创建策略，称为“Scope”，可以很方
便地配置特定类对象的创建策略。

### Bean的Scope

Scope决定了Spring容器如何新建和管理Bean的实例，分为5种。

1. 适合于所有项目的Scope：

* Singleton：一个Spring容器中只有一个Bean的实例，此为Spring
的默认配置，全容器共享一个实例。
* Prototype：每次调用新建一个Bean的实例。

2. 仅适合于Web项目：
* Request： Web项目中， 为每一个Http Request新建一个Bean实例。
* Session： Web项目中，为每一个Http Session新建一个Bean实例。
* GlobalSession： 仅在portal应用（现在很少用的技术） 中有用。

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

### 思考题

向一个Singleton Bean注入一个Prototype Bean，后者会被实例化几次？

### 小结

#### Spring初始化Bean的过程

1. 资源定位(例如@ComponentScan所定义的扫描包)
2. Bean定义()
3. 发布Bean定义
4. 实例化
5. 依赖注入(DI)

#### Spring IoC容器中的Bean生命周期

