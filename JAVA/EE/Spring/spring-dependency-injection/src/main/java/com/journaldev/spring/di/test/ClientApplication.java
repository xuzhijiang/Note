package com.journaldev.spring.di.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.journaldev.spring.di.configuration.DIConfiguration;
import com.journaldev.spring.di.consumer.MyApplication;

// 测试我们基于注解的Spring Dependency Injection
public class ClientApplication {

	public static void main(String[] args) {

		// AnnotationConfigApplicationContext是AbstractApplicationContext抽象类的实现，
		// it’s used for autowiring the services to components when annotations are
		// used.
		// 它用于在使用注解时 将组件的服务自动装配。(自动连线服务到组件)
		// 在本例中就是将服务(MessageService的实现)自动设置到MyApplication中

		// AnnotationConfigApplicationContext constructor takes Class as argument that
		// will
		// be used to get the bean implementation to inject in component classes.
		// AnnotationConfigApplicationContext构造函数将Class作为参数，
		// 将用于获取bean实现(从传入的配置类中的带有@Bean注解的方法获取)以在
		// 组件类(MyApplication)中注入(给成员变量MessageService设置实例)。
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DIConfiguration.class);

		// getBean(Class) method returns the Component object and
		// uses the configuration for autowiring the objects.
		// getBean（Class）方法返回Component组件对象，并使用该配置自动装配对象。
		MyApplication app = context.getBean(MyApplication.class);

		app.processMessage("Hi xzj", "xzj@abc.com");

		// 上下文对象是资源密集型的，因此我们应该在完成后关闭它们。
		// Context objects are resource intensive, so we should
		// close them when we are done with it.

		// close the context
		context.close();
	}

}
