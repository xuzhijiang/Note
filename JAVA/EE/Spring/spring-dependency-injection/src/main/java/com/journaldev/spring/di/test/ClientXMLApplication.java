package com.journaldev.spring.di.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.di.consumer.MyXMLApplication;

// 
public class ClientXMLApplication {

	public static void main(String[] args) {
		// ClassPathXmlApplicationContext用于通过提供配置文件位置来获取ApplicationContext对象。 
		// 它有多个重载的构造函数，我们也可以提供多个配置文件。
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		// 其余代码类似于基于annotation的配置测试程序，唯一的区别是我们根据配置
		// 选择获取ApplicationContext对象的方式。
		MyXMLApplication app = context.getBean(MyXMLApplication.class);

		app.processMessage("Hi xzj", "xzj@abc.com");

		// close the context
		context.close();
	}

}

//请注意，一些输出是由Spring Framework编写的。 由于Spring Framework使用
// log4j进行日志记录，并且我没有对其进行配置，因此输出将被写入控制台。