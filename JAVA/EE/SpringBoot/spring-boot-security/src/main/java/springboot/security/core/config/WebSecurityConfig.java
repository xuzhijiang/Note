package springboot.security.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

// 如果在应用程序中使用@Configuration修饰了一个WebSecurityConfigurerAdapter的子类，
// 则会关闭Spring Boot中的默认security settings(安全设置.)
// 原因:
// org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
// org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration
// @ConditionalOnMissingBean({WebSecurityConfigurerAdapter.class})
@Configuration
// 通过@EnableWebSecurity注解开启Spring Security的功能
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * Spring Security提供了一个过滤器来拦截请求并验证用户身份.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 指定了/和/home,/about不需要任何认证就可以访问
                .antMatchers("/", "/home", "/about").permitAll()
                // roles admin allow to access /admin/**
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                // roles user allow to access /user/**
                .antMatchers("/user/**").hasAnyRole("USER")
                // 如果不符合角色要求,直接访问/homePage，我们将自动重定向到“/loginPage”页面
                // 如果你是以游客(没有登录的状态)的身份访问/user,/admin这些需要特定角色的页面,则通过身份验证
                // 如果你已经登录了系统,获得的某种角色,比如是USER,那么你此时访问/admin,因为你没有权限,所以会得到403的响应
                // 也就是得到access“403 Access is Denied”错误消息。交给403的handler来处理
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 通过formLogin()定义当需要用户登录时候要转到/login.
                .loginPage("/login")
                // 默认情况下,在使用了WebSecurityConfigurerAdapter之后,如果用户身份认证失败
                // 页面就重定向到/login?error，并且页面中会展现相应的错误信息
                .defaultSuccessUrl("/hello") // 登录成功跳转的url
                .permitAll()
                .and()
                // 默认访问/logout会发生注销操作(post方法),注销完成后重定向到/login?logout
                .logout()
                .permitAll()
                .and()
                // custom 403 access denied handler
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * 在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER。
     */
    @Autowired // 这个注解很重要
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password(new BCryptPasswordEncoder().encode("password")).roles("USER");

        // 使用authorities(）方法定义角色，如“ROLE_USER”
        // 也可以使用roles()方法定义角色
        // authorities(）和roles(）方法之间的区别：
        // authorities(）需要完整的角色名称，如“ROLE_USER”
        // roles(）只需要角色名称，如“USER”。它会自动将“ROLE_”值添加到此“USER”角色名称

        // 我们添加了两个用户：一个用户具有“ROLE_USER”角色，
        // 另一个用户具有“ROLE_USER”和“ROLE_ADMIN”角色。这意味着第二个用户将充当管理员用户.
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("a").password(new BCryptPasswordEncoder().encode("aaa")).authorities("ROLE_USER");
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("b").password(new BCryptPasswordEncoder().encode("bbb")).authorities("ROLE_USER","ROLE_ADMIN");
    }

    /**
     *  通过实现UserDetailsService来完成认证.
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    // 我们希望在除HTML页面之外的所有页面中应用身份验证。
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

	/*
    //Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }*/

}