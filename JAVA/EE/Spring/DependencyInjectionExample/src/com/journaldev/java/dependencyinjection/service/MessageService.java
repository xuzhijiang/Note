package com.journaldev.java.dependencyinjection.service;

// Java Dependency Injection – Service Components
// 服务组件应设计为基类或接口。 最好选择定义服务契约的接口或抽象类: 
// 使用MessageService来声明服务实现的合同。
public interface MessageService {
	void sendMessage(String msg, String rec);
}
