package com.journaldev.spring.di.consumer;

import com.journaldev.spring.di.services.MessageService;

// 一个consuming 服务的简单应用程序类。 对于基于XML的配置，
// 我们可以使用基于构造函数的spring依赖注入或基于方法的spring依赖注入。 
// 请注意(Note that)，基于方法和基于setter的注入方法是相同的，
// 只是有些人更喜欢称之为基于setter，有些人称之为基于方法。

// without annotation and wiring configuration will be provided in the XML configuration file.
// 没有注解和接线配置的注释将在XML配置文件中提供。
public class MyXMLApplication {

	//没有初始化，spring会自动将配置的属性值赋给这个变量
	private MessageService service;

	//constructor-based dependency injection
//	public MyXMLApplication(MessageService svc) {
//		this.service = svc;
//	}
	
	//setter-based dependency injection
	public void setService(MessageService svc){
		this.service=svc;
	}

	public boolean processMessage(String msg, String rec) {
		// some magic like validation, logging etc
		return this.service.sendMessage(msg, rec);
	}
}
