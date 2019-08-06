# Spring和springmvc父子容器概念

在spring和springmvc进行整合的时候，一般情况下我们会使用不同的配置文件来配置spring(使用root-context.xml)和springmvc(使用servlet-context.xml),因此我们的应用中会存在至少2个ApplicationContext实例(分别加载这两个xml得到的)，

>由于是在web应用中，因此最终实例化的是ApplicationContext的子接口WebApplicationContext.

![](spring-springmvc.png)

图显示了2个WebApplicationContext实例，为了进行区分，分别称之为：

- Servlet WebApplicationContext
- Root WebApplicationContext

## Servlet WebApplicationContext

Servlet WebApplicationContext：这是对J2EE三层架构中的web层进行配置，
如控制器(controller)、视图解析器(view resolvers)等相关的bean。
通过spring mvc中提供的DispatchServlet来加载配置，通常情况下，配置文件的名称为servlet-context.xml。

## Root WebApplicationContext

Root WebApplicationContext：这是对J2EE三层架构中的service层、dao层进行配置，如业务bean，数据源(DataSource)等。通常
配置文件名称为root-context.xml。在web应用中，其一般通过`ContextLoaderListener`来加载。

## Root WebApplicationContext初始化过程详解

>Root WebApplicationContext创建过程源码分析:>spring-boot-learn-code中的`com.spring.CommonSource.ContextLoaderListener`

1. ContextLoaderListener(上下文加载器的监听器)会被优先初始化，被初始化时，其会根据`<context-param>`元素中`contextConfigLocation`参数指定的配置文件路径，在这里就是`"/WEB-INF/spring/root-context.xml”`，来创建WebApplicationContext实例。
并调用ServletContext的setAttribute方法，将其设置到ServletContext中，
属性的key为`”org.springframework.web.context.WebApplicationContext.ROOT”`，最后的”ROOT"字样表明这是一个Root WebApplicationContext。

## Servlet WebApplicationContext创建过程源码分析

DispatcherServlet负责创建Servlet WebApplicationContext，并尝试将ContextLoaderListener创建的ROOTWebApplicationContext设置为自己的parent。

其类图继承关系如下所示：

```java
public class DispatcherServlet extends FrameworkServlet {}

public abstract class FrameworkServlet extends HttpServletBean{}

public abstract class HttpServletBean extends HttpServlet{}
```

因此在应用初始化时，其`HttpServlet的init方法`会被调用,也就是HttpServletBean.init()会被调用,HttpServletBean的init方法中，
调用了initServletBean()方法，在HttpServletBean中，这个方法是空实现。
FrameworkServlet覆盖了HttpServletBean中的initServletBean方法。

>见: spring-boot-learn-code中的`com.spring.CommonSource.FrameworkServlet`

DispatcherServlet在初始化时，会根据`<init-param>`元素中`contextConfigLocation`参数指定的配置文件路径，
即`"/WEB-INF/spring/servlet-context.xml”`，来创建Servlet WebApplicationContext。同时，其会调用ServletContext的getAttribute方法来判断是否存在Root WebApplicationContext。如果存在，则将其设置为自己的parent。这就是父子上下文(父子容器)的概念。

## 父子容器的作用

当我们尝试从child context(即：Servlet WebApplicationContext)中获取一个bean时，如果找不到，则会委派给parent context (即Root WebApplicationContext)来查找。

如果我们没有通过ContextLoaderListener来创建Root WebApplicationContext，那么Servlet WebApplicationContext的parent就是null，也就是没有parent context。

## 为什么要有父子容器?

笔者理解，父子容器的作用主要是划分框架边界:

在J2EE三层架构中，在service层我们一般使用spring框架，而在web层则有多种选择，如springmvc、struts等。因此，通常对于web层我们会使用单独的配置文件

例如在下面的案例中，一开始我们使用servlet-context.xml来配置web层，
使用root-context.xml来配置service、dao层。如果现在我们想把web层从spring mvc替换成struts，那么只需要将servlet-context.xml替换成Struts的配置文件struts.xml即可，而root-context.xml不需要改变。

## 单独只使用`servlet-context.xml`

事实上，如果你的项目确定了只使用spring和spring mvc的话，你甚至可以将service 、dao、web层的bean都放
到servlet-context.xml中进行配置，并不是一定要将service、dao层的配置单独放到
root-context.xml中，然后使用ContextLoaderListener来加载。
在这种情况下，就没有了Root WebApplicationContext，只有Servlet WebApplicationContext。
