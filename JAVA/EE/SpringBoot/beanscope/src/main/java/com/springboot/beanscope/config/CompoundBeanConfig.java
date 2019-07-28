package com.springboot.beanscope.config;

import com.springboot.beanscope.bean.BeanPostProcessorBean;
import com.springboot.beanscope.bean.Employee;
import com.springboot.beanscope.bean.EmployeeConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CompoundBeanConfig {

    @Autowired
    Employee employee;

    @Bean
    @Scope("singleton")
    //@Scope("prototype")
    Employee getEmployee()  { // 注意方法不能为private
        return new Employee();
    }

    @Bean
    EmployeeConsumer getEmployeeConsumer() {
        return new EmployeeConsumer(employee);
    }

    // 测试BeanPostProcessor
    @Bean
    BeanPostProcessorBean getBeanPostProcessorBean() {
        return new BeanPostProcessorBean();
    }
}
