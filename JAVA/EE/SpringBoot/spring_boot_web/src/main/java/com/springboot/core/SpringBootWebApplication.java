package com.springboot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBootServletInitializer是WebApplicationInitializer的子类,使得可以从部署在外部web容器上的,传统的war包中运行SpringApplication.
 *
 * a. 如果我们打成war，就可以以传统的方式,部署到任何外部的tomcat或其它jee容器中(经典的 classic Java EE application servers (some of which cost a lot of money!) like WebSphere, WebLogic, or JBoss?”),外部容器那将执行configure()中的逻辑.
 *
 * b. 如果我们打成jar,我们就需要添加相同的逻辑到main方法中,因此嵌入式容器可以执行.(spring-boot-starter-web中默认集成了tomcat,所以spring boot默认启动的是tomcat,如果你需要使用具体的tomcat版本,需要配置 "<tomcat.version>8.0.3</tomcat.version>)" 属性,当然,如果你想要jetty,而不是tomcat,就需要把spring-boot-starter-web中的tomcat给exclude掉,然后再添加jetty的starter: spring-boot-starter-jetty,
 *
 */
@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

    private static final Class<SpringBootWebApplication> applicationClass = SpringBootWebApplication.class;

    public static void main(String[] args) {
        // SpringApplication.run(SpringBootWebApplication.class, args);
        SpringApplication sa = new SpringApplication(applicationClass);
        sa.run(args);
    }

    // SpringBootWebApplication继承SpringBootServletInitializer，并重写里面的configure方法:
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注册applicationClass作为配置类.
        return builder.sources(applicationClass);
    }
}

/**
 * a fat jar: This jar is handy because it includes all the other dependencies and things like your web server inside the archive. You can give anybody this one .jar and they can run your entire Spring application with no fuss: no build tool required, no setup, no web server configuration, etc: just java -jar ...your.jar.
 */
