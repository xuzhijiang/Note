package com.legacy.springmvc.jdbc.routing;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoutingAdvice implements MethodInterceptor {

    private Map<String, String> packageDataSourceKeyMap = null;

    private static final Map<Method, String> METHOD_DATASOURCE_KEY_CACHE = new ConcurrentHashMap<Method, String>();

    public RoutingAdvice(Map<String, String> packageDataSourceKeyMap) {
        this.packageDataSourceKeyMap = packageDataSourceKeyMap;
    }


    public Object invoke(MethodInvocation invocation) throws Throwable {
        if(RoutingContext.getCurrentDataSource() != null){
            return invocation.proceed();
        }

        String targetDataSource = determineDataSourceKeyWithCache(invocation);

        try{
            RoutingContext.setDatasource(targetDataSource);
            return invocation.proceed();
        } finally {
            RoutingContext.clear();
        }
    }

    private String determineDataSourceKeyWithCache(MethodInvocation invocation){
        String targetDataSource = null;
        Method method = invocation.getMethod();

        if(METHOD_DATASOURCE_KEY_CACHE.containsKey(method)){
            targetDataSource = METHOD_DATASOURCE_KEY_CACHE.get(method);
        }

        // method level
        if(method.getAnnotation(Routing.class) != null){
            targetDataSource = method.getAnnotation(Routing.class).value();
        }

        // class level
        if(targetDataSource == null && method.getDeclaringClass().getAnnotation(Routing.class) != null) {
            targetDataSource = method.getDeclaringClass().getAnnotation(Routing.class).value();
        }

        // package level
        if(targetDataSource == null && packageDataSourceKeyMap != null) {
            // exact mapping, tackle different package has same prefix. eg: xx.zebra, xx.tiger
            String packageName = method.getDeclaringClass().getPackage().getName();
            targetDataSource = packageDataSourceKeyMap.get(packageName);

            // line lookup mapping
            if(targetDataSource == null){
                for(Map.Entry<String, String> entry : packageDataSourceKeyMap.entrySet()){
                    if(packageName.startsWith(entry.getKey())){
                        targetDataSource = entry.getValue();
                        break;
                    }
                }
            }

        }

        if(targetDataSource != null){
            METHOD_DATASOURCE_KEY_CACHE.put(method, targetDataSource);
        }

        return targetDataSource;
    }

}
