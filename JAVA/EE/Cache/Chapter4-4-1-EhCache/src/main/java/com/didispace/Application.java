package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager）
 */
@SpringBootApplication
@EnableCaching// 在Spring Boot主类中增加@EnableCaching注解开启缓存功能
@EnableJpaRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
