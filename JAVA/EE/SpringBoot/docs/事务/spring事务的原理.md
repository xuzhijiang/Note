# spring事务的底层实现原理?

    Spring的事务控制依赖于Spring的AOP切面编程,来实现共享连接Connection. 
    aop是基于动态代理实现的