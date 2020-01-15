# 注册servlet/listener/filter的方式有几种?

# 第一种方式: 通过web.xml来配置servlet/listener/filter.

# 第二种方式: 通过注解WebServlet/WebFilter/WebListener来替代web.xml

# 第三种方式: 通过SpringBoot注册Servelt、Filter、Listener

    SpringBoot默认是以jar包的方式启动嵌入式的Servlet容器来启动SpringBoot的web应用，没有web.xml文件。
    
    Spring提供了2种方式配置Servlet、Listener、Filter。一种是基于RegistrationBean的注册，另一种是基于扫描注解的注册

---    
    第一种: 基于RegistrationBean的注册
        **注意RegistrationBean是org.springframework.boot包下的,是属于springboot的.
    
    spring boot提供了ServletRegistrationBean，FilterRegistrationBean，ServletListenerRegistrationBean
        这3个东西来进行配置Servlet、Filter、Listener
---        

    第二种: 基于扫描 @WebFilter, @WebListener, @WebServlet注解的注册

    在对应的Servlet，Filter，Listener打上对应的注解@WebServlet，@WebFilter，@WebListener,
    在启动类加上注解@ServletComponentScan即可

```java
@SpringBootApplication
// 引入@ServletComponentScan的目的就是为了支持 Servlet3.0的相关注解,包括: @WebFilter, @WebListener, @WebServlet
@ServletComponentScan(basePackages = "com.springboot.servelt3")
// @ServletComponentScan(basePackageClasses = {HelloFilter.class, HelloServlet.class, MyServletContextListener.class})
public class SpringBootFilterApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootFilterApp.class, args);
    }
}
```
