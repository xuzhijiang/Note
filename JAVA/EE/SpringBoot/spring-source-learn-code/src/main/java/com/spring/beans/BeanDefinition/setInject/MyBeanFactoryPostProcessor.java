package com.spring.beans.BeanDefinition.setInject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    // spring在初始化bean之前,提供了一个修改BeanDefinition的机会,也就是修改实例化Bean的行为
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 从BeanDefinitionMap<beanName,BeanDefinition>中得到beanName对应的BeanDefinition.
        GenericBeanDefinition rootBeanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("instA");
        // rootBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        // 懒加载: 只有使用到的时候,bean才会被初始化,容器启动的时候,不会被实例化
        // 非懒加载: 容器启动的时候,会被实例化
        // 这里相当于修改图纸
        // rootBeanDefinition.setLazyInit(true);

        // rootBeanDefinition.setBeanClass(InstD.class);
        // rootBeanDefinition.setAbstract(true);


        // 指定初始化的时候,到底使用哪个构造器
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("person");
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0, "张三");
        //constructorArgumentValues.addIndexedArgumentValue(0,  26);
        genericBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
    }
}
