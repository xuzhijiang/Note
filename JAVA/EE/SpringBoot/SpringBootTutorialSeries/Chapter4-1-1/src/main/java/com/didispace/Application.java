package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 我们在编写Spring Boot应用中经常会遇到这样的场景，比如：我需要定时地发送一些短信、
 * 邮件之类的操作，也可能会定时地检查和监控一些标志、参数等
 */
@SpringBootApplication
@EnableScheduling//在Spring Boot的主类中加入@EnableScheduling注解，启用定时任务的配置
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
