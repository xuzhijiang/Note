package com.journaldev.spring.security.config.core;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// Initialize Spring Security

// “SpringSecurityInitializer”用于注册DelegatingFilterProxy以使用
// springSecurityFilterChain。 它避免在web.xml文件中编写 Filters configuration
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
}