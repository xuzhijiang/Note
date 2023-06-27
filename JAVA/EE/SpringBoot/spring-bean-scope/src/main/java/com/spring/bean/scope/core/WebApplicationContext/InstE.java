package com.spring.bean.scope.core.WebApplicationContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InstE {
    public InstE() {
        System.out.println("prototype类型的InstE被创建了!!!");
    }
}