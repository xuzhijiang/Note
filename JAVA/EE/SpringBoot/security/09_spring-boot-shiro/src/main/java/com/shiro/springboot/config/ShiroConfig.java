package com.shiro.springboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 */
@Configuration
public class ShiroConfig {

	/**
	 * Shiro生命周期处理器
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,
	 * 并在必要时进行安全逻辑验证
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	/**
 　　* 开启shiro aop注解支持.
 　　* 使用代理方式;所以需要开启代码支持;
 　　*/
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("xzj-securityManager") SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 创建Realm
	 */
	@Bean("xzj-realm")
	@DependsOn("lifecycleBeanPostProcessor")
	public Realm myShiroRealm(@Qualifier("md5CredentialsMatcher") HashedCredentialsMatcher credentialsMatcher){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(credentialsMatcher);
		return myShiroRealm;
	}

	/**
	 * 创建DefaultWebSecurityManager
	 * 注意: ShiroAutoConfiguration自动配置类中也配置了一个类型为SessionsSecurityManager的,
	 * 名字叫securityManager的bean,所以名字不能冲突了
	 */
	@Bean(name = "xzj-securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("xzj-realm") Realm realm){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(realm);
		return securityManager;
	}

	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("xzj-securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perms： 该资源必须得到资源权限才可以访问
		 *       role: 该资源必须得到角色权限才可以访问
		 */
		// 配置不会被拦截的链接 顺序判断
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		Map<String,String> map = new LinkedHashMap<>();

		// anon:所有url都都可以匿名访问
		map.put("/static/**", "anon");
		map.put("/login", "anon"); //放行login.html页面
		map.put("/", "anon");
		map.put("/index", "anon");
		// 配置logout,其中的具体的退出代码Shiro已经替我们实现了(配置退出过滤器logout，由shiro实现)
		map.put("/logout", "logout");

		//授权过滤器
		//注意：当前没有权限访问的一些受保护页面的时候，shiro会自动跳转到未授权页面
		// filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);
		map.put("/userInfo/userList", "perms[user:view]"); // 访问/userList必须要有user:view
		map.put("/userInfo/userAdd", "perms[user:add]");
		// map.put("/userInfo/userDel", "perms[user:del]");
		// filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[user:add]”);
		// 访问/add必须要有“user:add”这个权限和具有“100002”这个角色
		//访问/user.jsp必须要认证,而且有user这个角色
		// map.put("/user.jsp", "authc,roles[user]");

		// authc:所有url都必须认证通过才可以访问
		map.put("/**", "authc");

		// 设置登录url
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 设置未授权之后跳转到的url(设置未授权提示页面)
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	/**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}

	/**
	 * 密码匹配器
	 */
	@Bean("md5CredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		// 密码的加密算法使用MD5
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		// 加密次数
		hashedCredentialsMatcher.setHashIterations(1024);
		return hashedCredentialsMatcher;
	}
}