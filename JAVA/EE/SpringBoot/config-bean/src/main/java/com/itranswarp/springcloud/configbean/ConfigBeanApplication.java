package com.itranswarp.springcloud.configbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application.
 */
@SpringBootApplication
public class ConfigBeanApplication {

	@Autowired
	UrlFetcher urlFetcher;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConfigBeanApplication.class, args);
	}
}
