public class ContextLoader {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        // 1. 这步的作用: 保证只能有一个父容器(ROOT WebApplicationContext)
        // 尝试以”org.springframework.web.context.WebApplicationContext.ROOT”为key
        // 从ServletContext中获取根容器(root WebApplicationContext),如果已经存在，则抛出异常。
        // 一个典型的异常场景是在web.xml中配置了多个ContextLoaderListener，那么后初始化的ContextLoaderListener就会抛出异常
        if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
            throw new IllegalStateException(
                    "Cannot initialize context because there is already a root application context present - " +
                            "check whether you have multiple ContextLoader* definitions in your web.xml!");
        }

        Log logger = LogFactory.getLog(ContextLoader.class);
        servletContext.log("Initializing Spring root WebApplicationContext");
        if (logger.isInfoEnabled()) {
            logger.info("Root WebApplicationContext: initialization started");
        }

        long startTime = System.currentTimeMillis();
        try {
            /**
             * 在基于xml整合springmvc的时候,context是空的,所以这里需要创建 根容器对象
             * 但是在基于注解配置springmvc,context不为空
             */
            if (this.context == null) {
                /**
                 * 在基于xml整合springmvc的时候,会走到这里,用于创建空的根容器
                 */
                this.context = createWebApplicationContext(servletContext);
            }

            /**
             * 如果是基于注解配置springmvc,context是从外面传进来的,类型为AnnotationConfigWebApplicationContext,
             * 所以是ConfigurableWebApplicationContext类型,会走到if里面
             *
             * 如果是基于xml的springmvc,context默认是XmlWebApplicationContext,
             * 它也实现了ConfigurableWebApplicationContext接口，因此会进入if代码块
             */
            if (this.context instanceof ConfigurableWebApplicationContext) {
                // 强制转成ConfigurableWebApplicationContext
                ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) this.context;
                // 判断ConfigurableWebApplicationContext这个上下文容器是不是激活了.

                // 不论是基于xml还是基于注解的根容器,此时都还没有加载配置文件中的bean,还是空的IOC容器,
                // spring上下文还没有被刷新，因此isActive返回false，进入if代码块
                if (!cwac.isActive()) { // 没有激活
                    // 若此时ConfigurableWebApplicationContext对象的父容器为空
                    // 则尝试通过loadParentContext获取父亲，并设置到刚刚创建的空的IOC容器中其中.
                    if (cwac.getParent() == null) {
                        // 由于loadParentContext方法目前写死返回null,而且很明显,
                        // 当前的ConfigurableWebApplicationContext已经就是父容器了,没有parent,所以loadParentContext()返回null
                        ApplicationContext parent = loadParentContext(servletContext);
                        cwac.setParent(parent);
                    }

                    // 配置和刷新我们的根容器对象
                    // 根据<context-param>指定的contextConfigLocation，确定xml文件的位置,加载spring bean.
                    configureAndRefreshWebApplicationContext(cwac, servletContext);
                }
            }

            // 把我们的spring上下文(IOC根容器)保存到ServletContext中,方便以后使用
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);

            long elapsedTime = System.currentTimeMillis() - startTime;
            return this.context;
        }
        catch (RuntimeException ex) {
            logger.error("Context initialization failed", ex);
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ex);
            throw ex;
        }
        catch (Error err) {
            logger.error("Context initialization failed", err);
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, err);
            throw err;
        }
    }

    protected WebApplicationContext createWebApplicationContext(ServletContext sc) {
        // a. 首先判断有没有<context-param>元素的<param-name>为contextClass，
        // b. 如果有，则对应的<param-value>值，就是要创建的WebApplicationContext实例类型
        // c. 如果没有指定，则默认的实现类为XmlWebApplicationContext。
        // 这是在spring-web-xxx.jar包中的ContextLoader.properties指定的
        // 注意这个时候，只是创建了WebApplicationContext对象实例，还没有加载xml文件中被bean(也就是当前的IOC容器是空的)
        Class<?> contextClass = this.determineContextClass(sc);
        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException("Custom context class [" + contextClass.getName() + "] is not of type [" + ConfigurableWebApplicationContext.class.getName() + "]");
        } else {
            return (ConfigurableWebApplicationContext)BeanUtils.instantiateClass(contextClass);
        }
    }

    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc) {
        String configLocationParam;
        if (ObjectUtils.identityToString(wac).equals(wac.getId())) {
            // 去我们的ServletContext获取contextId
            configLocationParam = sc.getInitParameter("contextId");
            // 若我们的web.xml中配置了该参数就设置到容器中
            if (configLocationParam != null) {
                wac.setId(configLocationParam);
            } else { // 若没有配置,就是用默认的
                wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX +
                        ObjectUtils.getDisplayString(sc.getContextPath()));
            }
        }
        // 把ServletContext设置到根容器中
        wac.setServletContext(sc);
        /**
         *   <context-param>
         *       <param-name>contextConfigLocation</param-name>
         *       <param-value>/WEB-INF/spring/root-context.xml</param-value>
         *   </context-param>
         */
        configLocationParam = sc.getInitParameter("contextConfigLocation");
        if (configLocationParam != null) {
            // 把配置文件的路径保存到上下文中
            wac.setConfigLocation(configLocationParam);
        }

        ConfigurableEnvironment env = wac.getEnvironment();
        if (env instanceof ConfigurableWebEnvironment) {
            ((ConfigurableWebEnvironment)env).initPropertySources(sc, (ServletConfig)null);
        }
        // 定制我们的spring上下文对象
        this.customizeContext(sc, wac);
        // 会触发IOC根容器的 刷新
        wac.refresh();
    }

}
