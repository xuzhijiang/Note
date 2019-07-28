package com.springboot.beanscope.service;

import com.springboot.beanscope.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutowireEmployeeService {

    // 基于字段的依赖注入
    // @Autowired
    private Employee employee;

    // 选择一个构造好的bean注入,(可以是从xml构造好的bean,或者是@Component构造好的,或者是@Configuration构造好的)
    // 在构造器上的@Autowired相当于xml中bean加上autowire="constructor"
	@Autowired(required = true)
	public AutowireEmployeeService(Employee e){
		this.employee=e;
	}


    //	6. @Qualifier注释 - 此注释用于避免bean映射中的冲突，我们需要为此注解提供将用于自动装配的bean的name。
    // 此注释通常与@Autowired注释一起使用。
//    @Autowired
//    public void setEmployee(@Qualifier("employee") Employee e){
//        this.employee=e;
//    }

    public Employee getEmployee() {
        return employee;
    }
}
