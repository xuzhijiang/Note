package com.springboot.tuling;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TulingMainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        Calculate calculate = (Calculate) context.getBean("calculate");

        // int retVal = calculate.mod(2, 4);
        calculate.div(2, 6);
    }
}
