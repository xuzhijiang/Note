package com.legacy.springmvc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @EnableWebSecurity = @EnableWebMVCSecurity + Extra features(额外功能)
//这就是Spring 4.x Framework中不推荐使用@EnableWebMVCSecurity Annotation的原因。
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	//在configureGlobal(）方法中，我们可以使用authorities(）方法来
	// 定义我们的应用程序角色，如“ROLE_USER”。我们也可以将roles(）方法用于相同的目的。
	//authority(）和roles(）方法之间的区别：
	//1. authorities(）需要完整的角色名称，如“ROLE_USER”
//2. roles(）需要角色名称，如“USER”。它会自动将“ROLE_”值添加到此“USER”角色名称。
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("journaldev")
				.password("jd@123").authorities("ROLE_USER");
	}


	// 1. 以下代码用于避免未经授权访问“/homePage”。
	// 如果您尝试直接访问此页面，我们将自动重定向到“/loginPage”页面。
	// 2. .antMatchers("/homePage").access("hasRole('ROLE_USER')")
	// 如果我们删除 access(“hasRole(‘ROLE_USER’)” )方法调用，
	// 那么我们可以访问此页面而无需登录我们的应用程序。
	//3. 我们使用formLogin(）和logout(）方法配置了登录和注销功能。
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/homePage").access("hasRole('ROLE_USER')")
			.and()
				.formLogin().loginPage("/loginPage")
				.defaultSuccessUrl("/homePage")
				.failureUrl("/loginPage?error")
				.usernameParameter("username").passwordParameter("password")				
			.and()
				.logout().logoutSuccessUrl("/loginPage?logout"); 
		
	}
}