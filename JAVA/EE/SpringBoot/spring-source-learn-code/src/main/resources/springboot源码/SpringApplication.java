// 2.0.4.RELEASE源码解析
public class SpringApplication {

    // 第一步
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[]{primarySource}, args);
    }

    // 第二步
    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new SpringApplication(primarySources).run(args);
    }

    /**
     * 第3步:
     */
    public ConfigurableApplicationContext run(String... args) {
        // 关键步骤:
        ConfigurableApplicationContext context = null;
        try {
            // 创建Spring容器
            context = createApplicationContext();
            // Spring容器的刷新
            refreshContext(context);
            // 容器创建刷新完成之后执行额外一些操作
        } catch (Throwable ex) {
            handleRunFailure(context, ex, exceptionReporters, listeners);
            throw new IllegalStateException(ex);
        }
        // 这样run方法执行完成之后。Spring容器也已经初始化完成，各种监听器和初始化器也做了相应的工作
        return context;// 返回Spring ioc容器(是AbstractApplicationContext的子类)
    }

    /**
     * Spring容器的创建
     */
    protected ConfigurableApplicationContext createApplicationContext() {
        Class<?> contextClass = this.applicationContextClass;
        if (contextClass == null) {
            try {
                // 如果是web程序，那么构造AnnotationConfigServletWebServerApplicationContext容器
                // 否则构造AnnotationConfigReactiveWebServerApplicationContext容器
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
                throw new IllegalStateException();
            }
        }
        return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
    }

    /**
     * Spring容器的刷新refresh方法内部会做很多很多的事情：
     * 比如BeanFactory的设置，BeanFactoryPostProcessor接口的执行、BeanPostProcessor接口的执行、
     * 自动化配置类的解析、条件注解的解析、国际化的初始化等等。
     */
    private void refreshContext(ConfigurableApplicationContext context) {
        refresh(context);
    }

    protected void refresh(ApplicationContext applicationContext) {
        ((AbstractApplicationContext) applicationContext).refresh();
    }

}
