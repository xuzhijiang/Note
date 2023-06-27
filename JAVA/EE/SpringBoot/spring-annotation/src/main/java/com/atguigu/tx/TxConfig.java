package com.atguigu.tx;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.annotation.TransactionManagementConfigurationSelector;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 声明式事务：
 * 
 * 环境搭建：
 * 1、导入相关依赖
 * 		数据源、数据库驱动、Spring-jdbc模块
 * 2、配置数据源、JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据
 * 3、给方法上标注 @Transactional 表示当前方法是一个事务方法；
 * 4、 @EnableTransactionManagement 开启基于注解的事务管理功能；
 * 		这个是必须的,相当于在xml中配置了<tx:annotation-driven/>
 * 5、配置事务管理器来控制事务;
 * 		@Bean
 * 		public PlatformTransactionManager transactionManager()
 * 
 * 
 * 原理：
 * 1）、@EnableTransactionManagement导入了TransactionManagementConfigurationSelector,
 * 			然后利用TransactionManagementConfigurationSelector给容器中会导入组件
 * 			导入两个组件
 * 			AutoProxyRegistrar
 * 			ProxyTransactionManagementConfiguration
 * 2）、AutoProxyRegistrar：
 * 			会给容器中注册一个bean定义: InfrastructureAdvisorAutoProxyCreator 组件；
 * 			InfrastructureAdvisorAutoProxyCreator：是一个后置处理器
 * 			利用后置处理器机制 在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
 * 
 * 3）、ProxyTransactionManagementConfiguration 做了什么？
 * 			1、给容器中注册事务增强器；
 * 				1）、给容器添加了一个bean: AnnotationTransactionAttributeSource,用来例解析(方法和类上的)事务注解
 * 				2）、给容器中添加 事务拦截器：TransactionInterceptor；保存了事务属性信息，事务管理器；(txManager)
 * 					TransactionInterceptor是一个 MethodInterceptor；(我们给容器中放代理对象,代理对象要执行目标方法了,方法拦截器MethodInterceptor就会工作,通过org.aopalliance.intercept.MethodInterceptor#invoke()来工作)
 * 					在目标方法执行的时候(代理对象会执行拦截器链)；
 * 						执行拦截器链(这里拦截器链只有一个就是TransactionInterceptor)；
 * 						事务拦截器(TransactionInterceptor)的作用(通过TransactionInterceptor的invoke方法来追踪)：
 * 							1）、先获取事务相关的属性
 * 							2）、再获取PlatformTransactionManager，如果事先没有添加指定任何transactionmanger(就是这么指定的:@Transactional(transactionManager = "txmanager"))
 * 								最终会从容器中按照"类型"获取一个PlatformTransactionManager(TransactionAspectSupport.beanFactory.getBean(PlatformTransactionManager.class);只需要给容器中添加一个类型为PlatformTransactionManager的bean就可以获取到了)；
 * 							3）、执行目标方法
 * 								如果异常，获取到事务管理器，利用事务管理来回滚操作；(真正的回滚与提交是事务管理器来做的,而Interceptor只是拦截目标方法的执行)
 * 								如果正常，利用事务管理器，提交事务
 * 			
 */
@EnableTransactionManagement
@ComponentScan("com.atguigu.tx") // 把UserDao和UserService扫描进来
@Configuration
public class TxConfig {
	
	//数据源
	@Bean
	public DataSource dataSource() throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("root");
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://192.168.32.150:3307/learn");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception{
		//Spring对@Configuration类会特殊处理；给容器中加组件的方法(比如dataSource())，多次调用(dataSource())都只是从容器中找组件,相当于只会创建一次DataSource
		//这里调用dataSource()返回的DataSource相当于从spring ioc容器中找组件,而不是重新new一个DataSource
		//所以这里调用dataSource()返回的bean和上面@Bean标注DataSource返回的bean 是同一个,因为是singleton的.
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}

	//注册事务管理器在容器中
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		// 事务管理器一定要管理数据源,所以要把dataSource传入,这样才可以控制数据源中的每一条连接Connection
		// Connection的回滚以及事务的开启都是由事务管理器来做.
		return new DataSourceTransactionManager(dataSource());
	}
}
