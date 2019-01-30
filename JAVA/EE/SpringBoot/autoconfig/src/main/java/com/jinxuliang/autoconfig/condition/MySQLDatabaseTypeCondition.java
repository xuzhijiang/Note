package com.jinxuliang.autoconfig.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

// 类似地，定义MySQLDatabaseTypeCondition类，用于检测 dbType==MYSQL。

// 确认在运行环境中是否有dbType属性，并且其值为“MYSQL”
@Component
public class MySQLDatabaseTypeCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext,
                           AnnotatedTypeMetadata metadata)  {
        String enabledDBType = System.getProperty("dbType");

        enabledDBType=conditionContext.getEnvironment().getProperty("dbType");
        return (enabledDBType != null &&
                enabledDBType.equalsIgnoreCase("MYSQL"));
    }
}

