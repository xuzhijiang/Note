package com.journaldev.spring;

//Spring Bean Singleton和Prototype Scope

//Spring bean单例和原型scopes可以在独立的spring应用程序中使用。

//让我们看看如何使用@Scope注释 轻松配置这些scopes范围。

public class MyBean {

	public MyBean() {
		System.out.println("MyBean instance created");
	}

}
