package com.mybatis.spring.tuling;

import com.mybatis.spring.tuling.config.MyBatisConfig;
import com.mybatis.spring.tuling.entity.Employee;
import com.mybatis.spring.tuling.mapper.EmployeeMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainStarter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyBatisConfig.class);
        EmployeeMapper employeeMapper = context.getBean(EmployeeMapper.class);
        List<Employee> list = employeeMapper.list();
        System.out.println(list);
    }
}
