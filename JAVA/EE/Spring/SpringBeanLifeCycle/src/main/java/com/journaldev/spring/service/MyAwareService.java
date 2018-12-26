package com.journaldev.spring.service;

import java.util.Arrays;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/*Spring Aware Interfaces

有时我们需要在bean中使用Spring Framework对象来执行某些操作，
例如读取ServletConfig和ServletContext参数或者知道ApplicationContext加载的bean定义。
这就是为什么spring框架提供了一堆我们可以在我们的bean类中实现的*Aware接口。

Spring Aware接口类似于具有回调方法和实现观察者设计模式的servlet监听器。

一些重要的Aware接口在如下实例中使用：

//5. ServletContextAware  - 在MVC应用程序中注入ServletContext对象，示例用法是读取上下文参数和属性。
//6. ServletConfigAware  - 在MVC应用程序中注入ServletConfig对象，示例用法是获取servlet配置参数。

*/

// 在一个我们将配置为spring bean的类中实现其中的一些Aware接口

public class MyAwareService implements ApplicationContextAware,
		ApplicationEventPublisherAware, BeanClassLoaderAware, BeanFactoryAware,
		BeanNameAware, EnvironmentAware, ImportAware, ResourceLoaderAware {

	/*org.springframework.beans.factory.Aware是所有这些Aware接口的根标记接口。
	所有*Aware接口都是Aware的子接口，这些*Aware声明了需要由bean(MyAwareService)实现的单个setter方法。
	例如ApplicationEventPublisherAware声明了setApplicationEventPublisher方法.
	需要由bean(MyAwareService)实现。
	然后spring context使用基于setter的依赖注入来在bean中注入相应的对象并使其可供我们使用
	(make it available for our use.)。*/
	
	// 1. ApplicationContextAware  - 注入ApplicationContext对象，示例用法是获取bean定义名称的数组。
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		System.out.println("setApplicationContext called");
		System.out.println("setApplicationContext:: Bean Definition Names="
				+ Arrays.toString(ctx.getBeanDefinitionNames()));
	}


//3. BeanNameAware  - 知道配置文件中定义的bean名称。
	@Override
	public void setBeanName(String beanName) {
		System.out.println("setBeanName called");
		System.out.println("setBeanName:: Bean Name defined in context="
				+ beanName);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("setBeanClassLoader called");
		System.out.println("setBeanClassLoader:: ClassLoader Name="
				+ classLoader.getClass().getName());
	}

	//4. ResourceLoaderAware  - 要注入ResourceLoader对象，示例用法是获取classpath中文件的输入流。
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		System.out.println("setResourceLoader called");
		Resource resource = resourceLoader.getResource("classpath:spring.xml");
		System.out.println("setResourceLoader:: Resource File Name="
				+ resource.getFilename());
	}

	@Override
	public void setImportMetadata(AnnotationMetadata annotationMetadata) {
		System.out.println("setImportMetadata called");
	}

	@Override
	public void setEnvironment(Environment env) {
		System.out.println("setEnvironment called");
	}

	// 2. BeanFactoryAware  - 注入BeanFactory对象，示例用法是检查bean的范围。
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("setBeanFactory called");
		System.out.println("setBeanFactory:: employee bean singleton="
				+ beanFactory.isSingleton("employee"));
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		System.out.println("setApplicationEventPublisher called");
	}

}
