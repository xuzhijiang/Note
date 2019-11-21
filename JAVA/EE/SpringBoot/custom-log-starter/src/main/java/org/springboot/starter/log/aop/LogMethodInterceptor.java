package org.springboot.starter.log.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

public class LogMethodInterceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(LogMethodInterceptor.class);

    private List<String> exclude;

    public LogMethodInterceptor(String[] exclude){
        this.exclude = Arrays.asList(exclude);
    }

    /**
     * 定义方法拦截的规则
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        logger.info("custom starter method name: " + methodName);
        if(exclude.contains(methodName)){
            return invocation.proceed();
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        logger.info("method({}), cost({})", methodName, (end - start));
        return result;
    }

}
