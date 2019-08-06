package com.spring.CommonSource;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * AbstractContextLoaderInitializer：其用于动态的往ServletContext中注册一个ContextLoaderListener，从而创建Root WebApplicationContext
 *
 * AbstractDispatcherServletInitializer：其用于动态的往ServletContext中注册一个DispatcherServlet，从而创建Servlet WebApplicationContext
 *
 * AbstractAnnotationConfigDispatcherServletInitializer extends了上面2个接口
 *
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 获得创建Root WebApplicationContext所需的配置类
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //return new Class<?> [] { RootConfig.class };
        return new Class[0];
    }

    // 获得创建Servlet WebApplicationContext所需的配置类
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // return new Class<?> [] {App1Config.class};
        return new Class[0];
    }

    // 获得DispatchServlet拦截的url
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/app1/*"};
    }
}
