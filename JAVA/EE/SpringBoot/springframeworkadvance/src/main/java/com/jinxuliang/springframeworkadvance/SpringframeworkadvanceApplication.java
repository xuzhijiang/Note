package com.jinxuliang.springframeworkadvance;

import com.jinxuliang.springframeworkadvance.applicationevent.DemoPublisher;
import com.jinxuliang.springframeworkadvance.beans.async.AsyncTaskService;
import com.jinxuliang.springframeworkadvance.beans.aware.AwareService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class SpringframeworkadvanceApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(
				SpringframeworkadvanceApplication.class, args);

		//testApplicationEvent(context);

		//testBeanAware(context);

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
		DemoPublisher demoPublisher=context.getBean(DemoPublisher.class);
		demoPublisher.publish("当前时间："+new Date());
	}

	private static void testBeanAware(ApplicationContext context){
		AwareService awareService=context.getBean(AwareService.class);
		System.out.println(awareService);
	}
}
