package com.springboot.beanscope;

import com.springboot.beanscope.bean.*;
import com.springboot.beanscope.config.BeanConfig;
import com.springboot.beanscope.config.CompoundBeanConfig;
import com.springboot.beanscope.service.AutowireEmployeeService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StandaloneScopeTestApplication {

    public static void main(String[] args) {
        // 对于独立应用程序，您需要在应用程序中的某个位置初始化Spring IoC容器，然后才能使用它来获取spring bean
        // 1. 我们初始化AnnotationConfigApplicationContext上下文(即Spring IoC容器)
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // 注册配置类
        ctx.register(BeanConfig.class);
        // 要调用refresh,否则当我们尝试从上下文中获取任何bean时，我们将得到以下错误:has not been refreshed yet
        ctx.refresh();

        // 或者直接传入要注册的类,在AnnotationConfigApplicationContext构造函数内部相当于调用了register和refresh
        // AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);

        // test singleton scope
        TestSingletonBean singletonBean1 = ctx.getBean(TestSingletonBean.class);
        TestSingletonBean singletonBean2 = ctx.getBean(TestSingletonBean.class);
        System.out.println("singleton bean: " + (singletonBean1 == singletonBean2));

        // test prototype scope
        TestPrototypeBean prototypeBean1 = ctx.getBean(TestPrototypeBean.class);
        TestPrototypeBean prototypeBean2 = ctx.getBean(TestPrototypeBean.class);
        System.out.println("prototype bean: " + (prototypeBean1 == prototypeBean2));

        // test request scope
        // 会抛出: No Scope registered for scope name 'request'(没有为范围名称“请求”注册的范围)
        try {
            TestRequestBean requestBean1 = ctx.getBean(TestRequestBean.class);
            TestRequestBean requestBean2 = ctx.getBean(TestRequestBean.class);
            System.out.println("request bean: " + (requestBean1 == requestBean2));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        // 调用了close才会调用bean的PreDestroy对应的方法.
        ctx.close();
    }

    /**
     * Prototype Scope模式，每次请求都实例化一个对象。
     */
    @Test
    public void testPrototypeBean() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Prototype.class);
        ctx.refresh();

        System.out.println("-------------testPrototypeBean-------------");
        Prototype prototype1 = ctx.getBean(Prototype.class);
        Prototype prototype2 = ctx.getBean(Prototype.class);
        System.out.println("prototype1 == prototype2: " + (prototype1 == prototype2)); //输出：false

        ctx.close();
    }

    /**
     * 测试singleton 单例bean
     */
    @Test
    public void testSingletonBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Singleton.class);
        context.refresh();

        System.out.println("-------------testSingletonBean-------------");
        Singleton singleton1 = context.getBean(Singleton.class);
        Singleton singleton2 = context.getBean(Singleton.class);
        System.out.println("singleton1 == singleton2: " + (singleton1 == singleton2)); //输出：true

        context.close();
    }

    /**
     * test PostConstructor and PreDestroy
     */
    @Test
    public void testPostConstructAndPreDestroy() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanConfig.class, InitAndDestoryAnnotationBean.class);
        context.refresh();

        context.getBean(InitAndDestoryBean.class);
        context.getBean(InitAndDestoryAnnotationBean.class);

        context.close();
    }

    @Test
    public void testSingletonAndPrototypeScope() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CompoundBeanConfig.class);
        context.refresh();

        Employee employee1 = context.getBean(Employee.class);
        Employee employee2 = context.getBean(Employee.class);

        System.out.println("employee1: " + employee1.hashCode());
        System.out.println("employee2: " + employee2.hashCode());

        context.close();
    }

    @Test
    public void testBeanPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CompoundBeanConfig.class);
        context.refresh();
    }

    @Test
    public void testAutowire() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CompoundBeanConfig.class);
        context.register(AutowireEmployeeService.class);
        context.refresh();

        AutowireEmployeeService autowireEmployeeService = context.getBean(AutowireEmployeeService.class);
        System.out.println(autowireEmployeeService.getEmployee().hashCode());
    }
}
