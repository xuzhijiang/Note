package com.security.springmvc.init;

import com.security.springmvc.config.ApplicationConfig;
import com.security.springmvc.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //spring容器，相当于加载 applicationContext.xml
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class};
    }

    //servletContext，相当于加载springmvc.xml
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    // 我们前端控制器DispatcherServlet的拦截路径(配置DispatchServlet拦截的url)
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
