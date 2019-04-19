package com.jinuxliang.first_springboot_app.source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.*;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.*;
import org.springframework.web.context.support.StandardServletEnvironment;
import java.lang.reflect.Constructor;
import java.security.AccessControlException;
import java.util.*;

// 2.0.4.RELEASE源码解析
public class SpringApplication {

    public static final String DEFAULT_CONTEXT_CLASS = "org.springframework.context."
            + "annotation.AnnotationConfigApplicationContext";

    public static final String DEFAULT_WEB_CONTEXT_CLASS = "org.springframework.boot."
            + "web.servlet.context.AnnotationConfigServletWebServerApplicationContext";
    public static final String DEFAULT_REACTIVE_WEB_CONTEXT_CLASS = "org.springframework."
            + "boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext";
    private static final String[] WEB_ENVIRONMENT_CLASSES = {"javax.servlet.Servlet",
            "org.springframework.web.context.ConfigurableWebApplicationContext"};
    private static final String REACTIVE_WEB_ENVIRONMENT_CLASS = "org.springframework."
            + "web.reactive.DispatcherHandler";
    private static final String MVC_WEB_ENVIRONMENT_CLASS = "org.springframework."
            + "web.servlet.DispatcherServlet";
    private static final String JERSEY_WEB_ENVIRONMENT_CLASS = "org.glassfish.jersey.server.ResourceConfig";
    private static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

    private static final Log logger = LogFactory.getLog(org.springframework.boot.SpringApplication.class);

    private Set<Class<?>> primarySources;

    private Set<String> sources = new LinkedHashSet<>();

    private Class<?> mainApplicationClass;

    private Banner.Mode bannerMode = Banner.Mode.CONSOLE;

    private boolean logStartupInfo = true;

    private boolean addCommandLineProperties = true;

    private Banner banner;

    private ResourceLoader resourceLoader;

    private BeanNameGenerator beanNameGenerator;

    private ConfigurableEnvironment environment;

    private Class<? extends ConfigurableApplicationContext> applicationContextClass;

    // 是一个枚举
    private WebApplicationType webApplicationType;

    private boolean headless = true;

    private boolean registerShutdownHook = true;

    private List<ApplicationContextInitializer<?>> initializers;

    private List<ApplicationListener<?>> listeners;

    private Map<String, Object> defaultProperties;

    private Set<String> additionalProfiles = new HashSet<>();

    /**
     * 第三步
     */
    public SpringApplication(Class<?>... primarySources) {
        // primarySources是FirstSpringbootAppApplication.class
        this(null, primarySources);
    }

    /**
     * 第4步
     */
    public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.resourceLoader = resourceLoader;
        Assert.notNull(primarySources, "PrimarySources must not be null");

        // 把primarySources设置到SpringApplication的primarySources属性中，
        // 目前只是一个FirstSpringbootAppApplication.class
        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));

        // 推断web应用程序类型:只有当javax.servlet.Servlet和
        // org.springframework.web.context.ConfigurableWebApplicationContext都在类加载器中存在的情况下，
        // 才认为当前是基于servlet的web应用程序，应该启动一个嵌入式的servlet web服务器.并设置到webApplicationType属性中
        this.webApplicationType = deduceWebApplicationType();

        // 从spring.factories文件中找出key为ApplicationContextInitializer的
        // 类并实例化后设置到SpringApplication的initializers属性中。这个过程也就是找出所有的应用程序初始化器

        // ApplicationContextInitializer，应用程序上下文初始化器，做一些初始化的工作：
        // ApplicationContextInitializer中有一个void initialize(C applicationContext);用于
        // Initialize the given application context.
        setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));

        // 从spring.factories文件中找出key为ApplicationListener的类并实例化后设
        // 置到SpringApplication的listeners属性中。这个过程就是找出所有的应用程序事件监听器

        // ApplicationListener，应用程序事件(ApplicationEvent)监听器：
        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
        // ApplicationListener的void onApplicationEvent(E event);用于处理an application event

        // 推断main类，这里是FirstSpringbootAppApplication.class
        this.mainApplicationClass = deduceMainApplicationClass();
    }

    // 第一步
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[]{primarySource}, args);
    }

    // 第二步
    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new SpringApplication(primarySources).run(args);
    }

    private static <E> Set<E> asUnmodifiableOrderedSet(Collection<E> elements) {
        List<E> list = new ArrayList<>();
        list.addAll(elements);
        list.sort(AnnotationAwareOrderComparator.INSTANCE);
        return new LinkedHashSet<>(list);
    }

    private WebApplicationType deduceWebApplicationType() {
        if (ClassUtils.isPresent(REACTIVE_WEB_ENVIRONMENT_CLASS, null)
                && !ClassUtils.isPresent(MVC_WEB_ENVIRONMENT_CLASS, null)
                && !ClassUtils.isPresent(JERSEY_WEB_ENVIRONMENT_CLASS, null)) {
            return WebApplicationType.REACTIVE;
        }
        for (String className : WEB_ENVIRONMENT_CLASSES) {
            if (!ClassUtils.isPresent(className, null)) {
                return WebApplicationType.NONE;
            }
        }
        return WebApplicationType.SERVLET;
    }

    /**
     * 第5步: SpringApplication的run方法代码如下：
     * 运行这个Spring application,创建并且刷新一个new ApplicationContext
     * <p>
     * 参数args是应用程序参数(通常是从Java main方法传过来的)
     * <p>
     * 返回一个正在运行的 a running {@link ApplicationContext}
     */
    public ConfigurableApplicationContext run(String... args) {
        // 构造一个任务执行观察器
        StopWatch stopWatch = new StopWatch();
        // 开始执行，记录开始时间
        stopWatch.start();
        // 定义Spring容器
        ConfigurableApplicationContext context = null;
        Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
        //configureHeadlessProperty();
        // 获取SpringApplicationRunListeners，内部只有一个EventPublishingRunListener
        SpringApplicationRunListeners listeners = getRunListeners(args);
        // 上面分析过，会封装成SpringApplicationEvent事件然后广播出去给SpringApplication中的listeners所监听
        // 这里接受ApplicationStartedEvent事件的listener会执行相应的操作
        listeners.starting();
        try {
            // 构造一个应用程序参数持有类
            ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
            ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
            configureIgnoreBeanInfo(environment);
            Banner printedBanner = printBanner(environment);
            // 创建Spring容器
            context = createApplicationContext();
            exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
                    new Class[]{ConfigurableApplicationContext.class}, context);
            prepareContext(context, environment, listeners, applicationArguments, printedBanner);
            // Spring容器的刷新
            refreshContext(context);
            // run方法中的Spring容器创建完成之后会调用afterRefresh方法,
            // 容器创建完成之后执行额外一些操作
            afterRefresh(context, applicationArguments);// 目前是个空实现
            stopWatch.stop();// 执行结束，记录执行时间
            if (this.logStartupInfo) {
                // 简化删除源码
            }
            // 广播出ApplicationStartedEvent事件给相应的监听器执行
            listeners.started(context);
            // 调用Spring容器中的ApplicationRunner和CommandLineRunner接口的实现类
            callRunners(context, applicationArguments);
        } catch (Throwable ex) {
            // 这个过程报错的话会执行一些异常操作、然后广播出ApplicationFailedEvent事件给相应的监听器执行
            handleRunFailure(context, ex, exceptionReporters, listeners);
            throw new IllegalStateException(ex);
        }

        try {
            listeners.running(context);
        } catch (Throwable ex) {
            handleRunFailure(context, ex, exceptionReporters, null);
            throw new IllegalStateException(ex);
        }
        // 这样run方法执行完成之后。Spring容器也已经初始化完成，各种监听器和初始化器也做了相应的工作。
        return context;// 返回Spring容器
    }

    private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners,
            ApplicationArguments applicationArguments) {
        // Create and configure the environment
        // 创建应用程序的环境信息。如果是web程序，创建StandardServletEnvironment；否则，创建StandardEnvironment
        ConfigurableEnvironment environment = getOrCreateEnvironment();
        // 配置一些环境信息。比如profile，命令行参数
        configureEnvironment(environment, applicationArguments.getSourceArgs());
        // 广播出ApplicationEnvironmentPreparedEvent事件给相应的监听器执行
        listeners.environmentPrepared(environment);
        bindToSpringApplication(environment);
        // 环境信息的校对
        if (this.webApplicationType == WebApplicationType.NONE) {
            //environment = new EnvironmentConverter(getClassLoader()).convertToStandardEnvironmentIfNecessary(environment);
            environment = null;
        }
        ConfigurationPropertySources.attach(environment);
        return environment;
    }

    /**
     * Spring容器的创建
     */
    protected ConfigurableApplicationContext createApplicationContext() {
        // ConfigurableApplicationContext是一个接口,这个接口继承了ApplicationContext，
        // 这里获取的是一个具体的ConfigurableApplicationContext的实现类
        Class<?> contextClass = this.applicationContextClass;
        if (contextClass == null) {
            try {
                // 如果是web程序，那么构造
                // org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext容器
                // 否则构造
                // org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext容器
                switch (this.webApplicationType) {
                    case SERVLET:
                        contextClass = Class.forName(DEFAULT_WEB_CONTEXT_CLASS);
                        break;
                    case REACTIVE:
                        contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
                        break;
                    default:
                        contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
                }
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException(
                        "Unable create a default ApplicationContext, "
                                + "please specify an ApplicationContextClass",
                        ex);
            }
        }
        // 基于web的应用中初始化的是AnnotationConfigServletWebServerApplicationContext
        // ,这个类的构造器中会先创建BeanFactory实例(在父类GenericApplicationContext中创建的beanFactory)，
        // 然后在构造reader的时候，这里的reader
        // 是AnnotatedBeanDefinitionReader，在这个reader的构造器中，会调用
        // AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
        // 然后会走到这个AnnotationConfigUtils。registerAnnotationConfigProcessors()方法,
        // 在这个方法中，会注册internalConfigurationAnnotationProcessor，等一系列的
        // Processor，自己看下，这个后面会使用,
        //
        // 注意AnnotationConfigUtils中重要的一步是方法
        // registerBeanDefinition()，这个方法会注册Bean定义.后面在解析,这个注册其实是往
        // beanFactory(实现类为DefaultListableBeanFactory.class)中属性beanDefinitionMap中put内容，然后在
        // ConfigurationClassPostProcessor的processConfigBeanDefinitions方法中执行registry.getBeanDefinitionNames()，
        // 获取的String数组就是beanFactory(实现类为DefaultListableBeanFactory.class)中属性beanDefinitionNames，
        // 这个beanDefinitionNames就是刚刚上面AnnotationConfigUtils中registerBeanDefinition()的时候添加的.
        return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
    }

    private void prepareContext(ConfigurableApplicationContext context,
                                ConfigurableEnvironment environment, SpringApplicationRunListeners listeners,
                                ApplicationArguments applicationArguments, Banner printedBanner) {
        // 设置Spring容器的环境信息
        context.setEnvironment(environment);
        // 回调方法，Spring容器创建之后做一些额外的事
        postProcessApplicationContext(context);
        // SpringApplication的的初始化器开始工作
        applyInitializers(context);
        // 遍历调用SpringApplicationRunListener的contextPrepared方法。目前只是将这个事件广播器注册到Spring容器中
        listeners.contextPrepared(context);
        if (this.logStartupInfo) {
            logStartupProfileInfo(context);
        }

        // Add boot specific singleton beans
        // 把应用程序参数持有类注册到Spring容器中，并且是一个单例
        context.getBeanFactory().registerSingleton("springApplicationArguments", applicationArguments);
        if (printedBanner != null) {// 是否在控制台上打印自定义的banner
            context.getBeanFactory().registerSingleton("springBootBanner", printedBanner);
        }

        // Load the sources
        Set<Object> sources = getAllSources();
        Assert.notEmpty(sources, "Sources must not be empty");
        load(context, sources.toArray(new Object[0]));
        // 广播出ApplicationPreparedEvent事件给相应的监听器执行
        listeners.contextLoaded(context);
    }

    protected void afterRefresh(ConfigurableApplicationContext context,
                                ApplicationArguments args) {
    }

    private Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        } catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }

    private void callRunners(ApplicationContext context, ApplicationArguments args) {
        List<Object> runners = new ArrayList<>();
        // 找出Spring容器中ApplicationRunner接口的实现类
        runners.addAll(context.getBeansOfType(ApplicationRunner.class).values());
        // 找出Spring容器中CommandLineRunner接口的实现类
        runners.addAll(context.getBeansOfType(CommandLineRunner.class).values());
        // 对runners进行排序
        AnnotationAwareOrderComparator.sort(runners);
        // 遍历runners依次执行
        for (Object runner : new LinkedHashSet<>(runners)) {
            if (runner instanceof ApplicationRunner) {// 如果是ApplicationRunner，进行ApplicationRunner的run方法调用
                callRunner((ApplicationRunner) runner, args);
            }
            if (runner instanceof CommandLineRunner) {// 如果是CommandLineRunner，进行CommandLineRunner的run方法调用
                callRunner((CommandLineRunner) runner, args);
            }
        }
    }

    private ConfigurableEnvironment getOrCreateEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        if (this.webApplicationType == WebApplicationType.SERVLET) {
            return new StandardServletEnvironment();
        }
        return new StandardEnvironment();
    }

    // 初始化器做的工作，比如ContextIdApplicationContextInitializer会设置应用程序的id；
    // AutoConfigurationReportLoggingInitializer会给应用程序添加一个条件注解解析器报告等：
    protected void applyInitializers(ConfigurableApplicationContext context) {
        // 遍历每个初始化器，对调用对应的initialize方法
        for (ApplicationContextInitializer initializer : getInitializers()) {
            Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(
                    initializer.getClass(), ApplicationContextInitializer.class);
            Assert.isInstanceOf(requiredType, context, "Unable to call initializer.");
            initializer.initialize(context);
        }
    }

    /**
     * Spring容器的刷新refresh方法内部会做很多很多的事情：
     * 比如BeanFactory的设置，BeanFactoryPostProcessor接口的执行、BeanPostProcessor接口的执行、
     * 自动化配置类的解析、条件注解的解析、国际化的初始化等等。
     * @param context
     */
    private void refreshContext(ConfigurableApplicationContext context) {
        refresh(context);
        if (this.registerShutdownHook) {
            try {
                context.registerShutdownHook();
            } catch (AccessControlException ex) {
                // Not allowed in some environments.
            }
        }
    }

    /**
     * Refresh the underlying {@link ApplicationContext}.
     */
    protected void refresh(ApplicationContext applicationContext) {
        Assert.isInstanceOf(AbstractApplicationContext.class, applicationContext);
        // 如果是web环境，这里调用的是
        // org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
        // 此类是AbstractApplicationContext的子类.
        ((AbstractApplicationContext) applicationContext).refresh();
        // fresh中做的工作: BeanFactory的设置，BeanFactoryPostProcessor接口的执行、BeanPostProcessor接口的执行
        // ,自动化配置类的解析、条件注解的解析、国际化的初始化等等。
    }

    /**
     * Spring容器创建之后有个回调方法postProcessApplicationContext
     */
    protected void postProcessApplicationContext(ConfigurableApplicationContext context) {
        if (this.beanNameGenerator != null) {// 如果SpringApplication设置了是实例命名生成器，注册到Spring容器中
            context.getBeanFactory().registerSingleton(
                    AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR,
                    this.beanNameGenerator);
        }
        if (this.resourceLoader != null) {// 如果SpringApplication设置了资源加载器，设置到Spring容器中
            if (context instanceof GenericApplicationContext) {
                ((GenericApplicationContext) context)
                        .setResourceLoader(this.resourceLoader);
            }
            if (context instanceof DefaultResourceLoader) {
                ((DefaultResourceLoader) context)
                        .setClassLoader(this.resourceLoader.getClassLoader());
            }
        }
    }

    private SpringApplicationRunListeners getRunListeners(String[] args) {
        Class<?>[] types = new Class<?>[]{org.springframework.boot.SpringApplication.class, String[].class};
        return new SpringApplicationRunListeners(logger, getSpringFactoriesInstances(
                SpringApplicationRunListener.class, types, this, args));
    }

    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type) {
        return getSpringFactoriesInstances(type, new Class<?>[]{});
    }

    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type,
                                                          Class<?>[] parameterTypes, Object... args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // Use names and ensure unique to protect against duplicates
        Set<String> names = new LinkedHashSet<>(
                SpringFactoriesLoader.loadFactoryNames(type, classLoader));
        List<T> instances = createSpringFactoriesInstances(type, parameterTypes,
                classLoader, args, names);
        AnnotationAwareOrderComparator.sort(instances);
        return instances;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> createSpringFactoriesInstances(Class<T> type,
                                                       Class<?>[] parameterTypes, ClassLoader classLoader, Object[] args,
                                                       Set<String> names) {
        List<T> instances = new ArrayList<>(names.size());
        for (String name : names) {
            try {
                Class<?> instanceClass = ClassUtils.forName(name, classLoader);
                Assert.isAssignable(type, instanceClass);
                Constructor<?> constructor = instanceClass
                        .getDeclaredConstructor(parameterTypes);
                T instance = (T) BeanUtils.instantiateClass(constructor, args);
                instances.add(instance);
            } catch (Throwable ex) {
                throw new IllegalArgumentException(
                        "Cannot instantiate " + type + " : " + name, ex);
            }
        }
        return instances;
    }

    /**
     * Template method delegating to
     * {@link #configurePropertySources(ConfigurableEnvironment, String[])} and
     * {@link #configureProfiles(ConfigurableEnvironment, String[])} in that order.
     * Override this method for complete control over Environment customization, or one of
     * the above for fine-grained control over property sources or profiles, respectively.
     *
     * @param environment this application's environment
     * @param args        arguments passed to the {@code run} method
     * @see #configureProfiles(ConfigurableEnvironment, String[])
     * @see #configurePropertySources(ConfigurableEnvironment, String[])
     */
    protected void configureEnvironment(ConfigurableEnvironment environment,
                                        String[] args) {
        configurePropertySources(environment, args);
        configureProfiles(environment, args);
    }

    /**
     * Add, remove or re-order any {@link PropertySource}s in this application's
     * environment.
     *
     * @param environment this application's environment
     * @param args        arguments passed to the {@code run} method
     * @see #configureEnvironment(ConfigurableEnvironment, String[])
     */
    protected void configurePropertySources(ConfigurableEnvironment environment,
                                            String[] args) {
        MutablePropertySources sources = environment.getPropertySources();
        if (this.defaultProperties != null && !this.defaultProperties.isEmpty()) {
            sources.addLast(
                    new MapPropertySource("defaultProperties", this.defaultProperties));
        }
        if (this.addCommandLineProperties && args.length > 0) {
            String name = CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME;
            if (sources.contains(name)) {
                PropertySource<?> source = sources.get(name);
                CompositePropertySource composite = new CompositePropertySource(name);
                composite.addPropertySource(new SimpleCommandLinePropertySource(
                        "springApplicationCommandLineArgs", args));
                composite.addPropertySource(source);
                sources.replace(name, composite);
            } else {
                sources.addFirst(new SimpleCommandLinePropertySource(args));
            }
        }
    }

    /**
     * Configure which profiles are active (or active by default) for this application
     * environment. Additional profiles may be activated during configuration file
     * processing via the {@code spring.profiles.active} property.
     *
     * @param environment this application's environment
     * @param args        arguments passed to the {@code run} method
     * @see #configureEnvironment(ConfigurableEnvironment, String[])
     * @see org.springframework.boot.context.config.ConfigFileApplicationListener
     */
    protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
        environment.getActiveProfiles(); // ensure they are initialized
        // But these ones should go first (last wins in a property key clash)
        Set<String> profiles = new LinkedHashSet<>(this.additionalProfiles);
        profiles.addAll(Arrays.asList(environment.getActiveProfiles()));
        environment.setActiveProfiles(StringUtils.toStringArray(profiles));
    }

    private void configureIgnoreBeanInfo(ConfigurableEnvironment environment) {
        if (System.getProperty(
                CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME) == null) {
            Boolean ignore = environment.getProperty("spring.beaninfo.ignore",
                    Boolean.class, Boolean.TRUE);
            System.setProperty(CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME,
                    ignore.toString());
        }
    }

    /**
     * Bind the environment to the {@link org.springframework.boot.SpringApplication}.
     *
     * @param environment the environment to bind
     */
    protected void bindToSpringApplication(ConfigurableEnvironment environment) {
        try {
            Binder.get(environment).bind("spring.main", Bindable.ofInstance(this));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot bind to SpringApplication", ex);
        }
    }

    private Banner printBanner(ConfigurableEnvironment environment) {
        // 简化实现,具体看源码
        return null;
    }

    /**
     * Called to log active profile information.
     *
     * @param context the application context
     */
    protected void logStartupProfileInfo(ConfigurableApplicationContext context) {
        Log log = getApplicationLog();
        if (log.isInfoEnabled()) {
            String[] activeProfiles = context.getEnvironment().getActiveProfiles();
            if (ObjectUtils.isEmpty(activeProfiles)) {
                String[] defaultProfiles = context.getEnvironment().getDefaultProfiles();
                log.info("No active profile set, falling back to default profiles: "
                        + StringUtils.arrayToCommaDelimitedString(defaultProfiles));
            } else {
                log.info("The following profiles are active: "
                        + StringUtils.arrayToCommaDelimitedString(activeProfiles));
            }
        }
    }

    /**
     * Returns the {@link Log} for the application. By default will be deduced.
     *
     * @return the application log
     */
    protected Log getApplicationLog() {
        if (this.mainApplicationClass == null) {
            return logger;
        }
        return LogFactory.getLog(this.mainApplicationClass);
    }

    /**
     * Load beans into the application context.
     *
     * @param context the context to load beans into
     * @param sources the sources to load
     */
    protected void load(ApplicationContext context, Object[] sources) {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading source " + StringUtils.arrayToCommaDelimitedString(sources));
        }
        BeanDefinitionLoader loader = createBeanDefinitionLoader(
                getBeanDefinitionRegistry(context), sources);
        if (this.beanNameGenerator != null) {
            loader.setBeanNameGenerator(this.beanNameGenerator);
        }
        if (this.resourceLoader != null) {
            loader.setResourceLoader(this.resourceLoader);
        }
        if (this.environment != null) {
            loader.setEnvironment(this.environment);
        }
        loader.load();
    }

    public ClassLoader getClassLoader() {
        if (this.resourceLoader != null) {
            return this.resourceLoader.getClassLoader();
        }
        return ClassUtils.getDefaultClassLoader();
    }

    /**
     * Get the bean definition registry.
     *
     * @param context the application context
     * @return the BeanDefinitionRegistry if it can be determined
     */
    private BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext context) {
        if (context instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context;
        }
        if (context instanceof AbstractApplicationContext) {
            return (BeanDefinitionRegistry) ((AbstractApplicationContext) context)
                    .getBeanFactory();
        }
        throw new IllegalStateException("Could not locate BeanDefinitionRegistry");
    }

    /**
     * Factory method used to create the {@link BeanDefinitionLoader}.
     *
     * @param registry the bean definition registry
     * @param sources  the sources to load
     * @return the {@link BeanDefinitionLoader} that will be used to load beans
     */
    protected BeanDefinitionLoader createBeanDefinitionLoader(
            BeanDefinitionRegistry registry, Object[] sources) {
        return new BeanDefinitionLoader(registry, sources);
    }

    private void callRunner(ApplicationRunner runner, ApplicationArguments args) {
        try {
            (runner).run(args);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to execute ApplicationRunner", ex);
        }
    }

    private void callRunner(CommandLineRunner runner, ApplicationArguments args) {
        try {
            (runner).run(args.getSourceArgs());
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to execute CommandLineRunner", ex);
        }
    }

    private void handleRunFailure(ConfigurableApplicationContext context, Throwable exception,
                                  Collection<SpringBootExceptionReporter> exceptionReporters,
                                  SpringApplicationRunListeners listeners) {
        if (listeners != null) {
            listeners.failed(context, exception);
        }
    }

    /**
     * Returns whether this {@link org.springframework.boot.SpringApplication} is running within a web environment.
     */
    @Deprecated
    public boolean isWebEnvironment() {
        return this.webApplicationType == WebApplicationType.SERVLET;
    }

    /**
     * Sets if this application is running within a web environment. If not specified will
     * attempt to deduce the environment based on the classpath.
     *
     * @param webEnvironment if the application is running in a web environment
     * @deprecated since 2.0.0 in favor of
     */
    @Deprecated
    public void setWebEnvironment(boolean webEnvironment) {
        this.webApplicationType = (webEnvironment ? WebApplicationType.SERVLET
                : WebApplicationType.NONE);
    }

    /**
     * Return an immutable set of all the sources that will be added to an
     * ApplicationContext when {@link #run(String...)} is called.
     *
     * @return an immutable set of all sources
     */
    public Set<Object> getAllSources() {
        Set<Object> allSources = new LinkedHashSet<>();
        if (!CollectionUtils.isEmpty(this.primarySources)) {
            allSources.addAll(this.primarySources);
        }
        if (!CollectionUtils.isEmpty(this.sources)) {
            allSources.addAll(this.sources);
        }
        return Collections.unmodifiableSet(allSources);
    }

    /**
     * Returns read-only ordered Set of the {@link ApplicationContextInitializer}s that
     * will be applied to the Spring {@link ApplicationContext}.
     *
     * @return the initializers
     */
    public Set<ApplicationContextInitializer<?>> getInitializers() {
        return asUnmodifiableOrderedSet(this.initializers);
    }

    /**
     * Sets the {@link ApplicationContextInitializer} that will be applied to the Spring
     * {@link ApplicationContext}.
     *
     * @param initializers the initializers to set
     */
    public void setInitializers(
            Collection<? extends ApplicationContextInitializer<?>> initializers) {
        this.initializers = new ArrayList<>();
        this.initializers.addAll(initializers);
    }

    /**
     * Sets the {@link ApplicationListener}s that will be applied to the SpringApplication
     * and registered with the {@link ApplicationContext}.
     *
     * @param listeners the listeners to set
     */
    public void setListeners(Collection<? extends ApplicationListener<?>> listeners) {
        this.listeners = new ArrayList<>();
        this.listeners.addAll(listeners);
    }

}
