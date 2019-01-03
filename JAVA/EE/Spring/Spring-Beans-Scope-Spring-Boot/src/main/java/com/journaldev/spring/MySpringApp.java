package com.journaldev.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// create a main method and test the singleton scope.

public class MySpringApp {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(MyConfiguration.class);

		ctx.refresh();

		MyBean mb1 = ctx.getBean(MyBean.class);
		System.out.println(mb1.hashCode());

		MyBean mb2 = ctx.getBean(MyBean.class);
		System.out.println(mb2.hashCode());

		ctx.close();
	}

}
