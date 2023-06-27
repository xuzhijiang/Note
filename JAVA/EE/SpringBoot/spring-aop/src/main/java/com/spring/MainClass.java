package com.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {

    @Test
    public void aspectA() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        /**
         * 重要:
         */
        // 默认使用jdk的动态代理: 所以代理对象和目标对象唯一可以产生关联的就是拥有相同的接口.
        // 所以这里一定要用接口来获取aop的代理对象,意思就是我要从ioc容器中获取一个实现Calculate接口的组件
        // aop的代理对象正好实现了这个接口,所以正好返回.
        Calculate calculate = context.getBean(Calculate.class);

        // 一个重要的区别：JDK动态代理无法转换为原始目标类，因为动态代理类只是实现了与目标类相同的接口.
        // 因为这里默认使用jdk的代理,所以会抛异常
        // java.lang.ClassCastException: com.sun.proxy.$Proxy27 cannot be cast to com.spring.ServiceA
        // ServiceA s = (ServiceA) calculate;

        System.out.println("calculate: " + calculate);// com.spring.ServiceA@7d9d0818
        // AOP的底层就是动态代理,ioc容器中保存的组件calculate是一个代理对象: com.sun.proxy.$Proxy23
        // 它的类型就是肯定不是com.spring.ServiceA,它只是和com.spring.ServiceA一样都实现了相同的Calculate接口
        System.out.println("calculate的类型: " + calculate.getClass()); // class com.sun.proxy.$Proxy23 是一个代理对象.

        // com.spring.ServiceA这个类实现了Calculate接口
        // 上面的代理对象calculate (com.sun.proxy.$Proxy23)也实现了Calculate接口
        // aop功能只是把com.spring.ServiceA的代理对象com.sun.proxy.$Proxy23放到了ioc容器中,
        // 并没有把com.spring.ServiceA这个类型的对象放到ioc容器中, (这里是重点****************************)
        // 所以这里不可以直接通过ServiceA.class来获取com.spring.ServiceA类型的对象.
        // 会提示No qualifying bean of type 'com.spring.ServiceA' available
        // 所以你不管怎么从ioc容器中寻找,都找的是aop的代理对象.(这里是重点****************************)
        // ServiceA serviceA = context.getBean(ServiceA.class);
        // System.out.println("serviceA: " + serviceA);

        System.out.println("========================正常执行测试=====================================");
        calculate.div(2, 2);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("========================异常执行测试=====================================");
        calculate.div(2, 0);
    }

    @Test
    public void aspectB() {
        /**
         * 由于ServiceB没有实现任何接口,所以aop不可以使用jdk的动态代理来生成代理对象了.
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        // 因为这里使用cblib的代理,所以从ioc中获取的代理对象是ServiceB的子类,所以可以转换为用ServiceB来接受
        ServiceB serviceB = context.getBean(ServiceB.class);
        System.out.println("serviceB: " + serviceB); // serviceB: com.spring.ServiceB@1807e3f6
        // serviceB的类型: class com.spring.ServiceB$$EnhancerBySpringCGLIB$$de9757fb ($$后面的意思就是内部类)
        // 所以即使你没有接口,返回的依然是代理对象,不过是使用cglib帮你生成的代理对象.
        System.out.println("serviceB的类型: " + serviceB.getClass());
        serviceB.add();
    }
}
