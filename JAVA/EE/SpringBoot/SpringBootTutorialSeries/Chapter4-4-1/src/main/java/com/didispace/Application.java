package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 随着时间的积累，应用的使用用户不断增加，数据规模也越来越大，
 * 往往数据库查询操作会成为影响用户使用体验的瓶颈，
 * 此时使用缓存往往是解决这一问题非常好的手段之一。
 *
 * 我们是如何给一个既有应用增加缓存功能的？
 *
 * 在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager）
 */
@SpringBootApplication
@EnableCaching// 在Spring Boot主类中增加@EnableCaching注解开启缓存功能
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
