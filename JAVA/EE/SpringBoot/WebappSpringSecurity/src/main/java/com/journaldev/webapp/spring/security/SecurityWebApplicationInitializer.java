package com.journaldev.webapp.spring.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// 这可以通过extend AbstractSecurityWebApplicationInitializer类
// 并在超类构造函数中传递Security配置类来轻松完成。

// 当我们的上下文启动时，它使用ServletContext添加ContextLoaderListener
// 监听器并将我们的配置类注册为Servlet Filter。

// 请注意，这仅适用于Servlet-3 complaint servlet containers.
// 。 因此，如果您使用的是Apache Tomcat，请确保它的版本为7.0或更高版本。

// 我们的项目准备就绪，只需将它部署在您最喜欢的servlet容器中。
 // 我正在使用Apache Tomcat-7来运行此应用程序。

// 下图显示了各种情况下的响应。

// Accessing HTML Page without Security
// localhost:9090/WebappSpringSecurity/health.html

// Authentication Failed for Bad Credentials
// localhost:9090/WebappSpringSecurity/login

// Home Page with Spring Security JDBC Authentication
// localhost:9090/WebappSpringSecurity/

// Home Page with Spring Security UserDetailsService DAO Authentication

// Home Page with Spring Security In-Memory Authentication

// Logout Page

// 如果要使用不支持Servlet规范3的Servlet容器，
// 则需要通过部署描述符注册DispatcherServlet。 有关更多详细信息，
// 请参阅WebApplicationInitializer的JavaDoc。

public class SecurityWebApplicationInitializer extends
		AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
        super(com.journaldev.webapp.spring.security.SecurityConfig.class);
    }
}
