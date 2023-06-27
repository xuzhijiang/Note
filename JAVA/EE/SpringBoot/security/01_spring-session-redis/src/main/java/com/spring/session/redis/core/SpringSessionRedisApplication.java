package com.spring.session.redis.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 把session存储到redis中，满足集群部署、分布式系统的session共享
 *
 * 实现访问资源时的安全认证、超时控制和用户登出功能
 */
@SpringBootApplication
public class SpringSessionRedisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSessionRedisApplication.class, args);
	}
}
