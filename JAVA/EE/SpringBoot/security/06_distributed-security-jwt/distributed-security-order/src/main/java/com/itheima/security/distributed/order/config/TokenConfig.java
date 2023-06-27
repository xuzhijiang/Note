package com.itheima.security.distributed.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 具体的微服务不再进行token的校验和解析,而是统一交给Gateway网关
 */
/*@Configuration
public class TokenConfig {

    // jwt可以采用非对称加密和对称加密,我们这里为了测试方便采用对称加密
    // 所以在对称加密的时候,这个密钥在资源服务(也就是order服务)和授权服务(uaa)中必须要保持一致
    // 对于对称加密,我们在资源服务中采用同样的密钥对jwt令牌进行解密
    private String SIGNING_KEY = "uaa123";

    // 令牌存储策略: jwt
    @Bean
    public TokenStore tokenStore() {
        //JWT令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    // 使用密钥生成jwt令牌
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }
}*/
