package com.springboot.beanscope;

import com.springboot.beanscope.bean.AwareBean;
import com.springboot.beanscope.service.EmployeeService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StandaloneXmlTestApplication {

    @Test
    public void testAwareInterface() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-aware.xml");

        ctx.getBean("awareBeanName-xzj", AwareBean.class);

        ctx.close();
    }

    @Test
    public void testXmlBean() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-bean.xml");
        // 执行到这里之前，spring.xml中的bean都会被创建，等待下面使用

        EmployeeService service = ctx.getBean("employee-service-xzj", EmployeeService.class);
        System.out.println("Employee Name="+service.getEmployee().getName());

        ctx.close();
    }


}
