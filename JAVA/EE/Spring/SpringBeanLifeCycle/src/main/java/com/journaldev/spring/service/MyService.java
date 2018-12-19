package com.journaldev.spring.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 我们将稍微研究一下spring bean配置文件。 在此之前，
// 让我们创建另一个使用@PostConstruct和@PreDestroy注释的服务类。

//下面的类它将配置为spring bean，对于post-init和pre-destroy方法，
// 我们使用@PostConstruct和@PreDestroy注释。
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
