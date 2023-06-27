package com.spring.condition.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

public class MongoDBCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        return "MONGODB".equalsIgnoreCase(conditionContext.getEnvironment().getProperty("dbType", "MONGODB"));
    }
}
