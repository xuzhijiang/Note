package com.spring.beans.BeanDefinition.setInject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.spring.beans.BeanDefinition.setInject"})
//@Import(value = {InstD.class}) // 这样InsD就可以不使用@Component注解了,也就是@Import可以把InstD的BeanDefinition导入到BeanDefinitionMap<beanName, BeanDefinition>中.
// 注意不是把InstD的class,而是BeanDefinition导入到BeanDefinitionMap中.
//@Import(value = {MyImportBeanDefinitionRegister.class})
//@Import(value = {MyImportSelector.class}) // 可以通过@Import往BeanDefinitionMap中放BeanDefinition
public class MainConfig {
}
