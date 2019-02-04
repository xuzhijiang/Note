package com.jinxuliang.exceptiondemo.domain;

//自定义异常
public class UserNotFoundException
        extends RuntimeException {

    private int id;

    public UserNotFoundException(int id,String message){
        super(message);
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
