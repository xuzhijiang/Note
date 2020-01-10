# BeanPostProcessor作用

    Aware接口是针对某个实现这个接口的Bean定制初始化的过程.
    
    Spring同样可以针对ioc中的所有Bean，或者某些Bean定制初始化过程，只需提供一个实现BeanPostProcessor接口的类即可 
    
    实现 BeanPostProcessor 接口，ioc中所有 bean在 初始化之前都会调用该接口中postProcessBeforeInitialization.
    在这个bean初始化之后,会调用postProcessAfterInitialization方法

    要将实现BeanPostProcessor的Bean像其他Bean一样定义在xml配置文件中:
    <bean class="com.spring.service.CustomerBeanPostProcessor"/>

    >示例: spring-bean-lifecycle