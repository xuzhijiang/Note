package com.journaldev.spring;

import org.springframework.context.annotation.Bean;

//What if we remove @Configuration annotation?

//What will happen if we remove the @Configuration annotation 
//from MyConfiguration class. You will notice that it still works 
//as expected and spring beans are registered and retrieved as singleton classes. 
//But in this case, if we make a call to myBean() method then 
//it will be a plain java method call and we will get a new 
//instance of MyBean and it won’t remain singleton. 
//To prove this point, let’s define another bean that will be using MyBean instance.

//如果我们从MyConfiguration类中删除@Configuration注释会发生什么？

//您会注意到它仍然按预期工作，并且spring bean被注册并作为singleton类检索。 
//但是在这种情况下，如果我们调用myBean() method，那么它将是一个普通的java方法调用(be a plain java method call)，
//我们将得到一个新的MyBean实例，它不会保持单例。 为了证明这一点，让我们定义另一个将使用MyBean实例的bean。

public class MyConfiguration3 {

	@Bean
    public MyBean myBean() {
		return new MyBean();
	}
	
	@Bean
    public MyBeanConsumer myBeanConsumer() {
		return new MyBeanConsumer(myBean());
	}
}
