package com.springboot.beanscope.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Arrays;

/**
 * Aware接口可以用于在初始化 other 时获得 Spring 中的一些对象，如Spring 上下文等。
 * 这些Aware接口会被Spring回调
 */
public class AwareBean implements ApplicationContextAware,
        ApplicationEventPublisherAware, BeanClassLoaderAware, BeanFactoryAware,
        BeanNameAware, EnvironmentAware, ImportAware, ResourceLoaderAware,
        ServletContextAware, ServletConfigAware {

    /**
     * 使用的类加载器
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("setBeanClassLoader: " + classLoader.getClass().getName());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // 检查bean的范围
        System.out.println("setBeanFactory: awareBeanName-xzj is singleton? " + beanFactory.isSingleton("awareBeanName-xzj"));
    }

    /**
     * 当前bean的名称
     */
    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName: " + s);
    }

    /**
     * Spring容器会自动调用这个方法，注入Spring IoC容器
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 返回一个字符串数组,表示所有的容器中的bean的名字
        System.out.println("ApplicationContextAware: " + Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @Override
    public void setEnvironment(Environment environment) {

    }

    /**
     * 获得Spring的资源加载器,并且加载资源
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource("classpath:spring-aware.xml");
        System.out.println("setResourceLoader: " + resource.getFilename());
    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {

    }

    /**
     * 读取上下文参数
     */
    @Override
    public void setServletContext(ServletContext servletContext) {

    }

    /**
     * 获取servlet配置参数
     */
    @Override
    public void setServletConfig(ServletConfig servletConfig) {

    }
}
