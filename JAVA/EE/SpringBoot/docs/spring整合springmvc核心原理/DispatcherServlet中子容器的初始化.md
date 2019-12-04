# DispatcherServlet是如何调用到initWebApplicationContext()的?

![](../pics/DispatcherServlet.png)

1. Servlet的init(ServletConfig)会调用到GenericServlet的init()
2. HttpServletBean继承了GenericServlet,HttpServletBean的init()调用到了initServletBean()
3. FrameworkServlet重写了initServletBean(),initServletBean()中会调用到initWebApplicationContext()

# DispatcherServlet的initWebApplicationContext()剖析

![](../pics/DispatcherServle中initWebApplicationContext详解.png)

    见当前目录下的: FrameworkServlet.java

![](../pics/刷新完子容器之后,controller就会被创建出来.png)    