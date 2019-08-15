package com.itranswarp.springcloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 根据配置，Spring Security提供了一个过滤器来拦截请求并验证用户身份。
 * 如果用户身份认证失败，页面就重定向到/login?error，并且页面中会展现相应的错误信息。
 * 若用户想要注销登录，可以通过访问/login?logout请求，在完成注销之后，页面展现相应的成功消息。
 */
@Configuration
@EnableWebSecurity//通过@EnableWebSecurity注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定义哪些URL需要被保护、哪些不需要被保护。
        // 以下指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证
        // 通过formLogin()定义当需要用户登录时候，转到的登录页面。
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/hello")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * 在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER。
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user").password(new BCryptPasswordEncoder().encode("password")).roles("USER");
    }

}