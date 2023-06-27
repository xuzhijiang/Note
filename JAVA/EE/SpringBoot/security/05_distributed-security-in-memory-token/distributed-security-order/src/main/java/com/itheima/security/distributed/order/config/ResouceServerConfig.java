package com.itheima.security.distributed.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer // 标记它是一个资源服务
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    // 资源的id,当前的Order服务就是一个资源,给这个资源起一个名字
    // 要和授权服务给客户端配置的资源id要一直.
    public static final String RESOURCE_ID = "res1";

    // tokenStore：TokenStore类的实例，指定令牌如何访问
    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证
        resources.resourceId(RESOURCE_ID)//资源 id
                .tokenServices(tokenService())//验证令牌的服务,令牌过来访问你了,你肯定要验证令牌啊,这个服务就是干这个的
                // 用不到session了,所以stateless可以设置为true
                .stateless(true);
    }

    // 安全访问的策略
    // HttpSecurity配置这个与Spring Security类似：
    //请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是保护资源服务的全部路径。
    //通过http.authorizeRequests()来设置受保护资源的访问规则
    //其他的自定义权限保护规则通过 HttpSecurity 来进行配
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 所有访问/**必须都有什么scope(也就是访问/**便须有什么权限)
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and().csrf().disable()
                // 因为我们是基于tokne的方式,session就不用再用了
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // 令牌验证服务 (ResourceServerTokenServices 类的实例，用来实现校验令牌的合法性)
    // 流程就是: 当有人携带令牌来 访问Order这个服务资源的时候,比如访问controller中的/r1的时候,资源服务就会通过
    // RemoteTokenServices来请求远程的UAA认证服务器: http://localhost:53020/uaa/oauth/check_token 来校验令牌的合法性
    @Bean
    public ResourceServerTokenServices tokenService() {
        // 使用远程服务 校验token,必须指定校验token 的url、client_id，client_secret
        RemoteTokenServices service=new RemoteTokenServices();
        // 当有人访问OrderController中的资源的时候,就会通过RemoteTokenServices请求远程uaa服务的校验token的端点: http://localhost:53020/uaa/oauth/check_token
        // 通过这个地址来校验令牌,这个地址我们在认证服务器已经开放了
        // 校验的token时候,肯定要携带token,授权服务器会返回这个token所拥有的权限等信息.
        service.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token"); // uaa的校验token端点(url)
        service.setClientId("c1");
        service.setClientSecret("secret");
        return service;
    }

}
