package com.springboot.advanced;

import com.springboot.advanced.applicationevent.MyEventPublisher;
import com.springboot.advanced.beans.async.AsyncTaskService;
import com.springboot.advanced.beans.aware.BeanNameAwareService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@EnableScheduling//使用注解启用定时调用
@EnableAsync//此注解启用异步调用支持
public class SpringframeworkadvanceApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(
				SpringframeworkadvanceApplication.class, args);

		testApplicationEvent(context);

		testBeanAware(context);

		testAsyncTask(context);
	}

	private static void testAsyncTask(ApplicationContext context) {
		AsyncTaskService asyncTaskService=context.getBean(
				AsyncTaskService.class);
		//由于启用了异步调用支持，process方法将在不同的线程中执行
		for(int i=0;i<100;i++){
			asyncTaskService.process(i);
		}
	}

	private static void testApplicationEvent(ApplicationContext context) {
		MyEventPublisher demoPublisher = context.getBean(MyEventPublisher.class);
		demoPublisher.publish("当前时间："+new Date());
	}

	private static void testBeanAware(ApplicationContext context){
		BeanNameAwareService awareService=context.getBean(BeanNameAwareService.class);
		System.out.println(awareService);
	}
}
