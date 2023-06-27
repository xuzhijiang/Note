package com.spring.ioc.core.BeanDefinition.ApplicationContext;

import org.springframework.stereotype.Component;

@Component
public class InstA {

    // 注入模型,默认是0
//    @Autowired
    private InstB instB;

    public InstB getInstB() {
        return instB;
    }

    // 如果注入模型为AUTOWIRE_BY_TYPE和AUTOWIRE_BY_NAME,那么spring会都会调用setInstB方法
    // 这个方法需要的参数InstB是从哪里来的?怎么找到的? 答: 是从ioc容器中拿到的.
    // 如果注入模型为: AUTOWIRE_BY_TYPE,则根据类型为InstB来寻找
    // 如果注入模型为: AUTOWIRE_BY_NAME,则根据bean的名字为instB来寻找.
    // AUTOWIRE_BY_TYPE容错率更高
    public void setInstB(InstB instB) {
        this.instB = instB;
    }

    // 正常情况InstA是非懒加载,也就是spring IoC容器会初始化InstA,会打印这句话
    public InstA() {
        System.out.println("InstA的构造方法.......");
    }

    @Override
    public String toString() {
        return "InstA{" +
                "instB=" + instB +
                '}';
    }

}
