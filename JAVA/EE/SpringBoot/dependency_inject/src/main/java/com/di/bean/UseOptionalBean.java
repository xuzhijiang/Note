package com.di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UseOptionalBean {

    // 如果没有实现IOptional接口的Bean，则@Autowired将会失败
    // 我们可以将它(required)定义为“false”，这样如果找不到适合自动装配的bean，spring框架就不会抛出任何异常。
    @Autowired(required = false)
    private IOptionalBean optionalBean;

    @Override
    public String toString() {
        return "UseOptionalBean{" +
                "optionalBean=" + optionalBean +
                '}';
    }

}