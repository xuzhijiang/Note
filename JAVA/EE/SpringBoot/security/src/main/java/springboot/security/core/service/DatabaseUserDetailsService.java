package springboot.security.core.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if(!username.equals("xzj")){
			throw new UsernameNotFoundException(username + " not found");
		}

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
				return new BCryptPasswordEncoder().encode("pwd");
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				List<SimpleGrantedAuthority> auths = new ArrayList<>();
				auths.add(new SimpleGrantedAuthority("admin"));
				return auths;
			}
		};

	}

}
