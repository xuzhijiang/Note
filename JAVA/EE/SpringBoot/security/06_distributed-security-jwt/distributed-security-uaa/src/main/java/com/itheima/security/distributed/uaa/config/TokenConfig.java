package com.itheima.security.distributed.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// 令牌的存储的方案: 包括:
// 1. 内存(InMemoryTokenStore),它可以完美的工作在单服务器上
// 2. 数据库(JdbcTokenStore): 令牌会被保存进关系型数据库
// 3. jwt: JwtTokenStore(最终选择这种方式)：这个版本的全称是 JSON Web Token（JWT），它可以把令牌相关的数据进行编码（因此对
//于后端服务来说，它不需要进行存储，这将是一个重大优势）,但是它有一个缺点，那就是撤销一个已经授
//权令牌将会非常困难，所以它通常用来处理一个生命周期较短的令牌以及撤销刷新令牌（refresh_token）。
//另外一个缺点就是: 如果你加入了比较多用户凭证信息,这个令牌占用的空间会比较大
@Configuration
public class TokenConfig {

    // jwt令牌可以采用对称加密/非对称加密的方式来生成令牌,这里为了测试方便采用对称加密的方式
    // 这里面只需要提供一个密钥就可以了,也就是资源服务和授权服务采用同样的密钥,将来资源服务校验令牌的时候就可以通过
    // 如果是非对称加密的方式,资源服务和授权服务的密钥就不同.
    private String SIGNING_KEY = "uaa123";

    // (令牌存储策略): 使用基于jwt的令牌
    @Bean
    public TokenStore tokenStore() {
        //JWT令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    // 令牌的产生要经过一系列的算法,所以这里配置JwtAccessTokenConverter,用于生成jwt令牌
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        // 授权服务放了这个密钥,资源服务也一定要放这个密钥,这样的话,令牌才可以解开
        return converter;
    }

}
