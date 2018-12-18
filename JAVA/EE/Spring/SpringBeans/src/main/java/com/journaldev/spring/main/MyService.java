package com.journaldev.spring.main;

import java.util.Date;

// Java Based Spring Bean Configuration

//对于独立应用程序，我们可以使用基于注释的配置以及基于XML的配置。
// 唯一的要求是在我们使用它之前,要在程序中的某个位置初始化上下文。
public class MyService {

	public void log(String msg){
		System.out.println(new Date()+"::"+msg);
	}
}
