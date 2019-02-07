package com.journaldev.spring.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyMainClass {

	public static void main(String[] args) {
		
		// 对于独立应用程序(本程序)，您需要在应用程序中的某个位置初始化容器，
		// 然后使用它来获取spring bean。

		// 1. 我们初始化AnnotationConfigApplicationContext上下文(即Spring IoC容器)
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
		
		// 2. 然后使用getBean（）方法获取MyService的实例。
		MyService service = ctx.getBean(MyService.class);
		
		// 如果您正在寻找基于XML的配置，只需创建Spring XML配置文件，
		// 然后使用以下代码片段初始化上下文
/*		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        MyService app = context.getBean(MyService.class);*/
		
		service.log("Hi");
		
		// 请注意，我两次调用getBean方法并打印哈希码。 由于没有为MyService定义范围，
		// 因此它应该是单例，因此两个实例的哈希码应该相同。
		MyService newService = ctx.getBean(MyService.class);
		
		System.out.println("service hashcode="+service.hashCode());
		System.out.println("newService hashcode="+newService.hashCode());
		
		ctx.close();
	}

}
