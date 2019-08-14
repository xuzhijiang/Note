package com.springboot.customAop.factory;

import com.springboot.customAop.manager.TransactionManager;
import com.springboot.customAop.annotation.MyTransactional;
import com.springboot.customAop.util.ConnectionUtils;

public class BeanFactory {

    public Object getBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException {
        //得到目标对象
        Object target = beanClazz.newInstance();
        //得到目标类上的@MyTransactional注解
        MyTransactional annotation = beanClazz.getAnnotation(MyTransactional.class);
        //如果打了@MyTransactional注解，返回代理对象，否则返回目标对象
        if (annotation != null) {
            ProxyBeanFactory proxyFactoryBean = new ProxyBeanFactory();
            TransactionManager txManager = new TransactionManager(new ConnectionUtils());
            //装配通知对象和目标对象
            proxyFactoryBean.setTarget(target);
            proxyFactoryBean.setTxManager(txManager);
            Object proxyBean = proxyFactoryBean.getProxy();
            //返回代理对象
            return proxyBean;
        }
        return target;
    }

    /**
     * 获取代理对象
     */
    public Object getBean(String beanName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 得到目标对象的Class对象
        Class<?> beanClazz = Class.forName(beanName);
        return getBean(beanClazz);
    }
}
