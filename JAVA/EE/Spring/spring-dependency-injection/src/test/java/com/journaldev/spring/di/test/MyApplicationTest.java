package com.journaldev.spring.di.test;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.journaldev.spring.di.consumer.MyApplication;
import com.journaldev.spring.di.services.MessageService;

// Spring依赖注入的最大好处之一是易于使用模拟服务类而不是使用实际服务。
// mock service classes rather than using actual services

// 该类使用@Configuration和@ComponentScan annotation进行注解，]
// 因为getMessageService(）方法返回MessageService模拟实现。
// 这就是使用@Bean注释注释getMessageService(）的原因。

// 由于我正在测试使用注解配置的MyApplication类，因此我使用
//AnnotationConfigApplicationContext并在setUp(）方法中创建它的对象。
//在tearDown(）方法中关闭上下文。 test(）方法代码只是从上下文获取组件对象并对其进行测试。
// test() method code is just getting the component object from context and testing it.

@Configuration
@ComponentScan(value="com.journaldev.spring.di.consumer")
public class MyApplicationTest {
	
	private AnnotationConfigApplicationContext context = null;

	@Bean
	public MessageService getMessageService() {
		return new MessageService(){

			public boolean sendMessage(String msg, String rec) {
				System.out.println("Mock Service");
				return true;
			}
			
		};
	}

	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext(MyApplicationTest.class);
	}
	
	@After
	public void tearDown() throws Exception {
		context.close();
	}

	@Test
	public void test() {
		MyApplication app = context.getBean(MyApplication.class);
		Assert.assertTrue(app.processMessage("Hi Pankaj", "pankaj@abc.com"));
	}

}
