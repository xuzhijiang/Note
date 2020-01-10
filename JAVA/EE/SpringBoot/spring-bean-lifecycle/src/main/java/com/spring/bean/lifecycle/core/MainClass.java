package com.spring.bean.lifecycle.core;

import com.spring.bean.lifecycle.core.beans.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

    public static void main(String[] args) {
        annotationConfigApplicationContext();

//        System.out.println("------------------------------");
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        classpathXmlApplicationContext();
    }

    private static void annotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig.class, InstA.class);
        context.refresh();

        System.out.println("***********************START****************");
        context.getBean(InstA.class);
        context.getBean(InstB.class);
        context.getBean(InstC.class);
        context.getBean(InstE.class);
        context.getBean(InstF.class);

        System.out.println("***************END*****************");
        context.close();
    }

    private static void classpathXmlApplicationContext() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-bean.xml");

        System.out.println("*********************************************");
        InstD instD = ctx.getBean("xzj", InstD.class);
        System.out.println(instD);
        System.out.println("*********************************");
        ctx.close();
    }
}
