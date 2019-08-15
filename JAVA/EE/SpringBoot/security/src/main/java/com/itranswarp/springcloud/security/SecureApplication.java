package com.itranswarp.springcloud.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我们在编写Web应用时，经常需要对页面做一些安全控制，
 * 比如：对于没有访问权限的用户需要转到登录表单页面。要实现访问控制的方法多种多样，
 * 可以通过Aop、拦截器实现，也可以通过框架实现（如：Apache Shiro、Spring Security）。
 */
@SpringBootApplication
public class SecureApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SecureApplication.class, args);
	}
}
