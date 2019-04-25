package com.journaldev.java.dependencyinjection.consumer;

// Java Dependency Injection – Service Consumer(服务的消费类)

// 消费者类应该根据服务组件(MessageService)定义的服务契约接口或基类来编写。
// (即Consumer接口应该根据MessageService接口来编写,他们的参数应该相同)
public interface Consumer {
	void processMessages(String msg, String rec);
}
