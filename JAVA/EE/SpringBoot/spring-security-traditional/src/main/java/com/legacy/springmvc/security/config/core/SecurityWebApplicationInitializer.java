package com.legacy.springmvc.security.config.core;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// 1. 实现AbstractSecurityWebApplicationInitializer抽象类来注册bean名字为springSecurityFilterChain的过滤器(Filter)
// 2.  It avoids writing Filters configuration in web.xml file.
// 3. 注意,本例中MvcWebApplicationInitializer只是把几个配置类能让Ioc容器找到,
// 并且能够管理,并没有注册filter,所以注册filter的工作要让SecurityWebApplicationInitializer完成.
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}