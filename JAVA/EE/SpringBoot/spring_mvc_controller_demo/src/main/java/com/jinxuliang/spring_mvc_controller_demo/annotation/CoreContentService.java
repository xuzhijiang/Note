package com.jinxuliang.spring_mvc_controller_demo.annotation;

public class CoreContentService implements ContentService{

    @Override
    public void doSomething() {
        System.out.println("do some core things");
    }

}
