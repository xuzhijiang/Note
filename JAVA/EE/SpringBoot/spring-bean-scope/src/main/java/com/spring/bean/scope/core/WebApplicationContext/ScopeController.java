package com.spring.bean.scope.core.WebApplicationContext;

import com.spring.bean.scope.core.WebApplicationContext.InstA;
import com.spring.bean.scope.core.WebApplicationContext.InstB;
import com.spring.bean.scope.core.WebApplicationContext.InstC;
import com.spring.bean.scope.core.WebApplicationContext.InstD;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ScopeController implements ApplicationContextAware {

    @Autowired
    private InstA instA;

    @Autowired
    private InstB instB;

    @Autowired
    private InstC instC;

    @Autowired
    private InstD instD;

    private static ApplicationContext ioc;

    @GetMapping("/A")
    public String method1() {
        System.out.println("InstA: " + instA);
        return instA.toString();
    }

    // 调用多次这个method2,只会有一个instB
    @GetMapping("/B")
    public String method2() {
        System.out.println("InstB: " + instB);
        System.out.println(ScopeController.this);
        return instB.toString();
    }

    // 每次调用ioc容器的getBean()方法,就会生成一个instB实例
    @GetMapping("/BB")
    public String method22() {
        InstB obj = ioc.getBean(InstB.class);
        System.out.println("InstB: " + obj);
        System.out.println(ScopeController.this);
        return obj.toString();
    }

    @GetMapping("/C")
    public String method3() {
        System.out.println("InstC: " + instC);
        return instC.toString();
    }

    @GetMapping("/D")
    public String method4() {
        System.out.println("InstD: " + instD);
        return instD.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ioc = applicationContext;
    }
}