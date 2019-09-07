package com.legacy.springmvc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @EnableWebSecurity = @EnableWebMVCSecurity + Extra features(额外功能)
//这就是Spring 4.x Framework中不推荐使用@EnableWebMVCSecurity Annotation的原因。
@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	//在configureGlobal(）方法中，我们可以使用authorities(）方法来
	// 定义我们的应用程序角色，如“ROLE_USER”。我们也可以使用roles()方法用于相同的目的。
	// authorities(）和roles(）方法之间的区别：
	//1. authorities(）需要完整的角色名称，如“ROLE_USER”
	//2. roles(）需要角色名称，如“USER”。它会自动将“ROLE_”值添加到此“USER”角色名称。
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		// 我们添加了两个用户：一个用户具有“ROLE_USER”角色，
		// 另一个用户具有“ROLE_USER”和“ROLE_ADMIN”角色。这意味着第二个用户将充当管理员用户.
		authenticationMgr.inMemoryAuthentication().withUser("jduser").password("jdu@123").authorities("ROLE_USER").and().withUser("jdadmin").password("jda@123").authorities("ROLE_USER","ROLE_ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// “/homePage”可用于USER和ADMIN角色
				// 如果不符合角色要求,直接访问/homePage，我们将自动重定向到“/loginPage”页面
			.antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				// “/userPage”只能由“USER”角色访问，而“/adminPage”只能由“ADMIN”角色访问
				// 如果其他角色访问这些页面，我们将得到access“403 Access is Denied”错误消息。
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

	// 通过覆盖configure(WebSecurity web）方法忽略所有HTML文件。
	/*
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				// Spring Security should completely ignore URLs starting with /resources/
				// Spring Security应该完全忽略以/ resources /开头的URL
				.antMatchers("/*.html");
	}
	*/
}