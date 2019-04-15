package com.jinxuliang.spring_mvc_controller_demo.annotation;

public class SimpleContentService implements ContentService{
    @Override
    public void doSomething() {
        System.out.println("do some simple things");
    }
}
