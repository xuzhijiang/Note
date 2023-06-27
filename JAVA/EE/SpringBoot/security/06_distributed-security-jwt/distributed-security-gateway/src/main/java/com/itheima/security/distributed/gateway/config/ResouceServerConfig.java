package com.itheima.security.distributed.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 网关会对客户端权限进行校验,这里的权限校验其实校验的就是scope,
 * 看看 它有没有权限来访问我们的微服务.
 *
 * 也就是客户端访问微服务的权限要在网关层校验,校验通过之后,网关要把token解析成明文,
 * 然后把解析后的明文(用户信息)转发给微服务.
 * 这样的话,微服务拿到token之后就不用去解析jwt令牌了.因为token 在网关已经解析过了.
 * 微服务 可以直接 使用 网关转发过来的 明文(用户信息).
 */
@Configuration
public class ResouceServerConfig  {

    // 资源的id,给资源起一个名字,要和授权服务给客户端配置的资源id要一直.
    public static final String RESOURCE_ID = "res1";

    //uaa资源服务配置
    @Configuration
    @EnableResourceServer // 标记它是一个资源服务
    public class UAAServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources
                    .resourceId(RESOURCE_ID)
                    .tokenStore(tokenStore)
                    .stateless(true);
        }

        // 安全访问的策略
        // HttpSecurity配置这个与Spring Security类似：
        //请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是保护资源服务的全部路径。
        //通过http.authorizeRequests()来设置受保护资源的访问规则
        //其他的自定义权限保护规则通过 HttpSecurity 来进行配
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 针对/uaa/**, 全部放行
            http.authorizeRequests()
                 .antMatchers("/uaa/**").permitAll();
        }
    }


    // 网关负责: 1) 转发请求到各个微服务,转发之前要校验客户端传过来的令牌(token)是否合法
    // 除了校验令牌本身是否合法外,还要校验客户端有没有权限来访问网关下面的微服务
    // 就是 下面configure(HttpSecurity http) 这个方法中配置的权限校验

    //order资源 (网关作为OAuth2.0的资源服务器角色，实现接入方权限拦截)
    //uaa资源服务配置
    @Configuration
    @EnableResourceServer // 标记它是一个资源服务
    public class OrderServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore; // 指定令牌如何访问，以及如何校验令牌

        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources
                    .resourceId(RESOURCE_ID) // resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证
                    // 验证令牌有效性的服务,令牌过来访问你了,你肯定要验证令牌啊,这个服务就是干这个的
                    // 远程校验令牌消耗性能,所以用jwt来替代,jwt不需要远程校验
                    // 注意: 令牌校验通过之后,会封装到security上下文中,方便其他地方直接使用:
                    // 校验通过后会封装到 "安全上下文中(SecurityContextHolder.getContext()中)"
                    .tokenStore(tokenStore)
                    .stateless(true);
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 以/order/**的请求,要有ROLE_API的的权限 (也就是访问/order/**便须有什么权限)
            http.authorizeRequests().antMatchers("/order/**")
                    .access("#oauth2.hasScope('ROLE_API')");
        }
    }

    // 还可以配置其它的资源服务..
}
