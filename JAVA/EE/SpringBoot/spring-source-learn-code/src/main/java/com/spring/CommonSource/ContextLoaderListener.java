package com.spring.CommonSource;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// 对应的项目实例为: traditional-project-structure-spring-mvc
// 对应的说明文档: Spring和SpringMVC父子容器的概念.md

/**
 * ContextLoaderListener用于创建ROOT WebApplicationContext，
 * 其实现了ServletContextListener接口的contextInitialized和contextDestroyed方法，在web应用启动和停止时，
 * web容器(如tomcat)会负责回调这两个方法。而创建Root WebApplicationContext就是在contextInitialized中完成的
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {

    WebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initWebApplicationContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        //1、保证只能有一个ROOT WebApplicationContext
        // 尝试以”org.springframework.web.context.WebApplicationContext.ROOT”为key
        // 从ServletContext中查找WebApplicationContext实例如果已经存在，则抛出异常。
        // 一个典型的异常场景是在web.xml中配置了多个ContextLoaderListener，那么后初始化的ContextLoaderListener就会抛出异常
        if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
            throw new IllegalStateException(
                    "Cannot initialize context because there is already a root application context present - " +
                            "check whether you have multiple ContextLoader* definitions in your web.xml!");
        }

        //2、打印日志，注意日志中的提示内容："Initializing Spring root WebApplicationContext”
        // 这验证了我们之前的说法，ContextLoaderListener创建的是root WebApplicationContext
        //Log logger = LogFactory.getLog(ContextLoader.class);
        servletContext.log("Initializing Spring root WebApplicationContext");
        //if (logger.isInfoEnabled()) {
        //    logger.info("Root WebApplicationContext: initialization started");
        //}

        long startTime = System.currentTimeMillis();
        try {
            if (this.context == null) {
                // 3. 创建WebApplicationContext实现类的实例。
                // 其内部首先会确定WebApplicationContext实例类型。
                // a. 首先判断有没有<context-param>元素的<param-name>值为contextClass，
                // b. 如果有，则对应的<param-value>值，就是要创建的WebApplicationContext实例类型
                // c. 如果没有指定，则默认的实现类为XmlWebApplicationContext。
                // 这是在spring-web-xxx.jar包中的ContextLoader.properties指定的
                // 注意这个时候，只是创建了WebApplicationContext对象实例，还没有加载对应的spring配置文件
                this.context = createWebApplicationContext(servletContext);
            }

            //4. XmlWebApplicationContext实现了ConfigurableWebApplicationContext接口，因此会进入if代码块
            if (this.context instanceof ConfigurableWebApplicationContext) {
                ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) this.context;
                // a. 由于WebApplicationContext对象实例还没有加载对应配置文件，
                // spring上下文还没有被刷新，因此isActive返回false，进入if代码块
                if (!cwac.isActive()) {
                    //b. 当前ROOT WebApplicationContext的父context为null，
                    // 则尝试通过loadParentContext方法获取父ApplicationContext，并设置到其中
                    //c. 由于loadParentContext方法目前写死返回null，因此可以忽略4.2这个步骤。
                    if (cwac.getParent() == null) {
                        ApplicationContext parent = loadParentContext(servletContext);
                        cwac.setParent(parent);
                    }
                    //d. 加载配置spring文件。根据<context-param>指定的contextConfigLocation，确定配置文件的位置。
                    configureAndRefreshWebApplicationContext(cwac, servletContext);
                }
            }

            // 5、将创建的WebApplicationContext实例
            // 以”org.springframework.web.context.WebApplicationContext.ROOT”为key
            // 设置到ServletContext中
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);

            long elapsedTime = System.currentTimeMillis() - startTime;
            return this.context;
        }
        catch (RuntimeException ex) {
            //logger.error("Context initialization failed", ex);
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ex);
            throw ex;
        }
        catch (Error err) {
            //logger.error("Context initialization failed", err);
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, err);
            throw err;
        }
    }
}
