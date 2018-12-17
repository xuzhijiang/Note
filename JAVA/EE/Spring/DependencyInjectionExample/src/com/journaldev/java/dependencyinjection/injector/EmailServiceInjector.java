package com.journaldev.java.dependencyinjection.injector;

import com.journaldev.java.dependencyinjection.consumer.Consumer;
import com.journaldev.java.dependencyinjection.consumer.MyDIApplication;
import com.journaldev.java.dependencyinjection.service.EmailServiceImpl;
// Java Dependency Injection – Injectors Classes

//  java dependency injector classes 将初始化服务以及消费者类。
public class EmailServiceInjector implements MessageServiceInjector {

	// Now for every service, we will have to create injector classes like below.
	@Override
	public Consumer getConsumer() {
		MyDIApplication app = new MyDIApplication();//initialize the consumer classes
		app.setService(new EmailServiceImpl());// 在注入器中创建服务类,并且设置到Consumer类中.
		return app;
	}

}
