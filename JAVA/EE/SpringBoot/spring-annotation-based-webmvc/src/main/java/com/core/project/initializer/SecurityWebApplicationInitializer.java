package com.core.project.initializer;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// 实现AbstractSecurityWebApplicationInitializer是必须的,要不然security就不会起作用!!

// 实现AbstractSecurityWebApplicationInitializer抽象类的作用:
// 注册名字为springSecurityFilterChain的过滤器(Filter)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}