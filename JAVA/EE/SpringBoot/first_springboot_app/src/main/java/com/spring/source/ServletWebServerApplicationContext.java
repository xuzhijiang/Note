package com.spring.source;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializerBeans;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.context.WebApplicationContextServletContextAwareProcessor;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.ServletContextAwareProcessor;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * ServletWebServerApplicationContext有2个子类，XmlServletWebServerApplicationContext
 * 和AnnotationConfigServletWebServerApplicationContext。一个是基于注释，一个是基于Xml.
 *
 * web程序对应的Spring容器是AnnotationConfigServletWebServerApplicationContext，
 * 继承自ServletWebServerApplicationContext。在ServletWebServerApplicationContext的
 * onRefresh方法中会去创建内置Servlet容器：
 */
public class ServletWebServerApplicationContext extends GenericWebApplicationContext
        implements ConfigurableWebServerApplicationContext {

    @Override
    public final void refresh() throws BeansException, IllegalStateException {
        try {
            super.refresh();
        }
        catch (RuntimeException ex) {
            stopAndReleaseWebServer();
            throw ex;
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        try {
            // 创建内置Servlet容器
            createWebServer();
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();
        //Servlet容器创建完毕之后在finishRefresh方法中会去启动：
        WebServer webServer = startWebServer();
        if (webServer != null) {
            // 发布ServletWebServerInitializedEvent事件
            //publishEvent(new ServletWebServerInitializedEvent(webServer, this));
        }
    }

    private WebServer startWebServer() {
        // 先得到在onRefresh方法中构造的Servlet容器webServer
        WebServer webServer = this.webServer;
        if (webServer != null) {
            // 启动
            webServer.start();
        }
        return webServer;
    }

    private void createWebServer() {
        WebServer webServer = this.webServer;
        ServletContext servletContext = getServletContext();
        // 内置Servlet容器和ServletContext都还没初始化的时候执行
        if (webServer == null && servletContext == null) {
            // 从Spring容器中获取ServletWebServerFactory，
            // 如果ServletWebServerFactory不存在或者有多个的话会抛出异常中止程序
            ServletWebServerFactory factory = getWebServerFactory();
            // 获取Servlet初始化器并创建Servlet容器，依次调用Servlet初始化器中的onStartup方法
            this.webServer = factory.getWebServer(getSelfInitializer());
        }
        // 内置Servlet容器已经初始化但是ServletContext还没初始化的时候执行
        else if (servletContext != null) {
            try {
                // 对已经存在的Servlet容器依次调用Servlet初始化器中的onStartup方法
                getSelfInitializer().onStartup(servletContext);
            }
            catch (ServletException ex) {
                throw new ApplicationContextException("Cannot initialize servlet context",
                        ex);
            }
        }
        initPropertySources();
    }

    /**
     * getSelfInitializer方法获得的Servlet初始化器内部会去构造一个
     * ServletContextInitializerBeans(Servlet初始化器的集合)，
     * ServletContextInitializerBeans构造的时候会去Spring容器中查找
     * ServletContextInitializer类型的bean，
     * 其中ServletRegistrationBean、FilterRegistrationBean、ServletListenerRegistrationBean会
     * 被找出(如果有定义)，这3种ServletContextInitializer会在onStartup方法中
     * 将Servlet、Filter、Listener添加到Servlet容器中(如果我们只定义了Servlet、Filter或者
     * Listener，ServletContextInitializerBeans内部会调用addAdaptableBeans方法把它
     * 们包装成RegistrationBean)：
     * @return
     */
    private org.springframework.boot.web.servlet.ServletContextInitializer getSelfInitializer() {
        return this::selfInitialize;
    }

    private void selfInitialize(ServletContext servletContext) throws ServletException {
        prepareWebApplicationContext(servletContext);
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.ExistingWebApplicationScopes existingScopes = new org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.ExistingWebApplicationScopes(
                beanFactory);
        WebApplicationContextUtils.registerWebApplicationScopes(beanFactory,
                getServletContext());
        existingScopes.restore();
        WebApplicationContextUtils.registerEnvironmentBeans(beanFactory,
                getServletContext());
        // // selfInitialize方法内部调用的getServletContextInitializerBeans方法
        // 获得ServletContextInitializerBeans
        for (ServletContextInitializer beans : getServletContextInitializerBeans()) {
            beans.onStartup(servletContext);
        }
    }

    protected Collection<ServletContextInitializer> getServletContextInitializerBeans() {
        return new ServletContextInitializerBeans(getBeanFactory());
    }

    private static final Log logger = LogFactory
            .getLog(org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.class);

    /**
     * Constant value for the DispatcherServlet bean name. A Servlet bean with this name
     * is deemed to be the "main" servlet and is automatically given a mapping of "/" by
     * default. To change the default behavior you can use a
     * {@link ServletRegistrationBean} or a different bean name.
     */
    public static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";

    private volatile WebServer webServer;

    private ServletConfig servletConfig;

    private String serverNamespace;

    /**
     * Create a new {@link org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext}.
     */
    public ServletWebServerApplicationContext() {
    }

    /**
     * Create a new {@link org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext} with the given
     * {@code DefaultListableBeanFactory}.
     * @param beanFactory the DefaultListableBeanFactory instance to use for this context
     */
    public ServletWebServerApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    /**
     * Register ServletContextAwareProcessor.
     * @see ServletContextAwareProcessor
     */
    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(
                new WebApplicationContextServletContextAwareProcessor(this));
        beanFactory.ignoreDependencyInterface(ServletContextAware.class);
    }



    @Override
    protected void onClose() {
        super.onClose();
        stopAndReleaseWebServer();
    }



    /**
     * Returns the {@link ServletWebServerFactory} that should be used to create the
     * embedded {@link WebServer}. By default this method searches for a suitable bean in
     * the context itself.
     * @return a {@link ServletWebServerFactory} (never {@code null})
     */
    protected ServletWebServerFactory getWebServerFactory() {
        // Use bean names so that we don't consider the hierarchy
        String[] beanNames = getBeanFactory()
                .getBeanNamesForType(ServletWebServerFactory.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException(
                    "Unable to start ServletWebServerApplicationContext due to missing "
                            + "ServletWebServerFactory bean.");
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException(
                    "Unable to start ServletWebServerApplicationContext due to multiple "
                            + "ServletWebServerFactory beans : "
                            + StringUtils.arrayToCommaDelimitedString(beanNames));
        }
        return getBeanFactory().getBean(beanNames[0], ServletWebServerFactory.class);
    }


    /**
     * Prepare the {@link WebApplicationContext} with the given fully loaded
     * {@link ServletContext}. This method is usually called from
     * {@link ServletContextInitializer#onStartup(ServletContext)} and is similar to the
     * functionality usually provided by a {@link ContextLoaderListener}.
     * @param servletContext the operational servlet context
     */
    protected void prepareWebApplicationContext(ServletContext servletContext) {
        Object rootContext = servletContext.getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (rootContext != null) {
            if (rootContext == this) {
                throw new IllegalStateException(
                        "Cannot initialize context because there is already a root application context present - "
                                + "check whether you have multiple ServletContextInitializers!");
            }
            return;
        }
        Log logger = LogFactory.getLog(ContextLoader.class);
        servletContext.log("Initializing Spring embedded WebApplicationContext");
        try {
            servletContext.setAttribute(
                    WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this);
            if (logger.isDebugEnabled()) {
                logger.debug(
                        "Published root WebApplicationContext as ServletContext attribute with name ["
                                + WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
                                + "]");
            }
            setServletContext(servletContext);
            if (logger.isInfoEnabled()) {
                long elapsedTime = System.currentTimeMillis() - getStartupDate();
                logger.info("Root WebApplicationContext: initialization completed in "
                        + elapsedTime + " ms");
            }
        }
        catch (RuntimeException | Error ex) {
            logger.error("Context initialization failed", ex);
            servletContext.setAttribute(
                    WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ex);
            throw ex;
        }
    }



    private void stopAndReleaseWebServer() {
        WebServer webServer = this.webServer;
        if (webServer != null) {
            try {
                webServer.stop();
                this.webServer = null;
            }
            catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    protected Resource getResourceByPath(String path) {
        if (getServletContext() == null) {
            return new ClassPathContextResource(path, getClassLoader());
        }
        return new ServletContextResource(getServletContext(), path);
    }

    @Override
    public String getServerNamespace() {
        return this.serverNamespace;
    }

    @Override
    public void setServerNamespace(String serverNamespace) {
        this.serverNamespace = serverNamespace;
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    /**
     * Returns the {@link WebServer} that was created by the context or {@code null} if
     * the server has not yet been created.
     * @return the embedded web server
     */
    @Override
    public WebServer getWebServer() {
        return this.webServer;
    }

    /**
     * Utility class to store and restore any user defined scopes. This allow scopes to be
     * registered in an ApplicationContextInitializer in the same way as they would in a
     * classic non-embedded web application context.
     */
    public static class ExistingWebApplicationScopes {

        private static final Set<String> SCOPES;

        static {
            Set<String> scopes = new LinkedHashSet<>();
            scopes.add(WebApplicationContext.SCOPE_REQUEST);
            scopes.add(WebApplicationContext.SCOPE_SESSION);
            SCOPES = Collections.unmodifiableSet(scopes);
        }

        private final ConfigurableListableBeanFactory beanFactory;

        private final Map<String, Scope> scopes = new HashMap<>();

        public ExistingWebApplicationScopes(ConfigurableListableBeanFactory beanFactory) {
            this.beanFactory = beanFactory;
            for (String scopeName : SCOPES) {
                Scope scope = beanFactory.getRegisteredScope(scopeName);
                if (scope != null) {
                    this.scopes.put(scopeName, scope);
                }
            }
        }

        public void restore() {
            this.scopes.forEach((key, value) -> {
                if (logger.isInfoEnabled()) {
                    logger.info("Restoring user defined scope " + key);
                }
                this.beanFactory.registerScope(key, value);
            });
        }

    }

}
