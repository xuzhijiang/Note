package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.atguigu.aop.LogAspects;
import com.atguigu.aop.MathCalculator;

// 源码讲解: https://www.bilibili.com/video/BV1ME411o7Uu?p=28

// 了解就好,不用记忆!!!
// AnnotationAwareAspectJAutoProxyCreator实现了SmartInstantiationAwareBeanPostProcessor,
// 所以他是一个后置处理器（在bean初始化前后做事情）.
// 所以,只要分析清楚AnnotationAwareAspectJAutoProxyCreator作为后置处理器做了哪些工作,整个流程就分析清楚了.
/**
 * 	总结：
 * 		1）、 通过@EnableAspectJAutoProxy 开启AOP功能
 * 		2）、 通过@EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 * 		3）、 AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
 * 		4）、容器的创建流程：
 * 			1）、创建容器的时候,会调用refresh()刷新容器,refresh()中有一个方法叫registerBeanPostProcessors（）,用于注册后置处理器；这一步会为所有后置处理创建对象,这里也就是会创建AnnotationAwareAspectJAutoProxyCreator对象
 * 			2）、refresh()里面还调用了一个方法叫finishBeanFactoryInitialization（）,作用是 初始化剩下的单实例bean,本例中LogAspects和MathCalculator的创建都在这里.
 * 				1）、创建业务逻辑组件和切面组件(LogAspects和MathCalculator的创建都在这里.)
 * 				2）、AnnotationAwareAspectJAutoProxyCreator会拦截(LogAspects和MathCalculator)组件的创建过程
 * 				3）、是如何拦截的呢? 组件创建完之后，判断组件是否需要增强
 * 					如果是：就把切面中的通知方法 包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象（cglib）；
 * 		5）、执行目标方法：
 * 			1）、就是代理对象执行目标方法
 * 			2）、用CglibAopProxy.intercept()；来进行拦截.
 * 				1）、拦截的过程就是 得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor的数组,就叫做拦截器链）
 * 				2）、利用拦截器的链式机制，依次进入每一个拦截器进行执行；
 * 				3）、效果：
 * 					正常执行：前置通知(前置通知的拦截器是在最后的)-》目标方法-》后置通知-》返回通知
 * 					出现异常：前置通知-》目标方法-》后置通知-》异常通知
 */
@EnableAspectJAutoProxy // 开启基于AspectJ的自动代理,这样就可以使用相关注解了.@Before
@Configuration
public class MainConfigOfAOP {

	// org.springframework.boot.autoconfigure.aop.AopAutoConfiguration

	//业务逻辑类加入容器中
	@Bean
	public MathCalculator calculator(){
		return new MathCalculator();
	}

	//切面类加入到容器中
	@Bean
	public LogAspects logAspects(){
		return new LogAspects();
	}
}

