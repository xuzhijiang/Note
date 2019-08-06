package com.springboot.beanscope.service;

import com.springboot.beanscope.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


    /**
     *  在Service和Controller中,经常需要引用bean,但是可能有多个bean候选,
     *  这时可以使用@Qualifier("beanName")- 用于避免在我们为同一类型配置两个bean时出现混淆.
     *  告诉spring框架将名为beanName的bean注入类属性中
     */
//    @Autowired
//    public void setEmployee(@Qualifier("employee") Employee e){
//        this.employee=e;
//    }

    public Employee getEmployee() {
        return employee;
    }
}
