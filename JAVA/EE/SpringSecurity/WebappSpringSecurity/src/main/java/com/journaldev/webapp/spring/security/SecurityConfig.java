package com.journaldev.webapp.spring.security;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.journaldev.webapp.spring.dao.AppUserDetailsServiceDAO;

// Spring安全示例WebSecurityConfigurer实现
// 我们可以实现WebSecurityConfigurer接口，
// 或者我们可以extend the base实现类WebSecurityConfigurerAdapter并覆盖这些方法。

// 请注意，我们通过覆盖configure（WebSecurity web）方法忽略所有HTML文件。

// 该代码显示了如何插入JDBC身份验证。 
// 我们需要通过提供DataSource来配置它。 由于我们使用自定义表，
// 因此我们还需要提供select queries(选择查询)以获取用户详细信息及其角色。

// 配置in-memory(内存)和基于DAO的身份验证很简单，
// 在下面的代码中进行了注释。 您可以取消注释以使用它们，确保一次只有一个配置。

// @Configuration和@EnableWebSecurity注释是必需的，
// 因此spring框架知道此类将用于spring安全性配置。

// Spring Security Configuration使用Builder Pattern并基于authenticate方法，
// 某些方法在稍后将无法使用。 例如，auth.userDetailsService（）
// 返回UserDetailsService的实例，然后我们就不能有任何其他选项，
// 例如我们不能在它之后设置DataSource。

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		// in-memory authentication
		// auth.inMemoryAuthentication().withUser("pankaj").password("pankaj123").roles("USER");

		// using custom UserDetailsService DAO
		// auth.userDetailsService(new AppUserDetailsServiceDAO());

		// using JDBC
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx
				.lookup("java:/comp/env/jdbc/MyLocalDB");

		final String findUserQuery = "select username,password,enabled "
				+ "from Employees " + "where username = ?";
		final String findRoles = "select username,role " + "from Roles "
				+ "where username = ?";
		
		auth.jdbcAuthentication().dataSource(ds)
				.usersByUsernameQuery(findUserQuery)
				.authoritiesByUsernameQuery(findRoles);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/*.html");
    }

}
