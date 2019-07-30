package com.springboot.legacy.standalone;

import com.springboot.legacy.aspect.*;
import com.springboot.legacy.bean.Employee;
import com.springboot.legacy.service.EmployeeService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectTest {

    /**
     * 基于注解的Aspect测试
     */
    @Test
    public void testAnnotationAspect() {
        // 扫描指定的包,注册此包下的组件
        // AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.springboot.legacy");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, AnnotationAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("xzj");

        ctx.close();
    }

    /**
     * Before测试
     */
    @Test
    public void testBeforeAspect() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, BeforeAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("xzj");

        ctx.close();
    }

    /**
     * After测试
     */
    @Test
    public void testAfterAspect() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, AfterAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("xzj");

        ctx.close();
    }

    /**
     * AfterReturning测试
     */
    @Test
    public void testAfterReturningAspect() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, AfterReturningAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("aaa");
        employeeService.getEmployee().getName();

        ctx.close();
    }
    /**
     * AfterThrowing测试
     */
    @Test
    public void testAfterThrowing() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, AfterThrowingAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("aaa");
        employeeService.getEmployee().getName();
        employeeService.getEmployee().throwException();

        ctx.close();
    }

    /**
     * Around测试
     */
    @Test
    public void testAround() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, AroundAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("aaa");
        System.out.println("name: " + employeeService.getEmployee().getName());

        ctx.close();
    }

    /**
     * PointAspect测试
     */
    @Test
    public void testPointAspect() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Employee.class, EmployeeService.class, PointcutAspect.class);
        ctx.refresh();

        EmployeeService employeeService = ctx.getBean(EmployeeService.class);
        employeeService.getEmployee().setName("aaa");
        System.out.println("name: " + employeeService.getEmployee().getName());

        ctx.close();
    }

    /**
     * xml aspect测试
     */
    @Test
    public void testXmlAspect() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-bean.xml");
        EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);

        employeeService.getEmployee().setName("xzj");
        System.out.println(employeeService.getEmployee().getName());

        ctx.close();
    }
}
