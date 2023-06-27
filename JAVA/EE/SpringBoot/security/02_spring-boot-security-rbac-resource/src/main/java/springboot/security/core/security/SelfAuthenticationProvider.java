package springboot.security.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义账号密码验证(这个只是为了理解spring security认证流程,可以不用自定义,spring security已经帮我们做好了)
 */
@Configuration
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端携带的username和password
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // 获取数据库中的用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 检查前端密码和数据库密码
        boolean checkpw = BCrypt.checkpw(password, userDetails.getPassword());

        if (!checkpw) {
            throw new BadCredentialsException("用户名或密码不正确,请重新输入!!");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    // 这个要返回true,否则认证有问题
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
