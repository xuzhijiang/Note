package com.journaldev.spring.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//Spring框架还支持@PostConstruct和@PreDestroy注释，
//这些注释是javax.annotation包的一部分。 但是，要使这些注释起作用，
//我们需要配置spring应用程序以查找注释。 
//我们可以通过定义org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
//类型的bean或spring bean配置文件中的context：annotation-config元素来实现。
public class MyService {

	@PostConstruct
	public void init(){
		System.out.println("MyService init method called");
	}
	
	public MyService(){
		System.out.println("MyService no-args constructor called");
	}
	
	@PreDestroy
	public void destory(){
		System.out.println("MyService destroy method called");
	}
	
}
