package com.di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 可以使用@Autowired构建组合对象
@Component
public class MyContainerBean {

    //直接附加在字段上
    @Autowired
    MyAnnotationBean annotationBean;

    private POJOBean pojoBean=null;

    // 把@Autowired附加在字段的setter方法上,会自动注入
    @Autowired
    public void setPojoBean(POJOBean pojoBean) {
        this.pojoBean = pojoBean;
    }

    @Override
    public String toString() {
        return "MyContainerBean{" +
                "annotationBean=" + annotationBean +
                ", pojoBean=" + pojoBean +
                '}';
    }
}

