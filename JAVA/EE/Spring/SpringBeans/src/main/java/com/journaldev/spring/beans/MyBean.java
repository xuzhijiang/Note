package com.journaldev.spring.beans;

// XML Based Spring Bean Configuration
// MyBean is a simple Java POJO class.

// Notice thatMyBean配置为单例，因此容器始终返回相同的实例，并且哈希码始终相同。
// 类似地，对于每个请求，使用不同的哈希码创建MyAnnotatedBean的新实例。
public class MyBean {

	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
