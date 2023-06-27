package com.spring.session.redis.core.config;

import com.spring.session.redis.core.common.security.handler.CustomAccessDeniedHandler;
import com.spring.session.redis.core.common.security.handler.CustomAuthenticationFailureHandler;
import com.spring.session.redis.core.common.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import javax.security.auth.login.AccountExpiredException;
import java.util.HashMap;

@EnableWebSecurity
// 添加注解@EnableGlobalMethodSecurity，并设置prePostEnabled为true（默认是false），
// 启用Spring security的前注解（例如本例用到的@PreAuthorize,Pre表示前）
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("databaseUserDetailService")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        // session无效时跳转的url
        http.sessionManagement().invalidSessionUrl("/session/invalid")
                .maximumSessions(1)// 上面设置maximumSessions设置为1后，只能有一个登录Session，多个登录，后一个会把前一个登录的Sesson失效。
                // 其他地方登录session失效处理URL
                .expiredUrl("/session/expired");

        http.authorizeRequests()
                // 需要放行条跳转的url
                .antMatchers(HttpMethod.GET, "/user/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers(HttpMethod.GET, "/my-login").permitAll()
                .antMatchers(HttpMethod.GET, "/logout/success").permitAll()
                .antMatchers(HttpMethod.GET, "/user-fail").permitAll()
                .antMatchers("/session/invalid").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 设置自定义登录的页面,如果访问一个url没有通过认证,就会被重定向到这个url
                .loginPage("/user/login")
                // 登录页表单提交的 action（th:action="@{/user/login}"） URL
                // 告诉security框架,我们的前端要向/user/login提交表单数据,本项目就是test-login.html中的
                // 表单提交的地址要和这里设置的匹配,然后只要前端用户点击登陆提交表单,然后spring security
                // 框架会自动帮我们进行身份的校验,如果登陆成功,spring security会走登陆成功的逻辑,否则走登陆失败的逻辑
                .loginProcessingUrl("/user/login")
                // .usernameParameter("username") // 默认就是 username
                // .passwordParameter("password") // 默认就是 password


/////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////登陆成功跳转逻辑处理////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         *  登录成功跳转：
         *  登录成功，如果是直接从登录页面登录，会跳转到该URL；
         *  如果是从其他页面跳转到登录页面，登录后会跳转到原来页面。
         *  可设置true来任何时候到跳转 .defaultSuccessUrl("/hello2", true);
         */
//                    .defaultSuccessUrl("/hello2")
                .successHandler(new CustomAuthenticationSuccessHandler())

                        // 登录成功重定向（和上面二选一）
//                    .successForwardUrl("/hello3")


/////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////登陆失败跳转逻辑处理////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
                // 登录失败跳转到的url，指定的路径要能匿名访问
                .failureUrl("/login-fail")

                // 登录失败重定向（和上面二选一）
//                .failureForwardUrl("/login-fail")
//                .failureHandler(new CustomAuthenticationFailureHandler())
//                .failureHandler(new ExceptionMappingAuthenticationFailureHandler())


                .and()
                .logout()
                .logoutUrl("/logout2") // 退出登录的url, 默认为/logout
                // 退出成功跳转URL，注意该URL不需要权限验证
                .logoutSuccessUrl("/logout/success").permitAll()
//                /退出登录成功处理器
//                .logoutSuccessHandler(logoutSuccessHandler)
                // 退出登录删除指定的cookie
                .deleteCookies("xzj-cookie-name") // 默认退出后不会删除Cookie。可配置退出后删除

                .and()
                .rememberMe()
                // 即登录页面的记住登录按钮的参数名
                .rememberMeParameter("remember-me")
                // 过期时间
                .tokenValiditySeconds(1800)
        // post请求需要csrf验证, 这里使用Thymeleaf模板引擎，表单默认发送csrf，可不用关闭
        .and()
        .csrf().disable();
    }

    @Bean
    public ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler(){
        ExceptionMappingAuthenticationFailureHandler handler = new ExceptionMappingAuthenticationFailureHandler();
        HashMap<String, String> map = new HashMap<>();
        // 登录失败时，跳转到 /badCredentials
        map.put(BadCredentialsException.class.getName(), "/badCredentials");
        // 用户过期时，跳转到 /accountExpired
        map.put(AccountExpiredException.class.getName(), "/accountExpired");
        // 用户被锁定时，跳转到 /locked
        map.put(LockedException.class.getName(), "/locked");
        handler.setExceptionMappings(map);
        return handler;
    }

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 不对密码进行加密的PasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }
}
