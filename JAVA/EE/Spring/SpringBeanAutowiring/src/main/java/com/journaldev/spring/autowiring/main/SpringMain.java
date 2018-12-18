package com.journaldev.spring.autowiring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.autowiring.service.EmployeeAutowiredByConstructorService;
import com.journaldev.spring.autowiring.service.EmployeeAutowiredByTypeService;
import com.journaldev.spring.autowiring.service.EmployeeService;

// As you can see that for autowire byName and byType, 
// default no-args constructor is used to initialize the bean. 
// For autowire by constructor, parameter based constructor is used.

// 正如您所看到的，对于autowire byName和byType，默认使用no-args(无参)构造函数来初始化bean。 
// 对于构造函数的autowire(For autowire by constructor)，使用基于参数的构造函数来初始化bean.

// 从所有变量的哈希码，我们已经确认所有的spring bean都是不同的对象，而不是引用同一个对象。

public class SpringMain {

	public static void main(String[] args) {
		// 我们只是创建spring应用程序上下文, 并使用它来获取不同的bean并打印员工姓名。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		EmployeeService serviceByName = ctx.getBean("employeeServiceByName", EmployeeService.class);
		
		System.out.println("Autowiring byName. Employee Name="+serviceByName.getEmployee().getName());
		
		EmployeeService serviceByType = ctx.getBean("employeeServiceByType", EmployeeService.class);
		
		System.out.println("Autowiring byType. Employee Name="+serviceByType.getEmployee().getName());
		
		EmployeeService serviceByConstructor = ctx.getBean("employeeServiceConstructor", EmployeeService.class);
		
		System.out.println("Autowiring by Constructor. Employee Name="+serviceByConstructor.getEmployee().getName());
		
		//printing hashcode to confirm all the objects are of different type
		System.out.println(serviceByName.hashCode()+"::"+serviceByType.hashCode()+"::"+serviceByConstructor.hashCode());
		
		//Testing @Autowired annotations
		EmployeeAutowiredByTypeService autowiredByTypeService = ctx.getBean("employeeAutowiredByTypeService",EmployeeAutowiredByTypeService.class);
		
		System.out.println("@Autowired byType. Employee Name="+autowiredByTypeService.getEmployee().getName());

		EmployeeAutowiredByConstructorService autowiredByConstructorService = ctx.getBean("employeeAutowiredByConstructorService",EmployeeAutowiredByConstructorService.class);
		
		System.out.println("@Autowired by Constructor. Employee Name="+autowiredByConstructorService.getEmployee().getName());

		ctx.close();
	}

}

// 由于我们从符合条件的bean列表中删除了“employee1”以进行自动装配，因此bean映射中没有混淆。
// 如果我们从“employee1”定义中删除autowire-candidate =“false”，
// 我们将在执行上述main方法时得到以下错误消息。


//Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'employeeServiceByType' defined in class path resource [spring.xml]: Unsatisfied dependency expressed through bean property 'employee': : No qualifying bean of type [com.journaldev.spring.autowiring.model.Employee] is defined: expected single matching bean but found 2: employee,employee1; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type [com.journaldev.spring.autowiring.model.Employee] is defined: expected single matching bean but found 2: employee,employee1
//	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireByType(AbstractAutowireCapableBeanFactory.java:1278)
//	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1170)
//	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:537)
//	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:475)
//	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:304)
//	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:228)
//	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:300)
//	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:195)
//	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:700)
//	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:760)
//	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:482)
//	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:139)
//	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:83)
//	at com.journaldev.spring.autowiring.main.SpringMain.main(SpringMain.java:12)
//Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type [com.journaldev.spring.autowiring.model.Employee] is defined: expected single matching bean but found 2: employee,employee1
//	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:967)
//	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:855)
//	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireByType(AbstractAutowireCapableBeanFactory.java:1263)
//	... 13 more
