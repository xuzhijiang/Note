package com.springboot.advanced.beans.aware;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

/**
 * 通过ApplicationContext获取bean 的时候才会创建,否则bean 不会被创建
 */
@Service
public class BeanNameAwareService implements BeanNameAware {

    private String beanName;

    //此Bean能够知道在IoC容器中自己的名字是哪个
    @Override
    public void setBeanName(String s) {
        beanName=s;
    }

    @Override
    public String toString() {
        return "BeanNameAwareService{" +
                "beanName='" + beanName + '\'' +
                '}';
    }
}
