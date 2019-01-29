package com.jinxuliang.dependency_inject.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UseOptionalBean {
    //如果没有实现IOptional接口的Bean，则@Autowired将会失败
    @Autowired(required = false)
    private IOptionalBean optionalBean;

    @Override
    public String toString() {
        return "UseOptionalBean{" +
                "optionalBean=" + optionalBean +
                '}';
    }
}
