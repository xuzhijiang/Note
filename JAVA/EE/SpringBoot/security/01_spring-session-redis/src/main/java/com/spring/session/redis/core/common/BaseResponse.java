package com.spring.session.redis.core.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable{
    public BaseResponse(){}

    public BaseResponse(Boolean ok){
        this.ok = ok;
    }

    private boolean ok;
    private String message;
    private Object data;
}
