package com.atguigu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

	//使用@Value赋值；
	//1、基本数值
	//2、可以写SpEL； #{}
	//3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）

	@Value("张三")
	private String name;

	@Value("#{20-2}") // 可以进行计算
	private Integer age;

	@Value("${person.nickName}")
	private String nickName;

	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
}
