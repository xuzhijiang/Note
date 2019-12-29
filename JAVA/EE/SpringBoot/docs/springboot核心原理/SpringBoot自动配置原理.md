# SpringBoot自动配置原理

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
<!-- 可以看到这个spring-boot-starter-freemarker依赖内部并没有freemarker的ViewResolver，而是仅仅加入了freemarker的依赖-->
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

## 为什么在springboot中加入了spring-boot-starter-freemarker后，SpringMVC自动地构造了一个freemarker的ViewResolver？

>先看下maven配置，看到了一个parent配置:

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```

>spring-boot-starter-parent内部也有一个parent：

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>1.3.5.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```

spring-boot-starter-web、spring-boot-starter-websocket、spring-boot-starter-data-solr,spring-boot-starter-freemarker等等，基本上所有的依赖都在这个parent里。

>freemarker使用了两个依赖，分别是spring-boot-starter-web和spring-boot-starter-freemarker。其中spring-boot-starter-web内部依赖了spring的两个springweb依赖：spring-web和spring-webmvc。spring-boot-starter-web内部还依赖spring-boot-starter，这个spring-boot-starter依赖了spring核心依赖spring-core；还依赖了spring-boot和spring-boot-autoconfigure这两个。

`spring-boot`定义了很多基础功能类，像运行程序的SpringApplication，Logging系统，一些tomcat或者jetty这些EmbeddedServlet容器等等

```java
// spring-boot-autoconfigure定义了很多自动配置的类
// 比如jpa，solr，redis，elasticsearch、mongo、freemarker、velocity，thymeleaf等等自动配置的类.
// FreeMarkerAutoConfiguration(freemarker的自动化配置类)就在spring-boot-autoconfigure这个依赖jar中
@Configuration
// 判断当前项目中有没有这2个类,需要freemarker.template.Configuration和
// FreeMarkerConfigurationFactory这两个类存在在classpath中才会进行自动配置
@ConditionalOnClass({freemarker.template.Configuration.class, FreeMarkerConfigurationFactory.class}) 
// 本次自动配置需要依赖WebMvcAutoConfiguration这个配置类配置之后触发。这个WebMvcAutoConfiguration内部会配置很多Web基础性的东西，比如RequestMappingHandlerMapping、RequestMappingHandlerAdapter
@AutoConfigureAfter(WebMvcAutoConfiguration.class) 
// 使用FreeMarkerProperties类中的配置
@EnableConfigurationProperties(FreeMarkerProperties.class)
public class FreeMarkerAutoConfiguration {

    private static final Log logger = LogFactory.getLog(FreeMarkerAutoConfiguration.class);
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
    // 非Web项目的情况下才自动配置
    @ConditionalOnNotWebApplication
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
    // 判断当前项目中有没有Servlet这个类,需要运行在Servlet容器下
    @ConditionalOnClass(Servlet.class)
    // 需要在Web项目下 
    @ConditionalOnWebApplication 
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
// 当Spring当前容器中不存在freeMarkerViewResolver这个bean的话，才会用这个方法构建FreeMarkerViewResolver这个bean
        @ConditionalOnMissingBean(name = "freeMarkerViewResolver") 
        // 判断配置文件中是否存在某个配置spring.freemarker.enabled
        @ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true) 
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

>所以说一开始我们加入了一个spring-boot-starter-freemarker依赖，这个依赖中存在freemarker的lib，满足了FreeMarkerAutoConfiguration中的ConditionalOnClass里写的freemarker.template.Configuration.class这个类存在于classpath中。所以就构造了FreeMarkerAutoConfiguration里的ViewResolver，这个ViewResolver被自动加入到SpringMVC中

>这一点跟springmvc对response进行json或xml渲染的原理相同。springmvc中的requestmapping注解加上responsebody注解后会返回xml或者json，如果依赖中加入jackson依赖就会转换成json，如果依赖中加入xstream依赖就会转换成xml