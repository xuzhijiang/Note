package com.legacy.springmvc.security.config.core;

import com.legacy.springmvc.security.config.LoginSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// 在项目中没有使用SpringMVC的情况下,要传递LoginSecurityConfig.class到父类,从而确保我们的
// 配置被加载.由于本例中使用了SpringMVC,我们的项目中已经存在了一个MvcWebApplicationInitializer,
// 这个时候我们要通过MvcWebApplicationInitializer的getRootConfigClasses()方法把LoginSecurityConfig
// 配置的相关bean注册到IoC容器中.然后再通过AbstractSecurityWebApplicationInitializer的onStartUp()
// 完成Filter的注册.
/*
public class SecurityWebApplicationInitializerWithoutSpringMVC extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebApplicationInitializerWithoutSpringMVC() {
        super(LoginSecurityConfig.class);
    }
}
*/