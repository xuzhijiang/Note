package com.spring.bean.scope.core.NonWebApplicationContext;

import org.springframework.stereotype.Component;

public class InstE {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
