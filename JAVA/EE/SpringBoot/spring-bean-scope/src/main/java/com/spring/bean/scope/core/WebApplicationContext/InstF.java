package com.spring.bean.scope.core.WebApplicationContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InstF {
    public InstF() {
        System.out.println("单例类型的InstF被创建了!!!");
    }
}