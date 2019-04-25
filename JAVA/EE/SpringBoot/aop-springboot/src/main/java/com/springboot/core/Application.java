package com.springboot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

/**
 * http://localhost:8080/hello?age=30，观察日志
 */
@RestController
@SpringBootApplication
public class Application {

	@ResponseBody
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(@RequestParam("age") int age){
		return "my age is: " + age;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
