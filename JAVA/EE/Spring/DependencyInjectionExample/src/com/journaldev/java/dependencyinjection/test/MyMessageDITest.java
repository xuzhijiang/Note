package com.journaldev.java.dependencyinjection.test;

import com.journaldev.java.dependencyinjection.consumer.Consumer;
import com.journaldev.java.dependencyinjection.injector.EmailServiceInjector;
import com.journaldev.java.dependencyinjection.injector.MessageServiceInjector;
import com.journaldev.java.dependencyinjection.injector.SMSServiceInjector;

// 我们的应用程序类仅负责使用该服务MessageService。
public class MyMessageDITest {

	public static void main(String[] args) {
		String msg = "Hi xzj";
		String email = "xzj@abc.com";
		String phone = "4088888888";
		
		MessageServiceInjector injector = null;
		Consumer app = null;
		
		//Send email
		injector = new EmailServiceInjector();// 创建注入器
		app = injector.getConsumer();// 在注入器中创建服务类 ,并且将服务注入Consumer，然后返回Consumer
		app.processMessages(msg, email);// Consumer开始消费消息.
		
		// 如果我们必须扩展我们的应用程序以允许SMS消息传递，我们将只需编写Service类和注入器类。
		//Send SMS
		injector = new SMSServiceInjector();
		app = injector.getConsumer();
		app.processMessages(msg, phone);
	}

}
