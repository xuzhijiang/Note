### Spring Boot项目的初始化过程

1. 开始
2. 收集各种条件和回调接口，例如ApplicationContextIntializer,ApplicationListener
-------->通告started()
3. 创建并准备Environment----------->通告environmentPrepared()
4. 创建并初始化ApplicationContext,例如设置Environment，加载配置等------------>
通告contextPrepared(), 通告contextLoaded()
5. refresh ApplicationContext完成最终程序的启动 ------------> 执行CommandLineRunner,通告finishd()
6. 结束

### 读入外部文件中的配置参数

>实例： springbootconfig

#### 装入外部XML配置文件

SpringBoot提倡零配置，即无xml配置，但是在实际项目中，可能有一些特殊要求
你必须使用xml配置，这时我们可以通过Spring提供的@ImportResource来加载
xml配置，例如：

```java
@ImportResource({"classpath:some-context.xml",
"classpath:another-context.xml"})
```

#### 从外部配置文件中提取参数

1. Spring Boot允许使用properties文件、 yaml文件或者命令行参数作为外部配置。
2. 在SpringBoot里，我们只需在application.properties定义属性，
直接使用@Value注入即可。
3. Spring Boot还提供了基于类型安全的配置方式，通过@ConfigurationProperties将properties属性和一个Bean及其属性关联，从而实现类型安全的配置。

### 用于测试的资源

可以在resources文件夹下放置其它资源，然后使用@Value配合Spring表达式，将它们的值注入到配置文件中.

左图展示了Spring Boot项目中如何从外部资源文件中读入数据，读取环境变量等相关技巧，基本上涵盖了日常开发中可能用到的各种场景。

### 强类型的配置方式(自动创建配置对象）

通过@ConfigurationProperties加载properties文件内的配置，通过prefix属性指定
properties的配置的前缀，必要时，也可以通过locations指定properties文件的位置，例如：

```java
@ConfigurationProperties(prefix="jxl",locations={"classpath:config/author.properties"})
```

注意需要添加以下项目依赖：

```xml
<!--允许使用@ConfigurationProperties-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

### 使用Profile

Profile是Spring用来针对不同的环境对不同的配置提供支持的，全局Profile配置使用application-{profile名字}.properties(如application-prod.properties)。

通过在application.properties中设置spring.profiles.active=profile名字来指定活动的Profile

```java
// 定义一个接口
//一个用于展示基于Profile选择不同Bean实例化而设计的接口
public interface IProfileBean {
}

// 两个Bean实现此接口
//开发阶段使用的Bean
@Component
@Profile("DEV")
public class DevBean implements IProfileBean {
}

//发布阶段使用的Bean
@Component
@Profile("PROD")
public class ProdBean implements IProfileBean {
}

// 在application.properties中指定以下配置，则使用DevBean：spring.profiles.active=DEV
```

### 引入XML配置Bean

尽管Spring Boot建议使用注解和扫描配置Bean，但是同样地，它并不拒绝使用XML配置Bean，因为有些框架(如Dubbo）是基于Spring的XML方式进行开发的。

这时，我们可以在Spring配置类中使用@ImportResource注解将放在外部XML配置文件中的Bean设置也一并导入，如下所示：

```java
@Configuration
@ComponentScan(basePackages = "com.jinxuliang.*")
@ImportResource(value = {"classpath:spring-other.xml"})
public class AppConfig {
......
}
```

### 自动配置与条件注解

>实例： autoconfig

#### 概述

1. 默认情况下， Spring Boot会启用自动配置，依据程序员在pom.xml中声明的项目依赖，或者是在application.properties中的设置参数，或者是命令行参数等地方提取出相应的信息，进行自动配置。
2. 在多数情况下，这种自动配置能满足需求。
3. 在特定的情况下，如果自动配置不能满足需求，我们还可以自定义相应的组件，利用Spring Boot的自动配置机制完成特定的配置工作.

#### @Conditional注解的用途

@Conditional根据满足某一个特定条件创建一个特定的Bean。比方说，当某一个jar包在一个类路径下的时候，自动配置一个或多个Bean；或者只有某个Bean被创建才会创建另外一个Bean。总的来说，就是根据特定条件来控制Bean的创建行为，这样我们可以利用这个特性进行一些自动的配置。

下面举一个例子，如何从application.properties中提取特定的配置参数，然后使用条件注解装载不同的组件……

#### 示例概述

>在项目配置文件中，定义一个dbType属性我们希望能依据DbType的具体值，在程序运行时动态选择启用MongoDB还是JDBC的数据存取组件, 例如: `application.properties: dbType=MYSQL`
