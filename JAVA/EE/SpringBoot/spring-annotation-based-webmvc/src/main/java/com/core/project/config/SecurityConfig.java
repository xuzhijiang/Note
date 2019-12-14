package com.core.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(101)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("a").password("aaa").authorities("ROLE_USER");
		authenticationMgr.inMemoryAuthentication().withUser("b").password("bbb").authorities("ROLE_USER","ROLE_ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				// /homePage可由USER和ADMIN访问
			.antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				// “/userPage”只能由“USER”角色访问，而“/adminPage”只能由“ADMIN”角色访问
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