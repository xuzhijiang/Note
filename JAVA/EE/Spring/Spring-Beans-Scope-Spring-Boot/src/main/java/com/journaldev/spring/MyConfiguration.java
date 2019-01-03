package com.journaldev.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

// 让我们定义spring配置类，我们将定义从Spring容器中获取MyBean实例的方法。

// 请注意，singleton是默认范围，因此我们可以从下面的bean定义中删除@Scope（value =“singleton”）。

@Configuration
public class MyConfiguration {
	
	// 请注意，两个MyBean实例都具有相同的哈希码，
	// 并且构造函数只调用一次，这意味着spring容器始终返回相同的MyBean实例。
	@Bean
	@Scope(value="singleton")
    public MyBean myBean() {
		return new MyBean();
	}
	
// 很明显，MyBean实例是每次从spring容器请求时创建的。
//	@Bean
//	@Scope(value="prototype")
//	public MyBean myBean() {
//		return new MyBean();
//	}
	

// 在这种情况下，我们将得到以下异常。
// 这是因为request, session and global-session scopes不适用于独立应用程序。
//	@Bean
//	@Scope(value="request")
//	public MyBean myBean() {
//		return new MyBean();
//	}


}
