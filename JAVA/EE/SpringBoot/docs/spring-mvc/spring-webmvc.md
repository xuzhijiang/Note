# Spring MVC

    mvc这种理念,具体的实现有springmvc/struts2

![](../pics/Spring-Web-MVC-framework介绍.png)

![](../pics/Spring-Web-MVC-framework-pic.png)

# 控制器(Controller)

![](../pics/SpringMVC-处理流程图.png)

![](../pics/RequestMapping注解详解.png)

>转发(forward) vs 重定向(redirect)

![](../pics/转发和重定向的区别.png)

学习过程中需要特别注意一下数据的传送方式，主要有两种：一是数据如何从client传给Server端，二是如何在Server端保存多个HTTP请求之间的状态信息。

# Spring Boot Web MVC 实现文件上传

Spring MVC对文件上传提供了良好的支持，而在SpringBoot中可以更为简单地配置文件上传所需的内容。

通常我们会在网页上设置一个form元素，里面再放置一个文件上传控件(type='file'的input元素）， 用户选择要上传的文件之后，点击“提交”按钮， 采用MIME值为“multipart/form-data”的方式上传文件到服务端。服务端只需要取出文件数据，再将其保存到硬盘上即可。

>Spring Boot Web MVC项目中文件上传相关配置

```
# MULTIPART(MultipartProperties)
# 是否启用Spring MVC多部分上传功能
spring.servlet.multipart.enable=true
# 将文件写入磁盘的阈值，值可以使用后缀“MB”或“KB”来表示兆字节或字节大小
spring.servlet.multipart.file-size-threshold=0
# 指定默认上传的文件夹
spring.servlet.multipart.location=d:/upload
# 限制单个文件最大大小，这里设置为5MB
spring.servlet.multipart.max-file-size=5MB
# 限制所有文件最大大小，这里设置为20MB
spring.servlet.multipart.max-request-size=20MB
# 是否延迟多部件文件请求的参数和文件的解析
spring.servlet.multipart.resolve-lazily=false
```

# Spring Boot Web MVC数据校验-validator

在实际开发中，为了保证数据的安全，必须对用户提交上来的数据进行有效性检测,数据有效性检测分为两类

1. 一类是在客户端进行的，常用的Web前端框架(比如Vue和React）都提供了相应的功能，在提交数据之前对数据的有效性进行检测;
2. 另一类则是服务端数据检测，就是在调用Spring Boot Web MVC控制器方法之前对客户端数据的有效性进行校验。

这里介绍的是如何使用Spring Boot MVC框架在服务端完成数据有效性检测的工作:

![](../pics/Spring-Validation01.png)    
![](../pics/Spring-Validation02.png)    
![](../pics/Spring-Validation03.png)    
![](../pics/Spring-Validation04.png)    
![](../pics/Spring-Validation05.png) 
  
![](../pics/数据校验示例01.png)
![](../pics/数据校验示例02.png)
![](../pics/数据校验示例03.png)

    常用的校验规则如下:

![](../pics/常用的数据校验规则.png) 

# Spring MVC异常处理

Spring Boot提供了一个默认的映射：/error，当处理中抛出异常之后，会转到/error中处理，并且该请求有一个全局的错误页面用来展示异常内容。

![](../pics/默认错误页面.png)

对于浏览器访问,返回默认错误页面的时候,状态吗为404,Spring Boot会使用这个默认页面将状态码显示在网页上.

对于非浏览器访问,例如使用Postman,Spring Boot会返回一个Json字符串，其中包容基础的出错信息。

![](../pics/postman访问返回的信息.png)

虽然，Spring Boot中实现了默认的error映射，但是在实际应用中，上面你的错误页面对用户来说并不够友好，我们通常需要自定义异常提示.

>Spring Boot项目的默认异常处理机制，是由一个内置的BasicErrorController实现的。

# Spring MVC 表单标签库

![](../pics/SpringMVC表单标签库01.png)

![](../pics/SpringMVC表单标签库02.png)

![](../pics/SpringMVC表单标签库03.png)

![](../pics/SpringMVC表单标签库04.png)
![](../pics/SpringMVC表单标签库05.png)
![](../pics/SpringMVC表单标签库06.png)
![](../pics/SpringMVC表单标签库07.png)
![](../pics/SpringMVC表单标签库08.png)

# 使用WebMvcConfigurer

![](../pics/使用WebMvcConfigurer配置SpringMVC01.png)
![](../pics/使用WebMvcConfigurer配置SpringMVC02.png)
![](../pics/使用WebMvcConfigurer配置SpringMVC03.png)
![](../pics/使用WebMvcConfigurer配置SpringMVC04.png)
![](../pics/WebMvcConfigurer目录加载问题.png)
![](../pics/WebMvcConfigurerAdapter过时01.png)
![](../pics/直接实现WebMvcConfigurer.png)

# Spring 整合 Spring MVC

![](../pics/DispatcherServlet组件.png)

![](../pics/DispatcherServlet工作原理.png)

![](../pics/Spring整合SpringMVC01.png)

![](../pics/Spring整合SpringMVC02.png)

![](../pics/Spring整合SpringMVC03.png)

![](../pics/Spring整合SpringMVC04.png)

![](../pics/Spring整合SpringMVC05.png)

![](../pics/Spring整合SpringMVC06.png)

![](../pics/Spring整合SpringMVC07.png)

# SpringBoot热加载

spring-boot-devtools是一个自动应用代码更改到最新的App上面去，使应用可以自动重启的模块
.原理是在发现代码有更改之后，重新启动应用，但是比速度比手动停止后再启动还要更快，更快的深层原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间（5秒以内）。

默认情况下，修改了/META-INF/maven, /META-INF/resources ,/resources ,/static ,/public或者/templates目录下的内容不会引起应用的重新启动。意思是，java代码，pom文件，application.properties(yml)文件的修改都会引起重新启动。

通过以下方式，我们可以指定只有哪些目录下的内容修改不会引起重启：

`spring.devtools.restart.exclude=static/**,public/**`

以上配置说明只有static目录和public目录下的内容修改后不会引起重启。

>由于默认的配置已经比较合理，所以有一种可能的情况是，我们希望在默认的配置下，添加其他我们想要的目录，在这个目录下修改内容时也不重新启动，此时可以将spring.devtools.restart.exclude改为spring.devtools.restart.additional-exclude即可。

![](../pics/实现热部署01.png)
![](../pics/实现热部署02.png)
![](../pics/实现热部署03.png)
![](../pics/实现热部署04.png)
![](../pics/实现热部署05.png)

>idea可以安装一个叫jRebel的热部署插件.

- [来源](https://www.funtl.com/zh/spring-mvc/第一个-Controller-控制器.html#概述)
