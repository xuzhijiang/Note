package com.di.bean;

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


