package com.springboot.beanscope.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 还可以实现InitializingBean,DisposableBean 这两个接口，也是在初始化以及销毁阶段调用
 */
@Component
public class InitializingAndDisposableBean implements InitializingBean, DisposableBean {

    // 这些方法是回调方法，类似于servlet侦听器实现
    @Override
    public void destroy() throws Exception {
        System.out.println("InitializingAndDisposableBean destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet......");
    }
}
