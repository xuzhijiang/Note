package com.security.jwt.core.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtTokenProvider类提供生成token以及验证token的方法
 */
@Component
public class JwtTokenProvider {

    Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private AuthParameters authParameters;

    /**
     * Generate token for user login.
     *
     * @param authentication
     * @return return a token string.
     */
    public String createJwtToken(Authentication authentication) {
        // 在com.sayo.authlogin.service.DatabaseUserDetailService中已经把验证通过的用户封装到了
        // spring-security框架提供的org.springframework.security.core.userdetails.User中,所以这里可以直接拿到使用
        //username
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        //expire time
        Date expireTime = new Date(System.currentTimeMillis() + authParameters.getTokenExpiredMs());
        //create token
        /**
         * 生成token主要用到四个元素：
         *  1）username: token主要的标志
         *  2）expireTime: token过期时间（xxx ms）
         *  3）issuedDate: token的创建时间
         *  4）signWith: token签名，包括签名方法和密钥
         */
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireTime)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, authParameters.getJwtTokenSecret())
                .compact();

        return token;
    }

    /**
     * validate token eligible.
     * if Jwts can parse the token string and no throw any exception, then the token is eligible.
     *
     * @param token a jws string.
     */
    public boolean validateToken(String token) {
        String VALIDATE_FAILED = "validate failed : ";
        // 解析时parseClaimsJws()有可能会抛出以下5个exception，可以分别catch处理,log出日志，这里都统一处理了。
        //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
        /**
         * 1）ExpiredJwtException token时效过期异常
         * 2）UnsupportedJwtException 验证的token和期待的token格式不一样时，例如解析的是一个明文JWT而期待的是一个加密签名JWT时就会抛出这个异常。
         * 3）MalformedJwtException 表示这不是一个正确方法创建的token。
         * 4）SignatureException token签名验证失败异常
         * 5）IllegalArgumentException token为null或者空异常
         */
        try {
            // 验证token用同样的密钥去解开token, 倘若能解开则表示该token是合法可用的
            Jwts.parser().setSigningKey(authParameters.getJwtTokenSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            logger.error(VALIDATE_FAILED + ex.getMessage());
            return false;
        }
    }
}
