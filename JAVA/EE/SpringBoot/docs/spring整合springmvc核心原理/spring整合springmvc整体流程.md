    
    写一个类,继承AbstractAnnotationConfigDispatcherServletInitializer
    
    在AbstractAnnotationConfigDispatcherServletInitializer的onStartup()中的步骤:
    
    第一步: 会创建spring root上下文(根IOC容器)
    第二步: 创建ContextLoaderListener
    第三步: 在ContextLoaderListener回调中会扫描xml中的service和repository(dao)这些bean
    第四步: 创建子容器(spring web上下文-servletApplicationContext)
    第五步: 创建DispatcherServlet,并且注册DispatcherServlet到ServletContext中
    第六步: 在DispatcherServlet的生命周期方法中将xml中的controller扫描到子容器中
    
    注意: 以上这不是springmvc的原理,这个是spring整合springmvc的原理
    springmvc的原理要看DispatcherServlet这个类