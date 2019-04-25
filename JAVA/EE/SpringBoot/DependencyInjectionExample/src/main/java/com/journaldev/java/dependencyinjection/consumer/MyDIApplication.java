package com.journaldev.java.dependencyinjection.consumer;

import com.journaldev.java.dependencyinjection.service.MessageService;

public class MyDIApplication implements Consumer {

	// 我们的应用程序类(也就是消费类)只是使用该MessageService服务。
	// 没有初始化MessageService,导致更好的关注点分离(separation of concerns)
	private MessageService service;

	// 我们使用构造函数在应用程序类中注入依赖项(MessageService)
	public MyDIApplication(MessageService svc) {
		this.service = svc;
	}

	public MyDIApplication() {}

	// setter dependency injection
	public void setService(MessageService service) {
		this.service = service;
	}

	/**
	 * 其他类将message传入消费者类，消费类将使用注入的依赖项(MessageService)，
	 * 来消费此消息
	 * @param msg
	 * @param rec
	 */
	public void processMessages(String msg, String rec) {
		// do some msg validation, manipulation logic etc
		this.service.sendMessage(msg, rec);
	}
}