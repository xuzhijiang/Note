package com.spring.bean.lifecycle.core.beans;

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
import java.util.Arrays;

public class InstE implements ApplicationContextAware,
        ApplicationEventPublisherAware, BeanClassLoaderAware, BeanFactoryAware,
        BeanNameAware, EnvironmentAware, ImportAware, ResourceLoaderAware{

    /**
     * 使用的类加载器
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("*************setBeanClassLoader: " + classLoader.getClass().getName());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // 可以检查bean的scope
        // System.out.println(beanFactory.isSingleton("awareBeanName-xzj"));
    }

    /**
     * 参数是当前bean的名称
     */
    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName: " + s);
    }

    /**
     * 注入Spring IoC容器
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // getBeanDefinitionNames(): 返回一个字符串数组,表示ioc容器中所有bean的名字
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
        Resource resource = resourceLoader.getResource("classpath:spring-bean.xml");
        System.out.println("setResourceLoader: " + resource.getFilename());
    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {

    }
}
