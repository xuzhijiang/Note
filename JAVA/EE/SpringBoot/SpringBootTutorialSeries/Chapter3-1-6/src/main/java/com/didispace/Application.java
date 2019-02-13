package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 至此，已完成在Spring Boot中创建统一的异常处理，实际实现还是依靠Spring MVC的注解，
 * 更多更深入的使用可参考Spring MVC的文档。
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
