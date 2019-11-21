package com.springboot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

/**
 * 企业应用程序中一些常见的横切关注点是日志记录，事务管理，数据验证等。
 *
 * 注意: 切面的作用是在方法前后，而不是方法内部的前后。
 *
 * Spring Aop使用CGLIB or JDK Dynamic Proxy?
 * Spring AOP: CGLIB or JDK Dynamic Proxies?
 *
 * 在运行时创建代理对象的不同技术：CGLIB或JDK动态代理。
 * 如果目标类实现了一个或多个接口，那么Spring将创建一个实现每个接口的JDK动态代理类。
 * 如果目标类没有实现接口，Spring将使用CGLIB动态创建一个新类，它是目标类的子类（“extends”）。这导致了一个重要的区别：JDK动态代理无法转换为原始目标类，因为动态代理类恰好实现了与目标类相同的接口。
 * 另一方面，如果model中完全没有接口，Spring将创建CGLIB代理，可以像目标类本身一样对待。
 *
 * 还有一种方法可以在这两种方案中强制创建CGLIB代理，这在Spring文档中有详细说明:https://docs.spring.io/spring/docs/2.0.0/reference/aop.html#d0e9015
 *
 * 也就是设置@EnableAspectJAutoProxy(proxyTargetClass = true),具体可以看EnableAspectJAutoProxy注解
 *
 * 参考:
 * https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/aop.html
 *
 * https://stackoverflow.com/questions/51795511/when-will-is-cglib-proxy-used-by-spring-aop
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
