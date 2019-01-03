package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 现在让我们创建一个spring组件并使用spring来自动配置上面的bean。

@Component
public class Customer {

	@Autowired
	private DataRequestScope dataRequestScope;
	
	@Autowired
	private DataSessionScope dataSessionScope;

	public DataRequestScope getDataRequestScope() {
		return dataRequestScope;
	}

	public void setDataRequestScope(DataRequestScope dataRequestScope) {
		this.dataRequestScope = dataRequestScope;
	}

	public DataSessionScope getDataSessionScope() {
		return dataSessionScope;
	}

	public void setDataSessionScope(DataSessionScope dataSessionScope) {
		this.dataSessionScope = dataSessionScope;
	}


}
