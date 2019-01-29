## 依赖注入与控制反转

### 理解“依赖注入”的概念

一个对象通常需要“依赖”其他对象所提供的各种服务。在实际开发中，这通常是使用对象组合来实现的。

要实现对象组合，就必须创建出所有用到的对象，然后依据它们之间的依赖关系将它们关联起来。比如A用到B，就要new出A和B两个对象，再让A对象关联上B对象（通常是让A对象的某个字段引用B对象）。

依赖注入”指的是不让对象本身自己解决自己的依赖（即自己new出自己所需要的对象），而是让一个专门的外部对象（称为IoC容器）创建好相关对象，再将它们提供（即“注入”）给需要用到它的对象，比如， IoC容器发现A“依赖”于B，于是它在new A时，自动new一个B，对象，并且设置A的相应字段引用这个B对象，这样一来， A对象自己就不需写代码去创建B对象了。

### 理解“IoC”与“DI”间的关系

“控制反转（IoC： Inversion of Control）”和“依赖注入（DI：Dependency Injection）” ， 前者是特性（即：我只管用，便不负责创建和管理对象，这事由外部负责），后者是技术实现手段（即：IOC这个特性，具体应该怎样用Java实现呢？答案是用依赖注入实现. ）。

> 简单地说： 控制反转是通过依赖注入实现的

### Spring Bean与Spring IoC容器

Spring中把每一个需要管理的对象称为Spring Bean(简称Bean),而Spring管理这些Bean的容器,称为Spring IoC容器。

#### IoC容器的功能

* 扫描和识别Bean之间依赖关系的能力
* 创建和装配Bean的能力

#### Spring IoC容器图解

容器是Spring框架的核心。 SpringIoC容器（即ApplicationContext对象） 负责创建Bean，并通过容器将功能类Bean注入到需要它的其他Bean中.

外界通过IoC容器获取Bean容器的两种方式

1. 按类型: `<T> T getBean(Class<T> requiredType)`
2. 按名称: `Object getBean(String name)`

通过实例学习Spring依赖注入: `示例项目(Spring Boot)： dependency_inject`

#### Spring应用中的常用容器类型

                    -----------------------------------
                    |     ApplicationContext          |
                    |       (Bean Factory)            |
                    |                                 |
                    -----------------------------------
                                    ^
                                    |
                    ----------------------------------
                    |                                |
 ClassPathXmlApplicationContext        AnnotationConfigApplicationContext   
   配置信息放在xml文件中                            配置信息由注解所提供

ApplicationContext实现了N多的接口，并且派生出了若干个子类，图中只画出了常用的两个.

### Spring容器的工作原理

通过在Bean上附加一些注解（annotation,例如@Configuration，@ComponentScan），Spring Container就知道了哪些Bean可以用，以及它们之间的依赖关系是什么样的，从而可以实现Bean的装配.

#### 使用ApplicationContext编程获取Bean对象

                            getBean(通过名字) 
Application Context       <-------------------      外部对象
                          ------------------->      
                                Bean

> 当某外部对象需要某个Bean来辅助它完成特定的工作时，它就把Bean的名字告诉IoC容器（Application.getBean方法），然后IoC容器就把创建好的Bean“发回”给需求者。

#### 用于声明Bean的注解：

* @Component组件，没有明确的角色，通常用于定义普通的没有特殊含义的Bean。
* @Service在业务逻辑层（service层）使用， 通常用于标识那些包容了“业务逻辑（business logic）”的Bean。
* @Repository在数据访问层（dao层）使用， 通常用于标识那些包容了数据存取（Data Access）代码的Bean
* @Controller在展现层使用，它即Spring MVC中的控制器。

> @Service 和 @Repository 都派生自@Component, 但没有添加任何的特性

#### 用于注入外部Bean的注解

* @Autowired： Spring提供的注解。
* @Inject： JSR-330提供的注解。
* @Resource： JSR-250提供的注解。

如果要使用JSR 250所提供的注解，需要在项目中添加以下引用：
```xml
<!--
https://mvnrepository.com/artifact/javax.annotation/jsr250-api
-->
<dependency>
<groupId>javax.annotation</groupId>
<artifactId>jsr250-api</artifactId>
<version>1.0</version>
</dependency>
```

#### 用于提供Bean之间依赖关系的“配置”类型的注解

* @Configuration：用于标识“配置类”， Spring Boot项目在启动时会自动扫描并加载标记有此注解的类。
* @Bean：在@Configuration类中附加于特定的方法之上，方法
返回的对象将成为一个Spring可以管理的Bean。

#### Bean的两种主要定义方式

直接使用@Component等注解向IoC声明——我是一个Bean:

```java
//使用@Component注解，可以定义一个Bean
@Component
public class MyAnnotationBean {
}
```

```java
//对于没有注解的普通Java类
//则需要配合使用Configuration类来定义
//Spring的IoC容器才能识别它
public class POJOBean {
}

@Configuration
//使用@ComponentScan指定额外的包扫描组件
@ComponentScan({"com.jinxuliang.dependency_inject.other"})
public class MyBeanConfig {

    //使用@Bean这个注解，
    //定义那些没有@Component注解的普通Java类
    @Bean
    POJOBean pojoBean(){
        return new POJOBean();
    }

    //将POJOBean通过构造方法注入到POJOBeanContainer中
    @Bean(name = "beanContainer")
    POJOBeanContainer pojoBeanContainer(){
        return  new POJOBeanContainer(pojoBean());
    }
}
```

#### Bean的实例化

@SpringBootApplication会通知Spring，启动一个组件扫描（Component Scan），查找所有相
关注解（比如@Component、 @Configuration等）的类，并将它们注册为可实例化的Bean。

```java
@SpringBootApplication
public class DependencyInjectApplication {

    public static void main(String[] args) {

        //获取Spring的IoC容器
        ApplicationContext context= SpringApplication.run(
                DependencyInjectApplication.class, args);

        //获取Bean的实例
        MyAnnotationBean myAnnotationBean = context.getBean(MyAnnotationBean.class);
        System.out.println(myAnnotationBean);

        POJOBean pojoBean=context.getBean(POJOBean.class);
        System.out.println(pojoBean);
    }

}            
```

#### 使用@Value初始化字段值

> 给Bean中的字段添加@Value注解，能为其设置默认值。而且这个值除了直接指定，还可以从项
目的application.properties文件中提取.

```java
//展示@Value的用法
@Component
public class AtValueBean {

    //可以使用@Value指定默认值
    @Value("Hello")
    private String info;

    //从application.properties中提取值
    @Value("${message}")
    private String message;
}

```

#### 组合对象的构建

使用@Autowired，能将一个Bean“自动”从外部注入到本Bean中。

```java
//可以使用@Autowired构建组合对象
@Component
public class MyContainerBean {

    //直接附加在字段上
    @Autowired
    MyAnnotationBean annotationBean;

    private POJOBean pojoBean=null;

    //附加在字段的setter方法上
    @Autowired
    public void setPojoBean(POJOBean pojoBean) {
        this.pojoBean = pojoBean;
    }
}
```

#### 复合对象的构造方法注入

```java
public interface CustomerRepository {
}

//定义一个Bean，实现特定的接口
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
}

@Service
public class CustomerService {

    private CustomerRepository repository;

    //使用构造方法进行注入
    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return "CustomerService{" +
                "repository=" + repository +
                '}';
    }
}
// 这种编程方式，在实际开发中用得极多！
```

#### 可选的注入

```java
// 定义一个接口：
public interface IOptionalBean {
}

// 定义一个Bean实现这个接口：

//如果注释掉@Component，则UserOptionalBean中的相应字段
//将得到一个null
//@Component
public class OptionalBean implements IOptionalBean {
}

@Component
public class UseOptionalBean {
    //如果没有实现IOptional接口的Bean，则@Autowired将会失败
    @Autowired(required = false)
    private IOptionalBean optionalBean;

    @Override
    public String toString() {
        return "UseOptionalBean{" +
                "optionalBean=" + optionalBean +
                '}';
    }
}
```

> 可以通过@Autowired的required属性定义一个可选的注入

#### 普通（无注解）的组合对象的注入

```java
//对于没有注解的普通Java类
//则需要配合使用Configuration类来定义
//Spring的IoC容器才能识别它
public class POJOBean {
}

//这是一个普通的Java类，不包容任何与依赖注入
//相关的代码
public class POJOBeanContainer {

    private POJOBean pojoBean;

    public POJOBeanContainer(POJOBean pojoBean) {
        this.pojoBean = pojoBean;
    }

    @Override
    public String toString() {
        return "POJOBeanContainer{" +
                "pojoBean=" + pojoBean +
                '}';
    }
}

@Configuration
//使用@ComponentScan指定额外的包扫描组件
@ComponentScan({"com.jinxuliang.dependency_inject.other"})
public class MyBeanConfig {

    //使用@Bean这个注解，
    //定义那些没有@Component注解的普通Java类
    @Bean
    POJOBean pojoBean(){
        return new POJOBean();
    }

    //将POJOBean通过构造方法注入到POJOBeanContainer中
    @Bean(name = "beanContainer")
    POJOBeanContainer pojoBeanContainer(){

        return  new POJOBeanContainer(pojoBean());
    }
}

// Bean实例化测试代码
//通过名字实现Bean的实例化
POJOBeanContainer beanContainer= (POJOBeanContainer) context.getBean("beanContainer");
System.out.println(beanContainer);
```

#### @ComponentScan

在Spring Boot项目中，默认情况下，会扫描程序入口点所在的包及下属子包中的Bean组件。
如果Bean组件放在其他的包中，则可以给配置类添加@ComponentScan注解，指定要扫描的包名。

### 示例

```java
@Configuration
//使用@ComponentScan指定额外的包扫描组件
@ComponentScan({"com.jinxuliang.dependency_inject.other"})
public class MyBeanConfig {

    //使用@Bean这个注解，
    //定义那些没有@Component注解的普通Java类
    @Bean
    POJOBean pojoBean(){
        return new POJOBean();
    }

    //将POJOBean通过构造方法注入到POJOBeanContainer中
    @Bean(name = "beanContainer")
    POJOBeanContainer pojoBeanContainer(){

        return  new POJOBeanContainer(pojoBean());
    }
}
```

加入@ComponentScan注解之后，放在other包下的组件就能被识别了。

>通过@ComponentScan注解扫描特定的包只是这一注解最常见的用法罢了，这一注解其实包容有诸多的属性，比如它可以定义过滤器，将特定的Bean排除在外。请课后自行通过互联网查询其用法。

#### 将IoC容器注入到Bean中

```java
//当一个Bean需要访问容器中的其它Bean，或者需要访问外部资源时，
//可以让其实现ApplicationContextAware，从而获取对外部ApplicationContext的引用
@Component
public class ContextAwareBean implements ApplicationContextAware {

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    //此方法将会在实例化Bean时由Spring Framework自动调用，从而将一个ApplicationContext
    //对象注入到Bean中
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
}
// 获取了Application对象之后，就可以使用它来干IoC容器能干的所有事情……
```

#### 实现“条件注入”

如果在注入时发现符合要求的Bean有多个，可以使用@Qualifier来人为指定选哪个Bean。

```java
//它有两个实现类
public interface IBeanService {
}

@Service
public class BeanServiceA implements IBeanService {
}
```

```java
@Controller
public class BeanServiceController {
    @Autowired()
    @Qualifier("beanServiceA")
    IBeanService serviceA;

    @Autowired()
    @Qualifier("beanServiceB")
    IBeanService serviceB;

    //如果取消以下注释，则无法注入，因为实现IBeanService的有两个Bean
//    @Autowired
//    IBeanService service;

    @Override
    public String toString() {
        return "BeanServiceController{" +
                "serviceA=" + serviceA +
                ", serviceB=" + serviceB +
                '}';
    }
}
```

### 小结

> 本PPT展示了Spring应用中实现依赖注入的基本编程方式，需要熟练掌握。
依赖注入是Spring最重要也是最基础的特性之一，一定要认真掌握，否则，会给后面的学习带来困难。
