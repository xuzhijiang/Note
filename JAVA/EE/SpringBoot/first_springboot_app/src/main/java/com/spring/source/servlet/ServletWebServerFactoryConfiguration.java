package com.spring.source.servlet;

//import org.apache.catalina.Loader;
//import org.apache.catalina.Server;
//import io.undertow.Undertow;
import org.apache.catalina.startup.Tomcat;
//import org.apache.coyote.UpgradeProtocol;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.util.Loader;
//import org.eclipse.jetty.webapp.WebAppContext;
//import org.xnio.SslClientAuthMode;
//import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

class ServletWebServerFactoryConfiguration {

    @Configuration
    // Tomcat类和Servlet类必须在classloader中存在
    @ConditionalOnClass({ Servlet.class, Tomcat.class, UpgradeProtocol.class })
    // 当前Spring容器中不存在ServletWebServerFactory类型的实例
    @ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
    public static class EmbeddedTomcat {

        @Bean
        public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
            // 上述条件注解成立的话就会构造TomcatServletWebServerFactory这个ServletWebServerFactory
            return new TomcatServletWebServerFactory();
        }

    }

    @Configuration
    // // Server类、Servlet类、Loader类以及WebAppContext类必须在classloader中存在
    //@ConditionalOnClass({ Servlet.class, Server.class, Loader.class,WebAppContext.class })
    // 当前Spring容器中不存在ServletWebServerFactory类型的实例
    @ConditionalOnMissingBean(value = org.springframework.boot.web.servlet.server.ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
    public static class EmbeddedJetty {

        // 上述条件注解成立的话就会构造JettyServletWebServerFactory这个ServletWebServerFactory
        @Bean
        public JettyServletWebServerFactory JettyServletWebServerFactory() {
            return new JettyServletWebServerFactory();
        }

    }

    @Configuration
    // Undertow类、Servlet类、以及SslClientAuthMode类必须在classloader中存在
    //@ConditionalOnClass({ Servlet.class, ServerProperties.Undertow.class, SslClientAuthMode.class })
    // 当前Spring容器中不存在ServletWebServerFactory类型的实例
    @ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
    public static class EmbeddedUndertow {

        @Bean
        public UndertowServletWebServerFactory undertowServletWebServerFactory() {
            // 上述条件注解成立的话就会构造UndertowServletWebServerFactory这个EmbeddedServletContainerFactory
            return new UndertowServletWebServerFactory();
        }

    }

}
