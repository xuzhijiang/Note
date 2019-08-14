# Bean的Scope

再谈生命周期之前有一点需要先明确：

> Spring 只帮我们管理单例模式 Bean 的**完整**生命周期，对于 prototype 的 bean ，Spring 在创建好交给使用者之后则不会再管理后续的生命周期。

## 旧版本的Spring

旧版本的Spring是5种scope:

- singleton – Return a single bean instance per Spring IoC container
- prototype – Return a new bean instance each time when requested
- request – Return a single bean instance per HTTP request.
- session – Return a single bean instance per HTTP session.
- globalSession – Return a single bean instance per global HTTP session.

---
	适合于所有项目的Scope：

	1. Singleton：每个Spring IoC容器中只有一个Bean的实例，这是spring bean的默认范围，这个Spring容器共享这个实例。使用此范围时，请确保bean没有共享成员变量，否则可能导致数据不一致问题

	2. Prototype：每次调用bean时都会新建一个Bean的实例


	仅适合于Web项目：
	
	1. Request： 这与原型范围相同，用于Web应用程序,将为每个HTTP请求创建一个新的bean实例.
	2. Session： 用于Web应用程序，将通过容器为每个HTTP session创建一个新bean
	3. GlobalSession： 用于为Portlet应用程序创建全局session bean.(现在很少用的技术).
---

## 新的spring版本是6种

>singleton, prototype,request,session,application,websocket,最后四种request,session,application,websocket仅在Web应用程序中可用。后4种在实际中用的也不多.

# Bean的初始化和销毁

经常会遇到在Bean在构造完成后使用之前或者在使用完毕之后销毁之前做些操作，有两种方式可以做到这一点：

* 使用注解方式 
* 使用配置类方式

## Spring Bean的配置:

> Spring框架提供了三种配置bean方法:

1. 基于注释的配置- 使用@Service或@Component,然后使用@Scope annotations提供范围信息.
2. 在xml中编写.
3. 从Spring 3.0开始,可以使用@Configuration,@ComponentScan和@Bean。

## 问题

>向一个Singleton Bean注入一个Prototype Bean，后者会被实例化几次？

答: prototype bean使用一次,就实例化一次.

## 小结

1. Spring ApplicationContext负责初始化spring bean配置文件中定义的Spring Beans。
2. Spring ApplicationContext还负责bean中的依赖注入，可以通过setter或构造函数方法，也可以通过spring autowiring(自动装配)实现依赖注入.

## Spring IoC容器中的Bean生命周期

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

![](pics/Bean-Life-Cycle.jpg)

>示例:beanscope