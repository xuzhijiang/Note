package com.journaldev.spring.main;

import java.util.Date;

// 基于Java的Spring Bean配置

// 1. 对于独立应用程序，我们可以使用"基于注释的配置"以及"基于XML的配置"。
// 2. 唯一的要求是在我们使用context之前,要在程序中的某个位置初始化context。
public class MyService {

	public void log(String msg){
		System.out.println(new Date()+"::"+msg);
	}
}
