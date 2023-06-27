package com.springboot.cors.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpSupportAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpSupportAdvice() {
        // 一旦发现参数中有callback,就把返回内容以 "callback(返回内容)" 这样的形式返回给前端
        super("callback");
    }
}
