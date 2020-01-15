# 嵌入式Servlet容器自动配置原理

    ServletWebServerFactoryAutoConfiguration(springboot2.0的配置类)
    见名知意: WebServer工厂的自动配置类.

```java
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(ServletRequest.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(ServerProperties.class)
//导入BeanPostProcessorsRegistrar：给容器中导入一些组件
//导入了WebServerFactoryCustomizerBeanPostProcessor,
//这是一个后置处理器：bean初始化前后（创建完对象，还没赋值赋值）执行初始化工作
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class,
		ServletWebServerFactoryConfiguration.EmbeddedTomcat.class, // 配置TomcatServletWebServerFactory工厂
		ServletWebServerFactoryConfiguration.EmbeddedJetty.class,// 配置JettyServletWebServerFactory工厂(依据条件注解判断是否初始化实例)
		ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })//配置UndertowServletWebServerFactory工厂
public class ServletWebServerFactoryAutoConfiguration {
    
}
```

# 以TomcatServletWebServerFactory为例

```java
public class TomcatServletWebServerFactory extends AbstractServletWebServerFactory implements ConfigurableTomcatWebServerFactory, ResourceLoaderAware {
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        // 创建一个Tomcat
        Tomcat tomcat = new Tomcat();
        //配置Tomcat的基本环节
        File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector(this.protocol);
        tomcat.getService().addConnector(connector);
        this.customizeConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
        this.configureEngine(tomcat.getEngine());
        Iterator var5 = this.additionalTomcatConnectors.iterator();
    
        while(var5.hasNext()) {
            Connector additionalConnector = (Connector)var5.next();
            tomcat.getService().addConnector(additionalConnector);
        }
    
        this.prepareContext(tomcat.getHost(), initializers);
        //将配置好的Tomcat传入进去，返回一个WebServer；并且启动Tomcat服务器
        return this.getTomcatWebServer(tomcat);
    }
}
```

# 我们对嵌入式容器的配置修改是怎么生效？

    WebServerFactoryCustomizer定制器帮我们修改了Servlet容器的配置

    容器中导入了WebServerFactoryCustomizerBeanPostProcessor

```java
public class WebServerFactoryCustomizerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private ListableBeanFactory beanFactory;
    private List<WebServerFactoryCustomizer<?>> customizers;

    public WebServerFactoryCustomizerBeanPostProcessor() {
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.isInstanceOf(ListableBeanFactory.class, beanFactory, "WebServerCustomizerBeanPostProcessor can only be used with a ListableBeanFactory");
        this.beanFactory = (ListableBeanFactory)beanFactory;
    }

    //初始化之前
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    //如果当前初始化的是一个WebServerFactory类型的组件
        if (bean instanceof WebServerFactory) {
            this.postProcessBeforeInitialization((WebServerFactory)bean);
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
        ((Callbacks)LambdaSafe.callbacks(WebServerFactoryCustomizer.class, this.getCustomizers(), webServerFactory, new Object[0]).withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)).invoke((customizer) -> {
            // 获取所有的定制器，调用每一个定制器的customize方法来给Servlet容器进行属性赋值；            
            customizer.customize(webServerFactory);
        });
    }

    private Collection<WebServerFactoryCustomizer<?>> getCustomizers() {
        if (this.customizers == null) {
            this.customizers = new ArrayList(this.getWebServerFactoryCustomizerBeans());
            this.customizers.sort(AnnotationAwareOrderComparator.INSTANCE);
            this.customizers = Collections.unmodifiableList(this.customizers);
        }
        return this.customizers;
    }

    private Collection<WebServerFactoryCustomizer<?>> getWebServerFactoryCustomizerBeans() {
            // 从容器中获取所有这个类型的组件：WebServerFactoryCustomizer
            // 这个组件是用来定制WebServer
        return this.beanFactory.getBeansOfType(WebServerFactoryCustomizer.class, false, false).values();
    }
}
```

# 步骤

    1）、SpringBoot根据导入的依赖情况，给容器中添加相应的TomcatServletWebServerFactory
    2）、容器中某个组件要创建对象就会惊动后置处理器；WebServerFactoryCustomizerBeanPostProcessor,只要是WebServerFactory工厂，这个后置处理器就工作；
    3）、后置处理器，从容器中获取所有的WebServerFactoryCustomizer，调用定制器的定制方法
