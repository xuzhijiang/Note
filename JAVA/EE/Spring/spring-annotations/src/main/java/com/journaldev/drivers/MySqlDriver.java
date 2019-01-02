package com.journaldev.drivers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

// Spring Component Classes

// 请注意使用@Component注释来表示spring框架 将此类视为Component。
// 我们还使用@PropertySource和@Value注解，Spring将在"运行时"使用这些注释 并从指定的"属性文件"中设置这些变量值。
// 下面是mysqldatabase.properties文件中声明的属性:

// databaseName=school
// disableStatementPooling=true


// @Component：表示带此注释的类是“组件”。当使用"基于注释的配置"和"类路径扫描"时，这些类被视为自动检测的候选者。
@Component
@PropertySource("classpath:mysqldatabase.properties")
public class MySqlDriver implements DataBaseDriver {

    @Value("${databaseName}")
    private String databaseName;
    @Value("${disableStatementPooling}")
    private String disableStatementPooling;



    public String getInfo() {
        return "[ Driver: mySql" +
                ", databaseName: " + databaseName +
                ", disableStatementPooling: " + disableStatementPooling +
                " ]";
    }
}
