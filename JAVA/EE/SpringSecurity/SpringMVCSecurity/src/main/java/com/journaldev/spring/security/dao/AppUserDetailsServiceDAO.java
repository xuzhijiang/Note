package com.journaldev.spring.security.dao;

import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// UserDetailsService
// 如果我们想要使用任何DAO类进行身份验证，
// 我们需要实现UserDetailsService接口。
// 配置DAO后，它的loadUserByUsername（）用于验证用户。

// 请注意，我通过使用匿名内部类实现返回UserDetails实例。
 // 理想情况下，我们应该有一个UserDetails的实现类，
// 它也可以包含其他用户数据，例如emailID，用户名，地址等。

// 请注意，唯一可行的组合是用户名为“pankaj”且密码为“pankaj123”。
public class AppUserDetailsServiceDAO implements UserDetailsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		
		logger.info("loadUserByUsername username="+username);
		
		if(!username.equals("pankaj")){
			throw new UsernameNotFoundException(username + " not found");
		}
		
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
				auths.add(new SimpleGrantedAuthority("Admin"));
				return auths;
			}
		};
	}

}
