package com.jinxuliang.springframeworkadvance.beans.aware;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

//此Bean能够知道在IoC容器中自己的名字是哪个
@Service
public class AwareService implements BeanNameAware {
    private String beanName;
    @Override
    public void setBeanName(String s) {
        beanName=s;
    }

    @Override
    public String toString() {
        return "AwareService{" +
                "beanName='" + beanName + '\'' +
                '}';
    }
}
