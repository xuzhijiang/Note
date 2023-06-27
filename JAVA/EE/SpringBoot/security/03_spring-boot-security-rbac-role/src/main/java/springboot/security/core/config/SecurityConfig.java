package springboot.security.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    //安全拦截机制（最重要）,相当于定义拦截器的策略了
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 为了方便,禁用了csrf
                .authorizeRequests() // 定制请求的授权规则,(authorizeRequests意思: 授权请求
                .antMatchers("/login*").permitAll()
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
                .antMatchers("/admin/**").hasAnyRole("ADMIN") // 可以传多个参数,表明只要有其中一个role就可以访问
                //.antMatchers("/vip1/**").hasRole("VIP1") // 需要有VIP1这个权限才可以访问/vip1/**
//                .anyRequest().permitAll()// 其它的请求可以访问
                .antMatchers("/index").permitAll() // 访问/不需要任何认证就可以访问
                .anyRequest().authenticated() // 所有的请求都需要认证

                .and()
                // 允许基于表单的认证 (Specifies to support form based authentication.)
                .formLogin()
                // Specifies the URL to send users to if login is required
                // 告诉spring security,我们的登录地址,如果访问的资源需要登录,spring security会自动重定向到这个地址,如果用户没有配置,则默认为/login
                .loginPage("/login-view")
                // Specifies the URL to validate the credentials: 指定验证用户凭证的url(validate username and password),
                // 这个验证用户密码的流程是security自动的,我们只需要定义UserDetailsService即可,
                // 剩下的security会自动调用UserDetailsService的loadUserByUsername来进行账号和密码的比对
                .loginProcessingUrl("/login")
                .usernameParameter("username") // 定义表单中用户名使用的参数名字
                .passwordParameter("password") // 定义表单中密码使用的参数名字
                .defaultSuccessUrl("/")
//                .successForwardUrl("/");//自定义登录成功跳转的url (这个方法添加上,导致认证不能通过,报405,目前原因未知)

                // 默认情况下,在使用了WebSecurityConfigurerAdapter之后,如果用户身份认证失败,
                // 页面就重定向到上面.loginPage("/login-view")中定义的url,本例就是login-view: /login-view?error
                // 当然如果我们没有定义.loginPage(),则默认会重定向到 /login?error
//                .failureForwardUrl("用户身份认证失败,重定向到哪个url")

                .and()
                .sessionManagement()
                //登陆成功以后，将cookie发给浏览器保存，以后只要在cookie没有失效的时候 访问页面带上这个cookie，就可以通过检查,就可以免登录了
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                .and()
                // 默认访问/logout会发生注销操作(post方法)
                .logout()
                /**
                 * The URL that triggers log out to occur (default is "/logout"). If CSRF protection
                 * is enabled (default), then the request must also be a POST. This means that by
                 * default POST "/logout" is required to trigger a log out. If CSRF protection is
                 * disabled, then any HTTP method is allowed.
                 * */
                .logoutUrl("/logout")
                // 默认: 注销成功会重定向到.loginPage("/login-view")中定义的url: /login-view?logout
//                .logoutSuccessUrl("/login-view?logout")

                .and()
                 //自定义403错误处理器
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .rememberMe().rememberMeParameter("remeber-xzj");  //开启记住我功能,参数的值就是remember.
        // 当然记住我功能生效的前提是关闭浏览器之后,浏览器不会自动清空cookie,如果会自动清空cookie,重启浏览器之后就要重新登录了,
        // 记住我功能会失效.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 一开始为了简单,我们把用户信息直接存放在内存
/*
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return manager;
    }
*/

    // 不采用任何加密方式的密码编码器
    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/

	/*@Override
	public void configure(WebSecurity web) throws Exception {
        // 我们希望在除HTML页面之外的所有页面中应用身份验证。
        // 通过覆盖configure(WebSecurity web）方法忽略所有HTML文件。
		web.ignoring().antMatchers("/*.html")
        // Spring Security应该完全忽略以/ resources /开头的URL
        // Spring Security should completely ignore URLs starting with /resources/, Spring Boot configured this already.
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}*/
}