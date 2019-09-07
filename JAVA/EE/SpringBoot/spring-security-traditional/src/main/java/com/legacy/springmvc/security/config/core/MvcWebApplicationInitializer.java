package com.legacy.springmvc.security.config.core;

import com.legacy.springmvc.security.config.LoginApplicationConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// “SpringMVCWebAppInitializer” class is used to initialize “DispatcherServlet” without web.xml file in a Annotation based configuration.
// 1. 此类相当于使用java代码的方式初始化“DispatcherServlet”,不用在web.xml中初始化.
// 2. 顺便在初始化DispatcherServlet的时候,把getRootConfigClasses()返回的类注册到Ioc容器中.
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//Specify @Configuration and/or @Component classes to be provided to the root application context.
	// 1. 此方法也就是能让Spring容器找到LoginApplicationConfig和SecurityConfig,以及LoginSecurityConfig中定义的相关的bean,并且管理这些bean.
	// 2. 可以找到"通过LoginSecurityConfig/SecurityConfig类上的注解@EnableWebSecurity创建的过滤器" bean,
	// 因为这个bean已经被IoC容器管理.
	// 3. LoginSecurityConfig.class是通过LoginApplicationConfig.class内部的@Import注册到IoC的.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { LoginApplicationConfig.class};
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