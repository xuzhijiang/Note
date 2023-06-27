package com.springboot.advanced;

import com.springboot.advanced.applicationevent.MyEventPublisher;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@EnableAsync  //开启异步注解功能
@EnableScheduling//开启基于注解的定时任务
@RestController
public class MainApp implements ApplicationContextAware {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainApp.class, args);
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@GetMapping("/testApplicationEvent")
	public String testApplicationEvent() {
		MyEventPublisher myEventPublisher = applicationContext.getBean(MyEventPublisher.class);
		myEventPublisher.publish("当前时间："+new Date());
		return "OK";
	}
}
