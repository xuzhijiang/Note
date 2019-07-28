package com.springboot.beanscope.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Customer默认是Singleton scope
@Component
public class Customer {

    @Autowired
    private RequestScopeBean requestScopeBean;

    @Autowired
    private SessionScopeBean sessionScopeBean;

    public RequestScopeBean getRequestScopeBean() {
        return requestScopeBean;
    }

    public void setRequestScopeBean(RequestScopeBean requestScopeBean) {
        this.requestScopeBean = requestScopeBean;
    }

    public SessionScopeBean getSessionScopeBean() {
        return sessionScopeBean;
    }

    public void setSessionScopeBean(SessionScopeBean sessionScopeBean) {
        this.sessionScopeBean = sessionScopeBean;
    }
}
