protected WebApplicationContext initWebApplicationContext() {
    // 从ServletContext中获取spring root上下文对象(根容器),为啥可以获取?
    // 因为spring root上下文对象在创建完成后存放到了ServletContext中
    WebApplicationContext rootContext =
    WebApplicationContextUtils.getWebApplicationContext(getServletContext());

    WebApplicationContext wac = null;
    // 基于注解的springmvc中,this.webApplicationContext是在创建DispatcherServlet的时候
    // 存放进来的子容器上下文对象(springmvc web的上下文对象)
   //基于xml的springmvc中,因为DispatcherServlet是通过反射创建的
  // 此时webApplicationContext为null，因此不会进入if
    if (this.webApplicationContext != null) {
        wac = this.webApplicationContext;
        if (wac instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
            if (!cwac.isActive()) { // 判断是否激活
                // 父子关联: 把父容器和子容器关联
                if (cwac.getParent() == null) {
                // 给ServletApplicationContext设置parent为root容器
                cwac.setParent(rootContext);
                }
                // 子容器的刷新(ServletApplicationContext的刷新)
                configureAndRefreshWebApplicationContext(cwac);
            }
        }
    }

  // 此时尝试根据FrameServlet的contextAttribute字段的值，
  // 从ServletContext中获取Servlet WebApplicationContext实例，
  // 在我们的案例中，contextAttribute值为空，因此这一步过后，wac依然为null
    if (wac == null) {
        wac = findWebApplicationContext();
    }
    // 基于xml的springmvc中,开始真正的创建Servlet WebApplicationContext，并将rootContext设置为parent
    if (wac == null) {
        wac = createWebApplicationContext(rootContext);
    }

    if (!this.refreshEventReceived) {
        synchronized (this.onRefreshMonitor) {
        onRefresh(wac);
        }
    }

    if (this.publishContext) {
        String attrName = getServletContextAttributeName();
        getServletContext().setAttribute(attrName, wac);
    }

    return wac;
}