package com.atguigu.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.atguigu.bean.Blue;
import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import com.atguigu.config.MainConfig2;

public class B_IOCTest {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

	@Test
	public void testFactoryBean(){
		//工厂Bean获取的是调用getObject创建的对象
		Object bean2 = applicationContext.getBean("colorFactoryBean");
		Object bean3 = applicationContext.getBean("colorFactoryBean");
		// 获取的是调用getObject创建的对象的类型,而不是工厂bean的类型
		System.out.println("bean的类型："+bean2.getClass());
		System.out.println(bean2 == bean3);

		// 我们就要获得ColorFactoryBean,而不是ColorFactoryBean的getObject()返回的对象怎么做呢?
		// 加一个前缀: &, 这个& 其实在 org.springframework.beans.factory.BeanFactory 中有定义,可以看一下源码.
		Object bean4 = applicationContext.getBean("&colorFactoryBean");
		System.out.println("FactoryBean的类型："+bean4.getClass());
	}

	@Test
	public void testImport(){
		Blue bean = applicationContext.getBean(Blue.class);
		System.out.println(bean);
	}

	@Test
	public void test03(){
		// 获取Person类的所有bean实例的名字
		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			System.out.println("getBeanNamesForType===========>" + name);
		}

		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		//动态获取环境变量的值；Windows 10
		String property = environment.getProperty("os.name");
		System.out.println("property========>" + property);

		// 获取所有Person的bean的实例
		Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
		// 打印Person的所有实例
		System.out.println(persons);
	}

	// 测试默认是单例的bean
	@Test
	public void test02(){
		Person bean = (Person) applicationContext.getBean("person");
		Person bean2 = (Person) applicationContext.getBean("person");
		System.out.println(bean == bean2); // true
		System.out.println("bean=====>" + bean);
		System.out.println("bean2=====>" + bean2);
	}

	// 打印bean定义
	@Test
	public void printBeans(){
		// 获取容器中的所有bean定义
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}
}
