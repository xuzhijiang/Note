package com.core.project.config;

import com.core.project.interceptor.MyWebInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

/**
 * @desc: 类的描述: web容器(子容器) 只扫描Controller相关的注解
 */
@Configuration
// useDefaultFilters改为false,要不然过滤规则不起作用
@ComponentScan(basePackages = {"com.core.project"}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {RestController.class, Controller.class})
}, useDefaultFilters = false)
@EnableWebMvc // @EnableWebMvc Annotation用于在Spring框架中启用Spring Web MVC应用程序功能
// @Import Annotation用于注册LoginSecurityConfig中的bean进入IoC.
@Import(value = { SecurityConfig.class })
public class WebAppConfig implements WebMvcConfigurer {

    /**
     * 这些bean以前需要在spring-mvc.xml中配置
     *
     * <servlet>
     *    <servlet-name>dispatcherServlet</servlet-name>
     *    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     *    <init-param>
     *        <param-name>contextConfigLocation</param-name>
     *        <param-value>classpath*:/spring-mvc*.xml</param-value>
     *    </init-param>
     *    <load-on-startup>1</load-on-startup>
     * </servlet>
     */

    /**
     * 配置拦截器
     */
    @Bean
    public MyWebInterceptor myWebInterceptor() {
        return new MyWebInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("添加自定义的拦截器");
        registry.addInterceptor(new MyWebInterceptor());
    }

    /**
     * 文件上传下载的组件
     * @return
     */
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//        commonsMultipartResolver.setDefaultEncoding("UTF-8");
//        commonsMultipartResolver.setMaxUploadSize(1024*1024*10);// 10M
//        return commonsMultipartResolver;
//    }

    /**
     * 配置视图解析器
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * 注册处理国际化资源的组件
     * @return
     */
//    @Bean
//    public AcceptHeaderLocaleContextResolver localeResolver() {
//        AcceptHeaderLocaleContextResolver acceptHeaderLocaleContextResolver = new AcceptHeaderLocaleContextResolver();
//        return acceptHeaderLocaleContextResolver;
//    }

    /**
     * 配置消息转换器,用于处理json
     * @param converters
     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }

}
