## 自定义starter

### 先学习mybatis starter的编写

我们先简单分析一下mybatis starter的编写，然后再编写自定义的starter。示例项目:mybatisspring

```xml
<!-- 在Maven中引入以下依赖，然后，mybatis相关的配置会被自动加入到spring container中: -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```

这个`mybatis-spring-boot-starter`中包含了`mybatis-spring-boot-autoconfigure`依赖，这个`mybatis-spring-boot-autoconfigure`中的/META-INF/spring.factories中包含了key为`org.springframework.boot.autoconfigure.EnableAutoConfiguration`，对应的值为`org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration`，所以Springboot能够加载`org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration`

```properties
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
```

>mybatis中的autoconfigure模块中使用了MybatisAutoConfiguration的自动化配置类。
这个MybatisAutoConfiguration需要在这些Condition条件下才会执行：

* @ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class }): 需要SqlSessionFactory和SqlSessionFactoryBean在classpath中都存在
* @ConditionalOnBean(DataSource.class): spring factory中需要存在一个DataSource的bean
* @AutoConfigureAfter(DataSourceAutoConfiguration.class): 需要在DataSourceAutoConfiguration自动化配置之后进行配置，因为mybatis需要数据源的支持

### 编写自定义的starter

>示例: logstarter, 和测试案例spring_mvc_controller_demo

### 总结

自定义springboot的starter，注意这两点:

1. 如果自动化配置类需要在程序启动的时候就加载，可以在META-INF/spring.factories文件中定义。如果本次加载还需要其他一些lib的话，可以使用ConditionalOnClass注解协助
2. 如果自动化配置类要在使用自定义注解后才加载，可以使用自定义注解+@Import注解或@ImportSelector注解完成
3. SpringBoot官方starter名字都是以:spring-boot-starter-开头的，例如spring-boot-starter-web,spring-boot-starter-actuator.
