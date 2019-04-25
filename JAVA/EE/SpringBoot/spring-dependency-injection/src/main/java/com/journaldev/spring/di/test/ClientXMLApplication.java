package com.journaldev.spring.di.test;

import com.journaldev.spring.di.services.MessageService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.di.consumer.MyXMLApplication;

public class ClientXMLApplication {

	public static void main(String[] args) {
		// 通过提供配置文件位置来获取ApplicationContext对象。
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		// 选择获取ApplicationContext对象的方式。
		MyXMLApplication app = context.getBean(MyXMLApplication.class);
		app.processMessage("Hi xzj", "xzj@abc.com");

		// 通过工厂根据配置的实例名称(对应xml中的id)获取实例对象
		MessageService service = (MessageService) context.getBean("twitter");
		service.sendMessage("Hi xzj", "222@qq.com");

		// close the context
		context.close();
	}

}

//由于Spring Framework使用log4j进行日志记录，并且我没有对其进行配置，因此输出将被写入控制台。