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
@HandlesTypes(value = {ITuling.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    // tomcat启动的时候会调用这个onStartup方法,来注册servlet/listener/filter
    // 会把com.servlet3.core.handlertypes.ITuling的实现类作为参数传入第一个参数,然后就可以通过反射来创建了
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        for (Class clazz : set) {
            System.out.println("类型: " + clazz.getCanonicalName());
        }

        // 通过ServletContext来注册我们的Listener
        servletContext.addListener(AngleListener.class);

        // 注册Servlet
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("angleServlet", new AngleServlet());
        servletRegistration.addMapping("/angleServlet");

        // 注册Filter
        FilterRegistration.Dynamic angleFilter = servletContext.addFilter("angleFilter", AngleFilter.class);
        angleFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
}
