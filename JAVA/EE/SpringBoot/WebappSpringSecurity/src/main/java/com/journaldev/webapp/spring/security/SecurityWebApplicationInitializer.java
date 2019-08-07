package com.journaldev.webapp.spring.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// 请注意，这仅适用于Servlet-3 servlet containers.
// 。 因此，如果您使用的是Apache Tomcat，请确保它的版本为7.0或更高版本。

// 如果要使用不支持Servlet规范3的Servlet容器，
// 则需要通过部署描述符注册DispatcherServlet。 有关更多详细信息，
// 请参阅WebApplicationInitializer的JavaDoc。

// Accessing HTML Page without Security
// localhost:9090/WebappSpringSecurity/health.html

// Authentication Failed for Bad Credentials
// localhost:9090/WebappSpringSecurity/login

// Home Page with Spring Security JDBC Authentication
// localhost:9090/WebappSpringSecurity/

// Home Page with Spring Security UserDetailsService DAO Authentication

// Home Page with Spring Security In-Memory Authentication

// Logout Page

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}
