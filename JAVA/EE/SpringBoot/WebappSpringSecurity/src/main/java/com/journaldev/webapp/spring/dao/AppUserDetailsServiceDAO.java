package com.journaldev.webapp.spring.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Spring安全示例UserDetailsService DAO实现
// 由于我们也将使用基于DAO的身份验证，我们需要实现UserDetailsService接口
// 并提供loadUserByUsername(）方法的实现。

// 理想情况下，我们应该使用一些资源来验证用户，但为了简单起见，我只是进行基本验证。

public class AppUserDetailsServiceDAO implements UserDetailsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		
		logger.info("loadUserByUsername username="+username);
		
		if(!username.equals("pankaj")){
			throw new UsernameNotFoundException(username + " not found");
		}
		
		// 请注意，我正在创建UserDetails的匿名内部类并返回它。 
		// 您可以为它创建一个实现类，然后实例化并返回它。 通常这是实际应用程序的方式。
		//creating dummy user details, should do JDBC operations
		return new UserDetails() {
			
			private static final long serialVersionUID = 2059202961588104658L;

			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return username;
			}
			
			@Override
			public String getPassword() {
				return "pankaj123";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
				auths.add(new SimpleGrantedAuthority("admin"));
				return auths;
			}
		};
	}

}
