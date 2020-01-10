package com.springboot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringBootPropertiesApplication {
	public static void main(String[] args) throws IOException {
		ApplicationContext ioc = SpringApplication.run(SpringBootPropertiesApplication.class, args);

		System.out.println("service02: " + ioc.containsBean("helloService02"));
		System.out.println("service03: " + ioc.containsBean("helloService03"));
	}
}
