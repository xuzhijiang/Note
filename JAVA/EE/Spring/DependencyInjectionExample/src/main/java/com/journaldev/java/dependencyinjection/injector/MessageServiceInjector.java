package com.journaldev.java.dependencyinjection.injector;

import com.journaldev.java.dependencyinjection.consumer.Consumer;

public interface MessageServiceInjector {

	/**
	 * 接口MessageServiceInjector，它带有返回Consumer类的方法声明
	 * @return
	 */
	Consumer getConsumer();
}
