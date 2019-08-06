package com.spring.SourcePracticeDemo.service.impl;

import com.spring.SourcePracticeDemo.service.ContentService;

public class SimpleContentService implements ContentService {
    @Override
    public void doSomething() {
        System.out.println("do some simple things");
    }
}
