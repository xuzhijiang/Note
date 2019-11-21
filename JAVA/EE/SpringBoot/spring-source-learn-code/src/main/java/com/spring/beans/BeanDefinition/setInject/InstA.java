package com.spring.beans.BeanDefinition.setInject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstA {

    //@Autowired
    private InstB instB;

    public InstB getInstB() {
        return instB;
    }

    public void setInstB(InstB instB) {
        this.instB = instB;
    }

    // 正常情况InstA是非懒加载,也就是spring IoC容器会初始化InstA,会打印这句话
    public InstA() {
        System.out.println("InstA的构造方法.......");
    }

    @Override
    public String toString() {
        return "InstA{" +
                "instB=" + instB +
                '}';
    }
}
