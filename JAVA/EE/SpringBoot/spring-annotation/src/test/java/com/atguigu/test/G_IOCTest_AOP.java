package com.atguigu.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.atguigu.aop.MathCalculator;
import com.atguigu.bean.Boss;
import com.atguigu.bean.Car;
import com.atguigu.bean.Color;
import com.atguigu.bean.Red;
import com.atguigu.config.MainConfigOfAOP;
import com.atguigu.config.MainConifgOfAutowired;
import com.atguigu.dao.BookDao;
import com.atguigu.service.BookService;

public class G_IOCTest_AOP {
	
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
		
		//1、不要自己创建对象,否则aop功能就不生效,我们需要把这个对象加入到容器中.然后从容器中获取
//		MathCalculator mathCalculator = new MathCalculator();
//		mathCalculator.div(1, 1);

		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
		// 执行顺序: 先执行前置通知,然后是执行目标方法,目标方法执行完后是执行后置通知,
		// 如果目标方法是正常返回,还会执行返回通知.
		// 如果目标方法不是正常返回,执行过程中抛异常了,在执行完后置通知之后,就不会执行返回通知,而是会执行异常通知.
		mathCalculator.div(1, 0);
		
		applicationContext.close();
	}

}
