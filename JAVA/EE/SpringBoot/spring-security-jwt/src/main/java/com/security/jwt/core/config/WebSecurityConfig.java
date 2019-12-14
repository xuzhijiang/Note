package com.security.jwt.core.config;

import com.security.jwt.core.auth.JwtAuthenticationFilter;
import com.security.jwt.core.service.DatabaseUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
// 添加注解@EnableGlobalMethodSecurity，并设置prePostEnabled为true（默认是false），
// 启用Spring security的前注解（例如本例用到的@PreAuthorize）
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Configuration
    public static class MySecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("databaseUserDetailService")
        private DatabaseUserDetailService userDetailsService;

        @Autowired
        @Qualifier("authenticationSuccessHandler")
        private AuthenticationSuccessHandler successHandler;

        @Autowired
        @Qualifier("authenticationFailHandler")
        private AuthenticationFailHandler failHandler;

        @Autowired
        @Qualifier("authenticationEntryPointImpl")
        private AuthenticationEntryPoint entryPoint;

        @Bean
        public JwtAuthenticationFilter getJwtAuthenticationFilter(){
            return new JwtAuthenticationFilter();
        }

        /**
         * 配置HttpSecurity
         * @param http
         * @throws Exception
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 把自定义的JwtAuthenticationFilter添加到UsernamePasswordAuthenticationFilter之前
            http.addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    // 因为我们使用了token，所以session要禁止掉创建和使用，不然会白白耗掉很多空间，
                    // SessionCreationPolicy设为STATELESS，即永不创建HttpSession并且不会使用HttpSession去获取SecurityContext
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable()
                    .authorizeRequests()
                    // 不需要限制的用permitAll()放行即可
                    .antMatchers("/v2/api-docs/**").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginProcessingUrl("/api/login")
                    /**
                     * successHandler() 和 failureHandler() 是配置登录失败或成功时的处理
                     * 所以我们要继承对应的方法，把想要返回给页面的信息在这两个类中写一下
                     */
                    // 登陆成功之后，spring会跳到AuthenticationSuccessHandler
                    .successHandler(successHandler)
                    // 登陆失败之后，spring会跳到AuthenticationFailHandler
                    .failureHandler(failHandler)
                    // authenticationEntryPoint()是没有登录就请求资源时的处理
                    // 未登录就请求资源时，spring会交给AuthenticationEntryPoint处理
                    .and().exceptionHandling().authenticationEntryPoint(entryPoint);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }
    }

}
