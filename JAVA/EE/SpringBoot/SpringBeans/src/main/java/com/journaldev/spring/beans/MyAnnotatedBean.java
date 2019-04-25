package com.journaldev.spring.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

// 1. 基于注解的Spring Bean配置
// 2. MyAnnotatedBean使用@Service注解配置，范围设置为Request。
// 3. 对于每个请求，使用不同的哈希码创建MyAnnotatedBean的新实例。
// 4. 1. Annotation Based Configuration (基于注释的配置 )- 使用@Service或@Component annotations。
// 可以使用@Scope annotations提供范围详细信息。
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
