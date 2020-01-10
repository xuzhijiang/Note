package com.spring.bean.scope.core.NonWebApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// 非WebApplicationContext环境测试
public class MainClass {

    public static void main(String[] args) {
        classpathXmlApplicationContext();

        System.out.println("------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();

        annotationConfigApplicationContext();
    }

    private static void classpathXmlApplicationContext() {
        // 01.Spring Bean的作用域之间有什么区别

        //创建IOC容器对象
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-beans.xml");
        Book book = (Book) ioc.getBean("book");
        Book book2 = (Book) ioc.getBean("book");
        System.out.println(book==book2);
    }

    // 注解版的测试
    private static void annotationConfigApplicationContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        System.out.println("***********singleton**************");
        InstE obj1 = ctx.getBean(InstE.class);
        InstE obj2 = ctx.getBean(InstE.class);
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
        System.out.println(obj1 == obj2);
        System.out.println("***********************************");
        System.out.println();

        System.out.println("***********prototype**************");
        InstF obj3 = ctx.getBean(InstF.class);
        InstF obj4 = ctx.getBean(InstF.class);
        System.out.println(obj3.hashCode());
        System.out.println(obj4.hashCode());
        System.out.println(obj3 == obj4);
        System.out.println("***********************************");

        ctx.close();
    }
}
