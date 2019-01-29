package com.jinxuliang.beanscope.beans;

//这是一个普通的POJO类
//希望在其初始化时，调用init()方法
//在其销毁时，调用destory()方法
public class InitAndDestoryBean {
    public void init(){
        System.out.println("InitAndDestoryBean's init method.");
    }

    public void destory(){
        System.out.println("InitAndDestoryBean's destory method.");
    }
}
