package com.journaldev.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//代码说明

// 1. 在configureGlobal（）方法中，我们添加了两个用户：一个用户具有“ROLE_USER”角色，
// 另一个用户具有“ROLE_USER”和“ROLE_ADMIN”角色。
// 这意味着第二个用户将充当管理员用户。 像这样我们可以配置任意数量的用户和角色。
// 2. 我们可以使用authorities(ROLE) or roles(ROLE)方法在我们的应用程序中配置角色。

// 3. authority（）和roles（）方法之间的区别：
// 		a. authorities（）需要完整的角色名称，如“ROLE_USER”
//		b. roles（）需要像“USER”这样的角色名称， 它会自动将“ROLE_”值添加到此“USER”角色名称。


//在configure（）方法中，我们已经定义了具有"所需访问角色"的不同URL。
//antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")



//此代码段配置“/homePage”可用于USER和ADMIN角色。

//.antMatchers("/userPage").access("hasRole('ROLE_USER')")
//.antMatchers("/adminPage").access("hasRole('ROLE_ADMIN')")
//此代码段配置“/userPage”只能由“USER”角色访问，而“/adminPage”只能由“ADMIN”角色访问。

//如果其他角色访问这些页面，我们将得到access“403 Access is Denied”错误消息。
@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication()
			.withUser("jduser").password("jdu@123").authorities("ROLE_USER")
			.and()
			.withUser("jdadmin").password("jda@123").authorities("ROLE_USER","ROLE_ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http.authorizeRequests()
			.antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/userPage").access("hasRole('ROLE_USER')")
			.antMatchers("/adminPage").access("hasRole('ROLE_ADMIN')")
			.and()
				.formLogin().loginPage("/loginPage")
				.defaultSuccessUrl("/homePage")
				.failureUrl("/loginPage?error")
				.usernameParameter("username").passwordParameter("password")				
			.and()
				.logout().logoutSuccessUrl("/loginPage?logout"); 
		
	}
}