package com.spring.bean.scope.core.WebApplicationContext;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

/**
 * 在application.properties中配置session timeout超时时间为5秒,所以
 * 每过5秒,当从IOC中getBean的时候就会创建新的InstD实例,
 * 也就是每个InstD实例 会在5秒内失效
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InstD {
    public InstD() {
        System.out.println("DataSessionScope Constructor Called at "+ LocalDateTime.now());
    }
}
