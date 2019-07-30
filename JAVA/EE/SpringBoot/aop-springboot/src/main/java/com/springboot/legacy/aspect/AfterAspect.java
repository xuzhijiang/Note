package com.springboot.legacy.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AfterAspect {

	@After("args(name)") // 参数名为name
	public void logStringArguments(String name){
		System.out.println("AfterAspect Running After Advice. String argument passed="+name);
	}
	
}
