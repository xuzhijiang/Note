package com.journaldev.java.dependencyinjection.service;

// Service Components

// 服务组件(Service Components)应设计为基类或接口,这些基类或接口定义了服务契约。
// 使用MessageService来声明服务实现的合同。
public interface MessageService {
	/**
	 * 发送消息
	 * @param msg content of message
	 * @param rec receiver of message
	 */
	void sendMessage(String msg, String rec);
}