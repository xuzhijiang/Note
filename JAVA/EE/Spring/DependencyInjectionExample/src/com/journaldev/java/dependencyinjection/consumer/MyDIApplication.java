package com.journaldev.java.dependencyinjection.consumer;

import com.journaldev.java.dependencyinjection.service.MessageService;

// 消费者类应该根据服务接口来编写。

// 使用service interface还允许我们通过模拟mock MessageService轻松测试应用程序，
// 并在运行时而不是编译时绑定服务。

// Java中的依赖注入是一种通过将对象绑定 从编译时 移到 运行时 来实现应用程序控制反转（IoC,Inversion of control ）的方法
// 。我们也可以通过Factory Pattern， 
// 模板方法设计模式( Template Method Design Pattern)，
// 策略模式Strategy Pattern和服务定位模式 Service Locator pattern来实现IoC。

// Spring依赖注入，Google Guice和Java EE CDI框架通过使用Java Reflection API和Java annotation
// 来促进依赖注入的过程。我们所需要的只是annotation字段、构造函数、setter方法，并在配置xml文件或类中配置它们。
public class MyDIApplication implements Consumer {

	// 我们的应用程序类只是使用该MessageService服务。
	// 没有初始化MessageService,导致更好的“separation of concerns“
	private MessageService service;

	// 是否使用基于 Constructor的依赖注入或基于setter method的依赖注入取决于您的要求。
//	例如，如果我的应用程序在没有服务类的情况下根本无法工作，那么我会更喜欢基于构造函数的DI(依赖注入)，
//	否则我会选择基于setter方法的DI，只有在真正需要时才使用它。

	// 我们使用构造函数在应用程序类中注入依赖项(MessageService)
	public MyDIApplication(MessageService svc) {
		this.service = svc;
	}

	public MyDIApplication() {
	}

	// setter dependency injection
	public void setService(MessageService service) {
		this.service = service;
	}
	// setter dependency injection依赖注入的最好例子之一是Struts2 Servlet API Aware interfaces。

	@Override
	public void processMessages(String msg, String rec) {
		// do some msg validation, manipulation logic etc
		this.service.sendMessage(msg, rec);
	}

}
