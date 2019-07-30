package com.di.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

// 确认在运行环境中是否有dbType属性，并且其值为“MONGODB”
public class MongoDBDatabaseTypeCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String enabledDBType = conditionContext.getEnvironment().getProperty("dbType");
        return (enabledDBType != null && enabledDBType.equalsIgnoreCase("MONGODB"));
    }

}