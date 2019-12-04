package com.core.project.initializer;

import com.core.project.config.RootConfig;
import com.core.project.config.WebAppConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 通过spi机制加载 classpath下META-INF/services目录下的的javax.servlet.ServletContainerInitializer:
 *
 * WebappServiceLoader<ServletContainerInitializer> loader = new WebappServiceLoader<>();
 * loader.load(ServletContainerInitializer.class);
 */
// AbstractAnnotationConfigDispatcherServletInitializer是org.springframework:spring-webmvc这个jar包下的
public class MyStarterInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * IOC 父容器的启动类(创建Root WebApplicationContext所需的配置类)
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * IOC 子容器配置 web容器配置(创建Servlet WebApplicationContext所需的配置类)
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAppConfig.class};
    }

    /**
     *
     * @return 我们前端控制器DispatcherServlet的拦截路径(获得DispatchServlet拦截的url)
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
