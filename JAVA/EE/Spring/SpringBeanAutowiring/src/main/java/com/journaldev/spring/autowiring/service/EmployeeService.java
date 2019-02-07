package com.journaldev.spring.autowiring.service;

import com.journaldev.spring.autowiring.model.Employee;

//As you can see that for autowire byName and byType, 
//default no-args constructor is used to initialize the bean. 
//For autowire by constructor, parameter based constructor is used.

//正如您所看到的，对于autowire byName和byType，默认使用no-args(无参)构造函数来初始化bean。 
//对于构造函数的autowire(For autowire by constructor)，使用基于参数的构造函数来初始化bean.

// 创建我们的服务类，我们将通过spring autowiring(自动装配)注入Employee bean 到此服务

// 我们将使用相同的服务类来执行Spring自动装配byName，byType和构造函数。 
// setter方法将用于Spring自动装配byName和byType，而基于构造函数的注入将由构造函数autowire属性使用。

// 当我们使用spring autowire byName或byType时，使用默认构造函数。 
// 这就是我们为EmployeeService bean明确定义默认构造函数的原因。
public class EmployeeService {

	private Employee empl;

	// 1. 用于自动装配的构造函数
	// 2. constructor is used for autowiring by constructor
	// 3. 通过构造器自动装配会调用这个方法
	public EmployeeService(Employee emp) {
		System.out.println("EmployeeService Autowiring by constructor used");
		this.empl = emp;
	}

	// 1. default constructor to avoid BeanInstantiationException for autowiring
	// 2. 默认的构造函数，以避免自动装配过程中的BeanInstantiationException
	// 3. 通过byName or byType的自动装配会走这个方法
	public EmployeeService() {
		System.out.println("EmployeeService Default Constructor used");
	}

	// used for autowire byName and byType
	// 用于通过name和type自动装配Employee(也就是通过这个setEmployee方法类设置成员变量this.employee)
	public void setEmployee(Employee emp) {
		System.out.println("------------xzj");
		this.empl = emp;
	}

	public Employee getEmployee() {
		return this.empl;
	}
}