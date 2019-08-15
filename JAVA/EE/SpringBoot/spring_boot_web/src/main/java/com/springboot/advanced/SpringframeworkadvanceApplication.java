package com.springboot.advanced;

import com.springboot.advanced.applicationevent.MyEventPublisher;
import com.springboot.advanced.beans.async.AsyncTaskService;
import com.springboot.advanced.beans.aware.BeanNameAwareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling//使用注解启用定时任务的配置
public class SpringframeworkadvanceApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context= SpringApplication.run(
				SpringframeworkadvanceApplication.class, args);

		testApplicationEvent(context);

		testBeanAware(context);

		testAsyncTask(context);

		testAsyncTask2(context);

		testAsyncTask3(context);

		testLog4j();
	}

	private static Logger logger = LoggerFactory.getLogger(SpringframeworkadvanceApplication.class);

	private static void testLog4j() {
		logger.trace("This is a trace log example");
		logger.info("This is an info log example");
		logger.debug("This is a debug log example");
		logger.error("This is an error log example");
		logger.warn("This is a warn log example");
	}

	private static void testAsyncTask3(ApplicationContext context) throws Exception {
		AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
		for (int i = 0; i < 10000; i++) {
			asyncTaskService.doTaskRedis(i);
			if (i == 9999) {
				System.out.println("redis 任务退出");
				System.exit(0);
			}
		}
	}

	/**
	 * 用@async实现异步调用以及自定义线程池
	 */
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

	/**
	 * 使用Future
	 */
	private static void testAsyncTask2(ApplicationContext context) {
		AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
		try {
			Future<String> futureResult = asyncTaskService.doTaskOne();
			// 如果在超时时间内还没有完成,就抛异常了,如果完成,就打印结果.
			String result = futureResult.get(5, TimeUnit.SECONDS);
			System.out.println("result: " + result);
		} catch (Exception e) {
			e.printStackTrace();
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
