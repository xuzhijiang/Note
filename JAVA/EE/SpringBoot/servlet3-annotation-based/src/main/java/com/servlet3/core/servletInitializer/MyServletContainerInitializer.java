package com.servlet3.core.servletInitializer;

import com.servlet3.core.filter.AngleFilter;
import com.servlet3.core.handlertypes.ITuling;
import com.servlet3.core.listener.AngleListener;
import com.servlet3.core.servlet.AngleServlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

// javax.servlet.ServletContainerInitializer在javax.servlet:javax.servlet-api:3.0.1这个依赖下

// 在org.springframework:spring-web这个jar包下的META-INF/services中有一个名为:
// javax.servlet.ServletContainerInitializer的文件,里面内容是这个javax.servlet.ServletContainerInitializer
// 接口的实现类: org.springframework.web.SpringServletContainerInitializer

// 本项目我们没有引入org.springframework:spring-web,所以我们自己在META-INF/services下 写了一个
// 名字为 javax.servlet.ServletContainerInitializer 的文件,文件内容为
// javax.servlet.ServletContainerInitializer接口的实现类: MyServletContainerInitializer

// 真实的tomcat启动的时候会调用:
// WebappServiceLoader<ServletContainerInitializer> loader = new WebappServiceLoader<>(context);
// loader.load(ServletContainerInitializer.class);

// 所以tomcat启动后,我们的MyServletContainerInitializer的onStartup()会被调用.

//容器启动的时候会将@HandlesTypes指定的这个类型的子类（实现类，子接口等）传递过来；也就是传入感兴趣的类型；
@HandlesTypes(value = {ITuling.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    // tomcat启动的时候会调用这个onStartup方法,来注册servlet/listener/filter
    // 会把com.servlet3.core.handlertypes.ITuling的实现类作为参数传入第一个参数,然后就可以通过反射来创建了
    /**
     * tomcat启动的时候，会运行onStartup方法；
     *
     * Set<Class<?>> arg0：感兴趣的类型的所有子类型；
     * ServletContext arg1:代表当前Web应用的ServletContext；一个Web应用一个ServletContext；
     *
     * 1）、使用ServletContext注册Web组件（Servlet、Filter、Listener）
     * 2）、使用编码的方式，在项目启动的时候给ServletContext里面添加组件；
     * 		必须在项目启动的时候来添加；
     * 		1）、ServletContainerInitializer得到的ServletContext；
     * 		2）、ServletContextListener得到的ServletContext；
     */
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("感兴趣的类型：");
        for (Class clazz : set) {
            System.out.println("类型: " + clazz.getCanonicalName());
        }

        // 通过ServletContext来注册我们的Listener
        servletContext.addListener(AngleListener.class);

        // 注册Servlet
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("angleServlet", new AngleServlet());
        //配置servlet的映射信息
        servletRegistration.addMapping("/angleServlet");

        // 注册Filter
        FilterRegistration.Dynamic angleFilter = servletContext.addFilter("angleFilter", AngleFilter.class);
        //配置Filter的映射信息
        angleFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
}
