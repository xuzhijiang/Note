package com.security.jwt.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主要实现的功能是Login拿到token，再用token请求资源
 *
 * https://www.jianshu.com/p/0292f4e42c84
 */
@SpringBootApplication
public class AuthLoginApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthLoginApplication.class, args);
	}
}
