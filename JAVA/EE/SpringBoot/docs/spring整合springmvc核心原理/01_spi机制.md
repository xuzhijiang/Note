# spi机制

    1. tomcat源码中spi机制的使用,加载org.springframework:spring-web这个jar包下的
    /META-INF/services/javax.servlet.ServletContainerInitializer这个文件

    2. 然后调用org.springframework.web.SpringServletContainerInitializer的onStartup()
    
    3. 然后调用org.springframework.web.WebApplicationInitializer的实现类的onStartup()
    
![](../pics/tomcat源码中spi机制的使用.png)

    代码示例: spring-source-learn-code/com.spi.MainClass