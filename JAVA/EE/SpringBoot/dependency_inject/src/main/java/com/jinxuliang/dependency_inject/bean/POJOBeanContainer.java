package com.jinxuliang.dependency_inject.bean;

//这是一个普通的Java类，不包容任何与依赖注入
//相关的代码
public class POJOBeanContainer {

    private POJOBean pojoBean;

    public POJOBeanContainer(POJOBean pojoBean) {
        this.pojoBean = pojoBean;
    }

    @Override
    public String toString() {
        return "POJOBeanContainer{" +
                "pojoBean=" + pojoBean +
                '}';
    }
}


