package com.springboot.beanscope.controller;

import com.springboot.beanscope.bean.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScopeTestController {

    @Autowired
    private Customer customer;

    // 1. http://localhost:8080/requestScope
    @RequestMapping("/requestScope")
    public String getRequestScopeBeanName() {
        return customer.getRequestScopeBean().getName();
    }

    // 2. http://localhost:8080/sessionScope
    // 3. http://localhost:8080/sessionScopeUpdate，
    // 4. 再次转到http://localhost:8080/sessionScope
    @RequestMapping("/sessionScopeUpdate")
    public String updateSessionScopeBeanName() {
        customer.getSessionScopeBean().setName("Session Scope Updated");
        return customer.getSessionScopeBean().getName();
    }

    @RequestMapping("/sessionScope")
    public String getSessionScopeBeanName() {
        return customer.getSessionScopeBean().getName();
    }
}

// 现在等待1分钟，以便我们的session scoped other(会话范围的bean)失效了。
// 然后再次转到http://localhost:8080/nameSS，您应该看到原始输出
// (DataSessionScope Constructor Called at XXX）。此外，您应该检查控制台消息，
// 以便容器再次创建DataSessionScope。