package com.spring.bean.lifecycle.core.beans;

public class InstC {
    public void init(){
        System.out.println(getClass() + "\t init");
    }

    public void destroy(){
        System.out.println(getClass() + "\t destroy");
    }
}
