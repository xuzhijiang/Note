package com.journaldev.java.dependencyinjection.injector;

import com.journaldev.java.dependencyinjection.consumer.Consumer;

// 接口MessageServiceInjector，它带有返回Consumer类的方法声明。

// 依赖注入实现解决了硬编码依赖的问题，并帮助我们使应用程序灵活且易于扩展。
public interface MessageServiceInjector {
	public Consumer getConsumer();
}
