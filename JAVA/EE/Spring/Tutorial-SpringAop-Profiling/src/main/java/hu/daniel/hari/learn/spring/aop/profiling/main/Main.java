package hu.daniel.hari.learn.spring.aop.profiling.main;

import hu.daniel.hari.learn.spring.aop.profiling.service.FooService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// 您可以看到为每个服务方法调用生成的分析日志。
// 您可以在同一个包或更低版本中创建其他服务类，也可以对其进行概要分析。

// 如果您使用来自附加源的log4j.properties文件，并取消注释第一行，您可以看到底层发生了什么。


/**
 * Example test class for Spring AOP Profiling.
 * 
 * (remark: Exceptional calls should be put in try catch and handle ctx.close
 * properly but we missed to keep simleness of this example.)
 * 
 * @author Daniel Hari
 */
public class Main {
	public static void main(String[] args) {

		// Create Spring application context that configured by xml.
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		// Get our service from the spring context (that we want to be profiled).
		FooService fooService = ctx.getBean(FooService.class);

		// Test profiling through methods calls.

		for (int i = 0; i < 10; i++) {
			fooService.doShortJob();
		}
		fooService.doLongJob();

		// Test that profiler also can handle Exceptions in profiled method.
		fooService.doSomethingThatThrowsException();

		// Close the spring context
		ctx.close();
	}
}
