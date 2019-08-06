package com.legacy.springmvc.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// 注册过滤器: 也就是注册springSecurityFilterChain

// AbstractSecurityWebApplicationInitializer来确保 springSecurityFilterChain被注册.

// “SecurityWebApplicationInitializer”用于注册DelegatingFilterProxy以使用
// springSecurityFilterChain。 它避免在web.xml文件中编写 Filters configuration
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}