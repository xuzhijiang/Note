package com.journaldev.spring.main;

import com.journaldev.spring.service.EmployeeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// 看看这些aspects如何切入bean方法:
public class SpringMainAnnotationAspect {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        // 参数1：要检索的bean的名字
        EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);

        System.out.println(employeeService.getEmployee().getName());

        employeeService.getEmployee().setName("xzj");

        ctx.close();

        // 从console可以看到advices 根据其切入点配置(pointcut configurations)逐个执行。
    }

}
