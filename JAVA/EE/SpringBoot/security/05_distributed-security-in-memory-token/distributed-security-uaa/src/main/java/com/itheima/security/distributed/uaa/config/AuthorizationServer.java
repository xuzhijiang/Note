package com.itheima.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 授权服务配置(配置完成后就可以测试oauth2.0协议了)
 *
 * 授权服务配置总结：授权服务配置分成三大块，可以关联记忆。
 *
 * 1. 既然要完成认证，它首先得知道客户端信息从哪儿读取，因此要进行客户端详情配置。
 *
 * 2. 既然要颁发token，那必须得定义token的相关endpoint，以及token如何存取，以及客户端支持哪些类型的token。
 *
 * 3. 既然暴露除了一些token相关的endpoint，那对这些endpoint可以定义一些安全上的约束等。
 **/
@Configuration
// 用 @EnableAuthorizationServer 注解并继承AuthorizationServerConfigurerAdapter来配置OAuth2.0 授权服务器
// @EnableAuthorizationServer 注解就是用于 启用授权服务的配置
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 客户端信息/客户端详情服务(就是我们的授权服务支持哪些客户端)
     * 这里的客户端其实是指第三方网站,不是指普通的大众用户********************
     */
    // 谁来申请令牌,肯定是客户端来申请令牌,所以我们要知道是哪些客户端
    // 客户端要来申请令牌,我们要知道你这个客户端是否是合法的,如果你不合法,不肯定不能给你颁发令牌
    // 这个配置就是干这个的,当一个客户端来申请令牌的时候,要看你客户端是否合法.
    // 这个客户端详情服务类似于UserDetailsService, 你客户端来申请令牌了,这个服务就要负责
    // 查询: 你是不是在我们这边配置好的一个客户端,就是要比较client_id和secret是否一致
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()// 暂时使用in-memory内存存储客户端信息,方便测试
                .withClient("c1")// client_id:（必须的）用来标识客户的Id (黑马这个网站在微信上的clientId)
                //客户端密钥,将来客户端要拿client_id和密钥来申请令牌
                .secret(passwordEncoder.encode("secret")) // (黑马这个网站 在微信上的clientId对应的 密钥)
                .resourceIds("res1")//资源列表,也就是这个c1可以访问哪些资源,这个就对应资源服务的id
                // authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
                // oauth2.0支持5种授权类型
                // 支持的授权类型: authorization_code: 授权码模式
                // password: 密码模式, 比如如果这里没有password,就不支持密码模式
                // client_credentials: 客户端模式
                .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
                .autoApprove(false)//false: 如果使用的是授权码模式,申请令牌的时候会 跳转到授权的页面,让用户进行授权,如果是true,就不用跳转到授权页面,直接发令牌
                // 加上验证回调地址
                .redirectUris("http://localhost");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Autowired
    private TokenStore tokenStore;

    // 配置令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        // 设置客户端信息的详情服务, 因为你是向客户端颁发令牌,所以它要知道是哪个客户端.
        service.setClientDetailsService(clientDetailsService);
        System.out.println("clientDetailsService.getClass(): " + clientDetailsService.getClass());
        service.setSupportRefreshToken(true);// 是否支持刷新令牌
        service.setTokenStore(tokenStore); //令牌存储策略

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    // 在授权码模式下,授权码如何存储，这里采用: 暂时采用内存方式
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    // 授权码服务
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    // 如果密码模式,就需要配置这个
    @Autowired
    private AuthenticationManager authenticationManager;

    // 用来配置令牌（token）的访问端点(访问url)和令牌服务(token services)
    // 令牌访问端点可以理解为: 申请令牌的url地址,这个也要配置,
    // 另外就是有人来申请令牌了,你这个令牌怎么生成和发放,也要配置(令牌生成的服务也要配置)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 如果密码模式,就需要配置这个authenticationManager
                // authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，
                // 请设置这个属性, 注入一个 AuthenticationManager 对象
                .authenticationManager(authenticationManager)//认证管理器

                // 这个属性是用来设置授权码服务的,主要用于 "authorization_code" 授权码类型模式。
                // 这个服务就是用于生成授权码
                .authorizationCodeServices(authorizationCodeServices)//授权码模式需要这个 authorizationCodeServices服务

                .tokenServices(tokenService())//令牌管理服务(如何生成令牌/存储令牌/令牌有效期等)

                // 允许post方法来访问令牌
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);

        // 配置授权端点的URL（Endpoint URLs）：这些端点默认都已经帮我们配置了
        //  /oauth/authorize：授权端点(校验用户身份合法性的url)
        // /oauth/token：令牌端点 (生成令牌的url,也就是颁发令牌的端点)
        // /oauth/confirm_access：用户确认授权提交端点。
        // /oauth/error：授权服务错误信息端点。
        // /oauth/check_token：用于资源服务访问的令牌解析端点 (校验令牌的合法性)
        // /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
        // 需要注意的是授权端点这个URL应该被Spring Security保护起来只供授权用户访问.
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 令牌访问端点的安全策略
     */
    // 针对令牌端点(端点就是url)的安全约束也要配置
    // 上面你暴漏了令牌的url(令牌的端点),那么哪些人可以来访问这个url,也要配置,
    // 这个就是令牌端点的安全约束.
    // 也就是令牌的相关端点(令牌相关的url)不是所有的请求都可以访问,这个安全约束就是干这个的.
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                // 如果要使用jwt,我们采用非对称加密的时候,需要提供一个公钥的端点,
                // 这个时候这个url也可以允许公开, 让资源服务去访问, jwt会用到这个
                // tokenkey这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，
                // 这里指这个endpoint完全公开。
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开

                // 允许检查令牌的合法性
                .checkTokenAccess("permitAll()")                  //oauth/check_token url是公开

                // 允许表单来提交,来认证,来申请令牌(允许表单认证)
                .allowFormAuthenticationForClients()				//表单认证（申请令牌）
        ;
    }

}
