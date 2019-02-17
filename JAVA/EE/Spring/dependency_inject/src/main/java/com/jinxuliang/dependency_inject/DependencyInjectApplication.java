package com.jinxuliang.dependency_inject;

import com.javax.other.bean.OtherBean;
import com.jinxuliang.dependency_inject.bean.*;
import com.jinxuliang.dependency_inject.controller.BeanServiceController;
import com.jinxuliang.dependency_inject.other.MyOtherClass;
import com.jinxuliang.dependency_inject.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

// @SpringBootApplication会通知Spring，启动一个组件扫描(Component Scan），查找所有使用相
// 关注解的类(比如@Component、 @Configuration等），并将它们注册为可实例化的Bean。
@SpringBootApplication
public class DependencyInjectApplication {

	public static void main(String[] args) {
	    //获取Spring的IoC容器
		ApplicationContext context = SpringApplication.run(DependencyInjectApplication.class, args);

		//外界通过IoC容器按类型获取Bean的实例
		MyAnnotationBean myAnnotationBean = context.getBean(MyAnnotationBean.class);
		System.out.println(myAnnotationBean);

		POJOBean pojoBean=context.getBean(POJOBean.class);
		System.out.println(pojoBean);

        MyContainerBean containerBean=context.getBean(MyContainerBean.class);
        System.out.println(containerBean);

        CustomerService customerService=context.getBean(CustomerService.class);
        System.out.println(customerService);

		// 外界通过IoC容器按名称获取Bean的实例
        POJOBeanContainer beanContainer= (POJOBeanContainer) context.getBean("beanContainer");
        System.out.println(beanContainer);

		System.out.println("扫描获取除了程序入口点所在的包及下属子包中的Bean组件");
		OtherBean otherBean = context.getBean(OtherBean.class);
		System.out.println(otherBean);

		//由于ContextAwareBean实现了ApplicationContextAware接口，所以，
		//IoC容器在实例化ContextAwareBean时，会自动地将ApplicationContext注入进去
		ContextAwareBean contextAwareBean=context.getBean(ContextAwareBean.class);
		//检测Bean内部的ApplicationContext与外部的ApplicationContext本质上是一回事
		System.out.println(contextAwareBean.getContext()==context);

		//按名字实例化Bean
		BeanServiceController controller=context.getBean(BeanServiceController.class);
		System.out.println(controller);

		System.out.println("测试@Value的用法");
		AtValueBean atValueBean=context.getBean(AtValueBean.class);
		System.out.println(atValueBean);

		UseOptionalBean useOptionalBean=context.getBean(UseOptionalBean.class);
		System.out.println(useOptionalBean);
	}
}
