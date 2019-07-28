package com.springboot.beanscope.bean;

public class TestRequestBean {

    public TestRequestBean(String scope) {
        System.out.println("Bean created!! scope: " + scope);
    }
}
