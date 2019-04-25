package com.journaldev.spring;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

// 让我们创建一些spring组件并将它们配置为spring容器中的spring bean，
// 其范围为request和session。
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DataRequestScope {

	private String name = "Request Scope";
	
	public DataRequestScope() {
		System.out.println("DataRequestScope Constructor Called");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
