package com.journaldev.spring.beans;

// 1. 基于XML(servlet-context.xml)的Spring Bean配置

// 2. 注意：MyBean在servlet-context.xml被配置为单例，
// 因此container始终返回相同的实例，并且哈希码始终相同。

// 2. XML Based Configuration基于XML的配置 - 通过创建Spring配置XML文件来配置bean。
//如果您使用的是Spring MVC框架，则可以通过在web.xml文件中编写一些样板代码来自动加载基于xml的配置。
public class MyBean {

	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
