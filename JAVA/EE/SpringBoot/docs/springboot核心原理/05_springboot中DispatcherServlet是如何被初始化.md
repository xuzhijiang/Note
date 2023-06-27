# Springboot中DispatcherServlet是如何被初始化的
    
    SpringBoot帮我们自动SpringMVC的时候，自动的注册SpringMVC的前端控制器；DIspatcherServlet；
    
    注意:这里讲的是springboot如何初始化DispatcherServlet的,不是讲springmvc如何初始化DispatcherServlet的
    springboot和springmvc初始化DispatcherServlet是不同的步骤.是两个东西

    核心: 在DispatcherServletAutoConfiguration这个自动化配置类中添加的

![](../pics/Springboot中DispatcherServlet是如何被初始化的01.png)

![](../pics/Springboot中DispatcherServlet是如何被初始化的02.png)

![](../pics/Springboot中DispatcherServlet是如何被初始化的03.png)

![](../pics/Springboot中DispatcherServlet是如何被初始化的04.png)

![](../pics/究竟Springboot如何配置DispatcherServlet02.png)

![](../pics/究竟Springboot如何配置DispatcherServlet03.png)

![](../pics/究竟Springboot如何配置DispatcherServlet04.png)