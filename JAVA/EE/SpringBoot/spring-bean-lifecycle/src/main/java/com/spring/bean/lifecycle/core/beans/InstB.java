package com.spring.bean.lifecycle.core.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class InstB implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(getClass() + "\t afterPropertiesSet......");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(getClass() + "\t destroy");
    }
}
