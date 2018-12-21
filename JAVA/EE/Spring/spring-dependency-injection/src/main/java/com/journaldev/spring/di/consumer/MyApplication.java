package com.journaldev.spring.di.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.journaldev.spring.di.services.MessageService;

// Component classes that will consume MessageService.
// 将会消费MessageService服务的组件

// 带有自动装配(自动连线)功能的Spring注释的MessageService的Consumer类
// a consumer class for MessageService with Spring annotations for autowiring

// @Component annotation被添加到这个类,因此当Spring framework将扫描components时，
// this class will be treated as component. @Component annotation只能应用于类，
// 它的保留策略是Runtime。
@Component
public class MyApplication {

	// 基于字段的自动装配(基于字段的依赖注入)
	//field-based dependency injection
	//@Autowired
	private MessageService service;
	
	/*For our example, I am using method-based dependency injection. 
	You can uncomment the constructor method to 
	switch to constructor based dependency injection.
	对于我们的示例，我使用基于方法的依赖注入。 您可以取消注释构造函数方法以切换到基于构造函数的依赖项注入。*/
	
	//constructor-based dependency injection	
	/*@Autowired
	public MyApplication(MessageService svc){
		this.service=svc;
	}*/
	
	// @Autowired annotation is used to let Spring know that
	// autowiring is required. This can be applied to field, 
	// constructor and methods. This annotation allows us to 
	// implement constructor-based, field-based or method-based 
	// dependency injection in our components.
	// @Autowired注释用于让Spring知道需要自动装配。 这可以应用于字段，构造函数和方法。
	// 此注释允许我们在组件中实现基于构造函数，基于字段或基于方法的依赖项注入。
	
	@Autowired
	public void setService(MessageService svc){
		this.service=svc;
	}
	
	public boolean processMessage(String msg, String rec){
		//some magic like validation, logging etc
		return this.service.sendMessage(msg, rec);
	}
	
}
