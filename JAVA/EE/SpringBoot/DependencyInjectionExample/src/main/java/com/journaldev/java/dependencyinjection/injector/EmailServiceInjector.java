package com.journaldev.java.dependencyinjection.injector;

import com.journaldev.java.dependencyinjection.consumer.Consumer;
import com.journaldev.java.dependencyinjection.consumer.MyDIApplication;
import com.journaldev.java.dependencyinjection.service.EmailServiceImpl;
// Injectors Classes
public class EmailServiceInjector implements MessageServiceInjector {

	@Override
	public Consumer getConsumer() {
		// 注入器类初始化消费者类
		MyDIApplication app = new MyDIApplication();
		// 注入器类创建服务类,并且将服务类注入到消费者类中
		app.setService(new EmailServiceImpl());
		// 返回消费类
		return app;
	}

}
