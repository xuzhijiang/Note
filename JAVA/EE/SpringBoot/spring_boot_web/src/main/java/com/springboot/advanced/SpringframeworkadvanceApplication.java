package com.springboot.advanced;

import com.springboot.advanced.applicationevent.MyEventPublisher;
import com.springboot.advanced.beans.async.AsyncTaskService;
import com.springboot.advanced.beans.aware.BeanNameAwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableScheduling//使用注解启用定时任务的配置
@EnableAsync//此注解启用异步调用支持
public class SpringframeworkadvanceApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context= SpringApplication.run(
				SpringframeworkadvanceApplication.class, args);

		testApplicationEvent(context);

		testBeanAware(context);

		testAsyncTask(context);
	}

	private static void testAsyncTask(ApplicationContext context) throws Exception {
		AsyncTaskService asyncTaskService=context.getBean(
				AsyncTaskService.class);
		//由于启用了异步调用支持，process方法将在不同的线程中执行
		for(int i=0;i<100;i++){
			asyncTaskService.process(i);
		}

		Future<String> task1 = asyncTaskService.doTaskOne();

		while(true) {
			if(task1.isDone()) {
				// 任务调用完成，退出循环等待
				break;
			}
			System.out.println("在线程"+Thread.currentThread().getName() +"上等待任务一完成");
			Thread.sleep(1000);
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
