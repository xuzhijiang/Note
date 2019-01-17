package com.journaldev.spring.security.config.core;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.journaldev.spring.security.config.LoginApplicationConfig;

//初始化Spring MVC应用程序
//“SpringMVCWebAppInitializer”类用于在基于注释的
//配置中初始化“DispatcherServlet”而不使用web.xml文件。

// 注意：-

// 当我们访问我们的应用程序时，默认情况下SpringMVCWebAppInitializer
//的getServletMappings（）将允许访问根URL：“/”。 我们可以覆盖转发到不同的URL。

// Spring或Pivotal团队正在通过引入注释来解决这个问题以避免这么多Java代码。 
// 请访问https://jira.spring.io/browse/SPR-10359查看此信息。
public class SpringMVCWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { LoginApplicationConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
}