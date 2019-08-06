package com.springboot.exception.exception;

/**
 * 异常信息多的情况下，可以使用枚举来管理异常信息
 */
public class UserNotFoundException extends RuntimeException {

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
