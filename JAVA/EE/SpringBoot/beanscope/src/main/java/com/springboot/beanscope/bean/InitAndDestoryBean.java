package com.springboot.beanscope.bean;

// 这是一个普通的POJO类，希望在其初始化时，调用init()方法
// 在其销毁时，调用destory()方法
public class InitAndDestoryBean {
    public void init(){
        System.out.println("InitAndDestoryBean's init method.");
    }

    public void destroy(){
        System.out.println("InitAndDestoryBean's destory method.");
    }
}

/**
 * 如果是原始的基于 XML 也是可以使用:
 *
 * ```xml
 * <other class="com.springboot.beanscope.other.InitAndDestoryBean" init-method="init" destroy-method="destroy">
 * </other>
 * ```
 *
 * 来达到相同的效果.
 */