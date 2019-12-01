package com.spi;

import javax.servlet.ServletContainerInitializer;
import java.util.ServiceLoader;

/**
 * 使用SPI的机制
 * 1. 当服务提供者提供了接口的一种具体实现后,在工程的META-INF/services目录下创建一个以"接口全限定名"为命名的文件,
 *          内容为接口实现类的全限定名
 * 2. 接口实现类所在的工程classpath中:
 * 3. 主程序通过java.util.ServiceLoader动态装载实现模块,他通过扫描META-INF/services目录下的配置文件找到实现类的全限定名,
 * 4. SPI的实现类必须携带一个不带参数的构造方法.
 */
public class MainClass {

    public static void main(String[] args) {
        ServiceLoader<IParseDoc> iParseDocs = ServiceLoader.load(IParseDoc.class);
        for (IParseDoc iParseDoc : iParseDocs) {
            iParseDoc.parse();
        }

        // spring boot就是通过spi机制来启动的
        // 在spring-web这个jar包下的META-INF/services中有一个名为:
        // javax.servlet.ServletContainerInitializer的文件,里面内容是这个javax.servlet.ServletContainerInitializer
        // 接口的实现类: org.springframework.web.SpringServletContainerInitializer
        // 所以tomcat启动的时候,会通过SPI机制,如下进行调用:

        // ServiceLoader<ServletContainerInitializer> servletContainerInitializers = ServiceLoader.load(ServletContainerInitializer.class);
        // for (ServletContainerInitializer servletContainerInitializer : servletContainerInitializers) {
        //     servletContainerInitializer.onStartup();
        // 此处其实就调用到了spring框架下的org.springframework.web.SpringServletContainerInitializer中的
        // onStartup()方法.这样就通过SPI机制把tomcat和spring两家公司不同的东西结合到了一起.
        // 然后就是spring框架在onStartup()方法中加载框架自己的东西了.
        // }

        // 上面通过SPI机制调用到了SpringServletContainerInitializer的onStartup()方法,
        // 在这个onStartup()方法中,最终会调用org.springframework.web.WebApplicationInitializer实现类的
        // onStartup()方法
        // 我们的servlet/listener/filter都是在这个onStartup()方法中注册的.
    }

}
