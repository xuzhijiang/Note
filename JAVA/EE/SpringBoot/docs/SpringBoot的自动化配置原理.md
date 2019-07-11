## Spring自动化配置原理

>以使用SpringMVC并且视图使用freemarker为例，分析springboot内部是如何解析freemarker视图的.

使用freemarker视图框架的依赖:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
    <version>1.3.5.RELEASE</version>
</dependency>
```

>这个spring-boot-starter-freemarker依赖对应的jar包里的文件如下：

```shell
META-INF
├── MANIFEST.MF
├── maven
│   └── org.springframework.boot
│       └── spring-boot-starter-freemarker
│           ├── pom.properties
│           └── pom.xml
└── spring.provides
```

```xml
<!-- spring-boot-starter-freemarker内部的pom.xml里需要的依赖如下 -->
<!-- 可以看到这个spring-boot-starter-freemarker依赖内部并没有freemarker的ViewResolver，而是仅仅加入了freemarker的依赖，还有3个依赖，分别是spring-boot-starter、spring-boot-starter-web和spring-context-support。 -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.freemarker</groupId>
  <artifactId>freemarker</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context-support</artifactId>
</dependency>
```

### 为什么在springboot中加入了spring-boot-starter-freemarker后，SpringMVC自动地构造了一个freemarker的ViewResolver？

>先看下maven配置，看到了一个parent配置:

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```

这个spring-boot-starter-parent的pom文件在![http://central.maven.org/maven2/org/springframework/boot/spring-boot-starter-parent/1.3.5.RELEASE/spring-boot-starter-parent-1.3.5.RELEASE.pom](http://central.maven.org/maven2/org/springframework/boot/spring-boot-starter-parent/1.3.5.RELEASE/spring-boot-starter-parent-1.3.5.RELEASE.pom)

>spring-boot-starter-parent内部也有一个parent：

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>1.3.5.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```

>这个spring-boot-dependencies的pom文件在![http://central.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/1.5.19.RELEASE/spring-boot-dependencies-1.5.19.RELEASE.pom](http://central.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/1.5.19.RELEASE/spring-boot-dependencies-1.5.19.RELEASE.pom)，内部有很多依赖.

比如spring-boot-starter-web、spring-boot-starter-websocket、spring-boot-starter-data-solr,spring-boot-starter-freemarker等等，基本上所有的依赖都在这个parent里。

>freemarker使用了parent依赖里的两个依赖，分别是spring-boot-starter-web和spring-boot-starter-freemarker。其中spring-boot-starter-web内部依赖了spring的两个springweb依赖：spring-web和spring-webmvc。spring-boot-starter-web内部还依赖spring-boot-starter，这个spring-boot-starter依赖了spring核心依赖spring-core；还依赖了spring-boot和spring-boot-autoconfigure这两个。

`spring-boot`定义了很多基础功能类，像运行程序的SpringApplication，Logging系统，一些tomcat或者jetty这些EmbeddedServlet容器，配置属性loader等等。

```java
// `spring-boot-autoconfigure`定义了很多自动配置的类，比如jpa，solr，redis，elasticsearch、mongo、freemarker、velocity，thymeleaf等等自动配置的类.
// FreeMarkerAutoConfiguration(freemarker的自动化配置类)就在spring-boot-autoconfigure这个依赖jar中

@Configuration
@ConditionalOnClass({freemarker.template.Configuration.class,
        FreeMarkerConfigurationFactory.class}) // 判断当前项目中有没有这2个类,需要freemarker.template.Configuration和FreeMarkerConfigurationFactory这两个类存在在classpath中才会进行自动配置
@AutoConfigureAfter(WebMvcAutoConfiguration.class) // 本次自动配置需要依赖WebMvcAutoConfiguration这个配置类配置之后触发。这个WebMvcAutoConfiguration内部会配置很多Web基础性的东西，比如RequestMappingHandlerMapping、RequestMappingHandlerAdapter等,
// 也就是这个FreeMarkerAutoConfiguration.class配置类需要在一些其他配置类(这里就是WebMvcAutoConfiguration.class)之后进行加载.
@EnableConfigurationProperties(FreeMarkerProperties.class) // 使用FreeMarkerProperties类中的配置
public class FreeMarkerAutoConfiguration {

    private static final Log logger = LogFactory
            .getLog(FreeMarkerAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FreeMarkerProperties properties;

    @PostConstruct // 构造之后调用的方法，主要检查模板位置是否存在
    public void checkTemplateLocationExists() {
        if (this.properties.isCheckTemplateLocation()) {
            TemplateLocation templatePathLocation = null;
            List<TemplateLocation> locations = new ArrayList<TemplateLocation>();
            for (String templateLoaderPath : this.properties.getTemplateLoaderPath()) {
                TemplateLocation location = new TemplateLocation(templateLoaderPath);
                locations.add(location);
                if (location.exists(this.applicationContext)) {
                    templatePathLocation = location;
                    break;
                }
            }
            if (templatePathLocation == null) {
                logger.warn("Cannot find template location(s): " + locations
                        + " (please add some templates, "
                        + "check your FreeMarker configuration, or set "
                        + "spring.freemarker.checkTemplateLocation=false)");
            }
        }
    }

    protected static class FreeMarkerConfiguration {

        @Autowired
        protected FreeMarkerProperties properties;

        protected void applyProperties(FreeMarkerConfigurationFactory factory) {
            factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
            factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
            factory.setDefaultEncoding(this.properties.getCharsetName());
            Properties settings = new Properties();
            settings.putAll(this.properties.getSettings());
            factory.setFreemarkerSettings(settings);
        }

    }

    @Configuration
    @ConditionalOnNotWebApplication // 非Web项目的情况下才自动配置
    public static class FreeMarkerNonWebConfiguration extends FreeMarkerConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
            FreeMarkerConfigurationFactoryBean freeMarkerFactoryBean = new FreeMarkerConfigurationFactoryBean();
            applyProperties(freeMarkerFactoryBean);
            return freeMarkerFactoryBean;
        }

    }

    @Configuration
    @ConditionalOnClass(Servlet.class) // 判断当前项目中有没有Servlet这个类,需要运行在Servlet容器下
    @ConditionalOnWebApplication // 需要在Web项目下
    public static class FreeMarkerWebConfiguration extends FreeMarkerConfiguration {

        @Bean
        @ConditionalOnMissingBean(FreeMarkerConfig.class)
        public FreeMarkerConfigurer freeMarkerConfigurer() {
            FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
            applyProperties(configurer);
            return configurer;
        }

        @Bean
        public freemarker.template.Configuration freeMarkerConfiguration(
                FreeMarkerConfig configurer) {
            return configurer.getConfiguration();
        }

        @Bean
        @ConditionalOnMissingBean(name = "freeMarkerViewResolver") // 当Spring当前容器中不存在freeMarkerViewResolver这个bean的话，才会用这个方法构建FreeMarkerViewResolver这个bean
        @ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true) // 判断配置文件中是否存在某个配置spring.freemarker.enabled,配置文件中开关开启的话，才会构造
        public FreeMarkerViewResolver freeMarkerViewResolver() {
            // 构造了freemarker的ViewSolver，这就是一开始我们分析的为什么没有设置ViewResolver，但是最后却还是存在的原因
            FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
            this.properties.applyToViewResolver(resolver);
            return resolver;
        }

    }

}
```

>freemarker对应的配置类：

```java
@ConfigurationProperties(prefix = "spring.freemarker") // 使用配置文件中以spring.freemarker开头的配置
public class FreeMarkerProperties extends AbstractTemplateViewResolverProperties {
    public static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/"; // 默认路径

    public static final String DEFAULT_PREFIX = ""; // 默认前缀

    public static final String DEFAULT_SUFFIX = ".ftl"; // 默认后缀

    ...

}
```

>下面是官网上的freemarker配置：

```properties
# FREEMARKER (FreeMarkerAutoConfiguration)
spring.freemarker.allow-request-override=false # Set whether HttpServletRequest attributes are allowed to override (hide) controller generated model attributes of the same name.
spring.freemarker.allow-session-override=false # Set whether HttpSession attributes are allowed to override (hide) controller generated model attributes of the same name.
spring.freemarker.cache=false # Enable template caching.
spring.freemarker.charset=UTF-8 # Template encoding.
spring.freemarker.check-template-location=true # Check that the templates location exists.
spring.freemarker.content-type=text/html # Content-Type value.
spring.freemarker.enabled=true # Enable MVC view resolution for this technology.
spring.freemarker.expose-request-attributes=false # Set whether all request attributes should be added to the model prior to merging with the template.
spring.freemarker.expose-session-attributes=false # Set whether all HttpSession attributes should be added to the model prior to merging with the template.
spring.freemarker.expose-spring-macro-helpers=true # Set whether to expose a RequestContext for use by Spring's macro library, under the name "springMacroRequestContext".
spring.freemarker.prefer-file-system-access=true # Prefer file system access for template loading. File system access enables hot detection of template changes.
spring.freemarker.prefix= # Prefix that gets prepended to view names when building a URL.
spring.freemarker.request-context-attribute= # Name of the RequestContext attribute for all views.
spring.freemarker.settings.*= # Well-known FreeMarker keys which will be passed to FreeMarker's Configuration.
spring.freemarker.suffix= # Suffix that gets appended to view names when building a URL.
spring.freemarker.template-loader-path=classpath:/templates/ # Comma-separated list of template paths.
spring.freemarker.view-names= # White list of view names that can be resolved.
```

>所以说一开始我们加入了一个spring-boot-starter-freemarker依赖，这个依赖中存在freemarker的lib，满足了FreeMarkerAutoConfiguration中的ConditionalOnClass里写的freemarker.template.Configuration.class这个类存在于classpath中。所以就构造了FreeMarkerAutoConfiguration里的ViewResolver，这个ViewResolver被自动加入到SpringMVC中。同样地，如果我们要使用velocity模板，springboot内部也有velocity的自动配置类VelocityAutoConfiguration，原理是跟freemarker一样的。

>Mybatis的autoconfigure是Mybatis提供的springboot的自动配置模块，由于springboot官方没有提供mybatis的自动化配置模块，所以mybatis自己写了这么一个模块，观察它的源码，发现基本上跟freemarker的autoconfigure模块一样，只需要构造对应的实例即可。

### 总结

springboot内部提供了很多自动化配置的类，这些类会判断classpath中是否存在自己需要的那个类，如果存在则会自动配置相关的配置，否则就不会自动配置。如果我们需要使用一些框架，只需要加入依赖即可，这些依赖内部是没有代码的，只是一些对应框架需要的lib，有了这些lib就会触发自动化配置，于是就能使用框架了。

这一点跟当时看springmvc的时候对response进行json或xml渲染的原理相同。springmvc中的requestmapping注解加上responsebody注解后会返回xml或者json，如果依赖中加入jackson依赖就会转换成json，如果依赖中加入xstream依赖就会转换成xml。当然，前提是springmvc中有了这两种依赖的HttpMessageConverter代码，这个HttpMessageConverter代码就相当于springboot中的各种AutoConfiguration。

### SpringBoot自动化配置注解的开关原理

>有些配置类，比如上面讲的FreeMarkerAutoConfiguration，需要存在并且在其他的一些配置类之后进行加载。还存在一些自动化配置类，它们需要在使用一些注解开关的情况下才会生效。比如spring-boot-starter-batch里的@EnableBatchProcessing注解、@EnableCaching等。

在分析这些开关的原理之前，我们来看一个需求：`定义一个Annotation，让使用了这个Annotaion的应用程序自动化地注入一些类的bean或者做一些底层的事情。`

>示例: spring_mvc_controller_demo

### ImportSelector在SpringBoot中的使用

ImportSelector在SpringBoot中是通过@EnableAutoConfiguration注解里使用的。
这个@EnableAutoConfiguration注解可以显式地调用，否则它会在@SpringBootApplication注解中隐式地被调用.@EnableAutoConfiguration注解中使用了AutoConfigurationImportSelector作为ImportSelector。下面这段代码就是AutoConfigurationImportSelector中进行选择的具体代码:

```java
public class AutoConfigurationImportSelector
        implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware,
        BeanFactoryAware, EnvironmentAware, Ordered {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        if (!isEnabled(annotationMetadata)) {
            return NO_IMPORTS;
        }
        AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
                .loadMetadata(this.beanClassLoader);
        AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(
                autoConfigurationMetadata, annotationMetadata);
        return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
    }

    protected AutoConfigurationEntry getAutoConfigurationEntry(
            AutoConfigurationMetadata autoConfigurationMetadata,
            AnnotationMetadata annotationMetadata) {
        if (!isEnabled(annotationMetadata)) {
            return EMPTY_ENTRY;
        }
        AnnotationAttributes attributes = getAttributes(annotationMetadata);
        // getCandidateConfigurations方法将获取配置类
        List<String> configurations = getCandidateConfigurations(annotationMetadata,
                attributes);
        // 删除重复的配置
        configurations = removeDuplicates(configurations);
        // 去掉需要exclude的配置
        Set<String> exclusions = getExclusions(annotationMetadata, attributes);
        checkExcludedClasses(configurations, exclusions);
        configurations.removeAll(exclusions);

        configurations = filter(configurations, autoConfigurationMetadata);
        fireAutoConfigurationImportEvents(configurations, exclusions);
        return new AutoConfigurationEntry(configurations, exclusions);
    }           

    // getCandidateConfigurations方法将获取配置类
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata,
        AnnotationAttributes attributes) {
    // SpringFactoriesLoader.loadFactoryNames方法会从所有的jar包中读取META-INF/spring.factories文件信息.

    // getCandidateConfigurations方法中的getSpringFactoriesLoaderFactoryClass方法返回的是EnableAutoConfiguration.class，所以SpringFactoriesLoader.loadFactoryNames会过滤出key为org.springframework.boot.autoconfigure.EnableAutoConfiguration的值。
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
            getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
    Assert.notEmpty(configurations,
            "No auto configuration classes found in META-INF/spring.factories. If you "
                    + "are using a custom packaging, make sure that file is correct.");
    return configurations;
    }

}
```

>下面这段配置代码就是autoconfigure这个jar包里的spring.factories文件的一部分内容(有个key为org.springframework.boot.autoconfigure.EnableAutoConfiguration，所以会得到这些AutoConfiguration)：

```
# Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.autoconfigure.logging.AutoConfigurationReportLoggingInitializer

# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.autoconfigure.BackgroundPreinitializer

# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration,\
```

>当然了，这些AutoConfiguration不是所有都会加载的，会根据AutoConfiguration上的@ConditionalOnClass等条件判断是否加载。上面这个例子说的读取properties文件的时候只会过滤出key为`org.springframework.boot.autoconfigure.EnableAutoConfiguration`的值。
SpringBoot内部还有一些其他的key用于过滤得到需要加载的类：

* org.springframework.test.context.TestExecutionListener
* org.springframework.beans.BeanInfoFactory
* org.springframework.context.ApplicationContextInitializer
* org.springframework.context.ApplicationListener
* org.springframework.boot.SpringApplicationRunListener
* org.springframework.boot.env.EnvironmentPostProcessor
* org.springframework.boot.env.PropertySourceLoader