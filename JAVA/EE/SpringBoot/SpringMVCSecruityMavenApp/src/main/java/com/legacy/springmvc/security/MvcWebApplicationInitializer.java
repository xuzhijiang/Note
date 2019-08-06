package com.legacy.springmvc.security;

import com.legacy.springmvc.security.config.LoginApplicationConfig;
import com.legacy.springmvc.security.config.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//“MvcWebApplicationInitializer”类用于在基于注释的
//配置中初始化“DispatcherServlet”而不使用web.xml,不用在web.xml中初始化.
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { LoginApplicationConfig.class, SecurityConfig.class };
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