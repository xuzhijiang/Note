package spring.security.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//如果在应用程序中使用@Configuration修饰了一个WebSecurityConfigurerAdapter的子类，
// 则会关闭Spring Boot中的默认Webapp security settings(安全设置.)
@Configuration
@EnableWebSecurity//通过@EnableWebSecurity注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security提供了一个过滤器来拦截请求并验证用户身份.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定了/和/home不需要任何认证就可以访问
                .antMatchers("/", "/home").permitAll()
                // 其他的路径都必须通过身份验证
                .anyRequest().authenticated()
                .and()
                // 通过formLogin()定义当需要用户登录时候要转到/login.
                // 默认情况下,在使用了WebSecurityConfigurerAdapter之后,如果用户身份认证失败，
                // 页面就重定向到/login?error，并且页面中会展现相应的错误信息
                .formLogin()
                .loginPage("/login")
                // 登录成功跳转的url
                .defaultSuccessUrl("/hello")
                .permitAll()
                .and()
                // 默认访问/logout会发生注销操作(post方法),注销完成后重定向到/login?logout
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

    /**
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // (内存中的认证)
        // in-memory authentication
        // auth.inMemoryAuthentication().withUser("pankaj").password("pankaj123").roles("USER");

        // 通过实现UserDetailsService来完成认证.
        // using custom UserDetailsService DAO
        // auth.userDetailsService(new AppUserDetailsServiceDAO());

        // 使用JDBC来完成认证
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");

        final String findUserQuery = "select username,password,enabled "
                + "from Employees " + "where username = ?";
        final String findRoles = "select username,role " + "from Roles "
                + "where username = ?";

        auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery(findUserQuery)
                .authoritiesByUsernameQuery(findRoles);
    }
     */

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

}