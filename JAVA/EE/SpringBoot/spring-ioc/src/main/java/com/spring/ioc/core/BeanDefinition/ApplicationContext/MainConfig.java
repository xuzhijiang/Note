package com.spring.ioc.core.BeanDefinition.ApplicationContext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.spring.ioc.core.BeanDefinition.ApplicationContext"})
// @Import有三种玩法

// @Import的第一种玩法:
// 这样InsD就可以不使用@Component注解了,也不用通过@Configuration+@Bean,
// 也就是只通过@Import就可以把InstD的BeanDefinition导入到BeanDefinitionMap<beanName, BeanDefinition>中.
// 注意不是把InstD的class,而是BeanDefinition导入到BeanDefinitionMap中.
//@Import(value = {InstD.class})

// @Import的第二种玩法: 通过一个ImportBeanDefinitionRegistrar接口的实现类来导入
//@Import(value = {MyImportBeanDefinitionRegister.class})

// @Import的第三种玩法: 通过实现ImportSelector接口来导入bean定义
@Import(value = {MyImportSelector.class}) // 可以通过@Import往BeanDefinitionMap中放BeanDefinition
public class MainConfig {
}
