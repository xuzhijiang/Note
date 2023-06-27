package com.atguigu.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.atguigu.bean.Car;

/**
 * BeanPostProcessor原理:,
 * 具体细节:
 *
 * 		1). 先执行: populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
 * 		2). 在给bean进行属性赋值之后,在initializeBean中执行BeanPostProcessor的相关方法:
 *
 * 		initializeBean
 * 		{
 * 			applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * 			invokeInitMethods(beanName, wrappedBean, mbd); 执行自定义初始化
 * 			applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *		}
 * 
 * 4）、BeanPostProcessor【interface】：bean的后置处理器；
 * 		 在bean初始化前后进行一些处理工作；
 * 		postProcessBeforeInitialization:在初始化之前工作,根据源码中的文档说明: 在bean已经创建完成,但是在任何的bean初始化回调方法之前(比如like InitializingBean's {@code afterPropertiesSet} or a custom init-method,,@PostConstruct)
 * 		postProcessAfterInitialization:在初始化之后工作,在任何的bean初始化回调方法之后调用(比如like InitializingBean's {@code afterPropertiesSet} or a custom init-method,@PostConstruct)
 * 
 * Spring底层对 BeanPostProcessor 的使用；
 * 		bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async等都是利用BeanPostProcessor来完成的.
 *
 * 		举例: ApplicationContextAwareProcessor: 作用是给我们组件中 设置ioc容器 (组件只需要实现,ApplicationContextAware,ApplicationContextAwareProcessor就会给容器设置ioc容器)
 * @author lfy
 *
 */
@ComponentScan("com.atguigu.bean")
@Configuration
public class MainConfigOfLifeCycle {
	
	//@Scope("prototype")
	@Bean(initMethod="init",destroyMethod="detory")
	public Car car(){
		return new Car();
	}

}
