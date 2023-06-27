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
        logger.info("===>>>>>要调用的方法的名字====> " + methodName);
        if(exclude.contains(methodName)){
            return invocation.proceed();
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        logger.info("执行的方法是: {}, 耗时{}MS", methodName, (end - start));
        return result;
    }

}
