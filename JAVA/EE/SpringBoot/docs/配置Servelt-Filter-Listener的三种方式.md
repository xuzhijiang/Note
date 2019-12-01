注册servlet/listener/filter的方式:


第一种方式: 通过web.xml来配置servlet/listener/filter.
第二种方式: 通过注解WebServlet/WebFilter/WebListener来替代web.xml
第三种方式: 

# Spring boot配置Servelt、Filter、Listener

>实例:spring_boot_web

在SpringBoot中，不光将Spring的配置文件省略了，连web容器的web.xml文件都省略了，而之前，我们通常都是将Servelt、Filter、Listener等配置在web.xml中配置的，而SpringBoot提供了更加简化的配置.

Spring Boot集成了servlet容器，当我们在pom文件中增加spring- boot-starter-web的maven依赖时，不做任何web相关的配置便能提供web服务，这还得归功于spring boot自动配置的功能（因为加了EnableAutoConfiguration的注解），帮我们创建了一堆默认的配置，以前在web.xml中配置，现在都可以通过spring bean的方式进行配置，由spring来进行生命周期的管理.

>SpringBoot提供了2种方式配置Servlet、Listener、Filter。一种是基于RegistrationBean，另一种是基于注解。

## 基于RegistrationBean的配置

>spring boot提供了ServletRegistrationBean，FilterRegistrationBean，ServletListenerRegistrationBean这3个东西来进行配置Servlet、Filter、Listener,见:配置类WebConfig.java

## 基于注解的配置

>在对应的Servlet，Filter，Listener打上对应的注解@WebServlet，@WebFilter，@WebListener,在启动类加上注解@ServletComponentScan即可
