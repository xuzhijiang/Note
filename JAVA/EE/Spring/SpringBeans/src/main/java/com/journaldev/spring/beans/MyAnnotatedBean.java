package com.journaldev.spring.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

// Annotation Based Spring Bean Configuration

// MyAnnotatedBean使用@Service配置，范围设置为Request。

//Notice thatMyBean配置为单例，因此容器始终返回相同的实例，并且哈希码始终相同。
//类似地，对于每个请求，使用不同的哈希码创建MyAnnotatedBean的新实例。
@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class MyAnnotatedBean {

	private int empId;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
}
