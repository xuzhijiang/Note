package com.journaldev.service;

import com.journaldev.drivers.DataBaseDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

// @Service：表示带此注释的类是“服务(Service)”。指示Spring框架将其视为Service服务类。
// 此注释用作@Component的特化，允许"实现类(即带此注解的类)"通过类路径扫描自动检测。
@Service
public class UserService {


    // 然后我们使用@Autowired和@Qualifier(“oracleDriver”）注释
    // 来告诉spring框架将名为rrr的bean注入类属性dataBaseDriver。
    // 请注意，我们还没有创建这个spring bean(名为oracleDriver的bean)。

    // Spring @Qualifier注释与Autowired结合使用，以避免在我们为同一类型配置两个bean时出现混淆。
    @Autowired
    @Qualifier("rrr")
    private DataBaseDriver dataBaseDriver;

    public String getDriverInfo(){
        return dataBaseDriver.getInfo();
    }
    
}