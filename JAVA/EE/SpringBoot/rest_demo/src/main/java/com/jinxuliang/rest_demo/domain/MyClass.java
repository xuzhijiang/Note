package com.jinxuliang.rest_demo.domain;

import javax.validation.constraints.NotBlank;
import java.util.Date;

// 编写一个普通的JavaBean，它将被用来展示RESTful Service的编写方法。
//一个用于测试的类
public class MyClass {

    private int id;

    private String info;
    private Date date=new Date();
    @NotBlank
    private String secretInfo;

    public String getSecretInfo() {
        return secretInfo;
    }

    public void setSecretInfo(String secretInfo) {
        this.secretInfo = secretInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public MyClass(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public MyClass(){

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }
}
