package com.security.jwt.core.auth;

import com.security.jwt.core.service.UserService;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 创建JwtAuthenticationFilter类在用户获取资源之前让spring去filter这个token是否合法可用。
 *
 * 继承OncePerRequestFilter重写doFilterInternal方法，前端发送请求时，token会放在header，
 * 在每个请求读取资源之前后台对token进行filter。
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthParameters authParameters;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //1.从每个请求header获取token
        String token = getJwtFromRequest(request);

        //2.调用前面写的validateToken方法对token进行合法性验证
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //3.解析得到username，并从database取出用户相关信息权限
            String username = getUsernameFromJwt(token, authParameters.getJwtTokenSecret());
            UserDetails userDetails = userService.getUserDetailByUserName(username);
            // 把这个用户拥有的权限设置到UsernamePasswordAuthenticationToken中,后续权限验证会用到这个Authentication
            // org.springframework.security.access.expression.SecurityExpressionRoot的getAuthoritySet()中
            // 的authentication就是这个实例
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());

            //4.把用户信息以UserDetail形式放进SecurityContext以备整个请求过程使用。
            // （例如哪里需要判断用户权限是否足够时可以直接从SecurityContext取出去check
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.error(request.getParameter("username") + " :Token is null");
        }
        super.doFilter(request, response, filterChain);
    }

    /**
     * Get Bear jwt from request header Authorization.
     *
     * @param request servlet request.
     * @return token or null.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.replace("Bearer ", "");
        }
        return null;
    }

    /**
     * Get user name from Jwt, the user name have set to jwt when generate token.
     *
     * @param token jwt token.
     * @param signKey jwt sign key, set in properties file.
     * @return user name.
     */
    private String getUsernameFromJwt(String token, String signKey) {
        return Jwts.parser().setSigningKey(signKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
