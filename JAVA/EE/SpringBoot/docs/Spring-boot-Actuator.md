actuator是spring boot项目中非常强大一个功能，有助于对应用程序进行监视和管理，通过 restful api 请求来监管、审计、收集应用的运行情况，针对微服务而言它是必不可少的一个环节…

### Endpoints

它是actuator的核心部分，它用来监视应用程序及交互，spring-boot-actuator中已经内置了非常多的 Endpoints（health、info、beans、httptrace、shutdown等等），同时也允许我们自己扩展自己的端点

Spring Boot 2.0 中的端点和之前的版本有较大不同,使用时需注意。另外端点的监控机制也有很大不同，启用了不代表可以直接访问，还需要将其暴露出来，传统的management.security管理已被标记为不推荐。

### 内置Endpoints

id					desc					Sensitive
auditevents		显示当前应用程序的审计事件信息		Yes
beans			显示应用Spring Beans的完整列表	Yes
caches			显示可用缓存信息					Yes
conditions		显示自动装配类的状态及及应用信息	Yes
configprops		显示所有 @ConfigurationProperties 列表	Yes
env			显示 ConfigurableEnvironment 中的属性	Yes
flyway			显示 Flyway 数据库迁移信息	Yes
health	显示应用的健康信息（未认证只显示status，认证显示全部信息详情）	No
info			显示任意的应用信息（在资源文件写info.xxx即可）	No
liquibase		展示Liquibase 数据库迁移	Yes
metrics		展示当前应用的 metrics 信息	Yes
mappings	显示所有 @RequestMapping 路径集列表	Yes
scheduledtasks	显示应用程序中的计划任务	Yes
sessions	允许从Spring会话支持的会话存储中检索和删除用户会话。	Yes
shutdown	允许应用以优雅的方式关闭（默认情况下不启用）	Yes
threaddump	执行一个线程dump	Yes
httptrace	显示HTTP跟踪信息（默认显示最后100个HTTP请求 - 响应交换）	Yes

我们详细介绍一下spring-boot-starter-actuator模块中已经实现的一些原生端点。如果根据端点的作用来说，我们可以原生端点分为三大类：

1. 应用配置类：获取应用程序中加载的应用配置、环境变量、自动化配置报告等与Spring Boot应用密切相关的配置类信息。
2. 度量指标类：获取应用程序运行过程中用于监控的度量指标，比如：内存信息、线程池信息、HTTP请求统计等。
3. 操作控制类：提供了对应用的关闭等操作类功能。

>下面我们来详细了解一下这三类端点都分别可以为我们提供怎么样的有用信息和强大功能，以及我们如何去扩展和配置它们。

#### 应用配置类

由于Spring Boot为了改善传统Spring应用繁杂的配置内容，采用了包扫描和自动化配置的机制来加载原本集中于xml文件中的各项内容。虽然这样的做法，让我们的代码变得非常简洁，但是整个应用的实例创建和依赖关系等信息都被离散到了各个配置类的注解上，这使得我们分析整个应用中资源和实例的各种关系变得非常的困难。而这类端点就可以帮助我们轻松的获取一系列关于Spring 应用配置内容的详细报告，比如：自动化配置的报告、Bean创建的报告、环境属性的报告等。

* /autoconfig：该端点用来获取应用的自动化配置报告，其中包括所有自动化配置的候选项。同时还列出了每个候选项自动化配置的各个先决条件是否满足。所以，该端点可以帮助我们方便的找到一些自动化配置为什么没有生效的具体原因。该报告内容将自动化配置内容分为两部分：

	* positiveMatches中返回的是条件匹配成功的自动化配置
	* negativeMatches中返回的是条件匹配不成功的自动化配置

```json
{
    "positiveMatches": { // 条件匹配成功的
        "EndpointWebMvcAutoConfiguration": [
            {
                "condition": "OnClassCondition",
                "message": "@ConditionalOnClass classes found: javax.servlet.Servlet,org.springframework.web.servlet.DispatcherServlet"
            },
            {
                "condition": "OnWebApplicationCondition",
                "message": "found web application StandardServletEnvironment"
            }
        ],
        ...
    },
    "negativeMatches": {  // 条件不匹配成功的
        "HealthIndicatorAutoConfiguration.DataSourcesHealthIndicatorConfiguration": [
            {
                "condition": "OnClassCondition",
                "message": "required @ConditionalOnClass classes not found: org.springframework.jdbc.core.JdbcTemplate"
            }
        ],
        ...
    }
}
```

>从如上示例中我们可以看到，每个自动化配置候选项中都有一系列的条件，比如上面没有成功匹配的HealthIndicatorAutoConfiguration.DataSourcesHealthIndicatorConfiguration配置，它的先决条件就是需要在工程中包含org.springframework.jdbc.core.JdbcTemplate类，由于我们没有引入相关的依赖，它就不会执行自动化配置内容。所以，当我们发现有一些期望的配置没有生效时，就可以通过该端点来查看没有生效的具体原因。

* /beans：该端点用来获取应用上下文中创建的所有Bean。

```json
[
    {
        "context": "hello:dev:8881",
        "parent": null,
        "beans": [
            {
                "bean": "org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration$DispatcherServletConfiguration",
                "scope": "singleton",
                "type": "org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration$DispatcherServletConfiguration$$EnhancerBySpringCGLIB$$3440282b",
                "resource": "null",
                "dependencies": [
                    "serverProperties",
                    "spring.mvc.CONFIGURATION_PROPERTIES",
                    "multipartConfigElement"
                ]
            },
            {
                "bean": "dispatcherServlet",
                "scope": "singleton",
                "type": "org.springframework.web.servlet.DispatcherServlet",
                "resource": "class path resource [org/springframework/boot/autoconfigure/web/DispatcherServletAutoConfiguration$DispatcherServletConfiguration.class]",
                "dependencies": []
            }
        ]
    }
]
```

如上示例中，我们可以看到在每个bean中都包含了下面这几个信息：

bean：Bean的名称
scope：Bean的作用域
type：Bean的Java类型
reource：class文件的具体路径
dependencies：依赖的Bean名称

* /configprops：该端点用来获取应用中配置的属性信息报告。从下面该端点返回示例的片段中，我们看到返回了关于该短信的配置信息，prefix属性代表了属性的配置前缀，properties代表了各个属性的名称和值。所以，我们可以通过该报告来看到各个属性的配置路径，比如我们要关闭该端点，就可以通过使用endpoints.configprops.enabled=false来完成设置。

```
{
    "configurationPropertiesReportEndpoint": {
        "prefix": "endpoints.configprops",
        "properties": {
            "id": "configprops",
            "sensitive": true,
            "enabled": true
        }
    },
    ...
}
```

`/env：该端点与/configprops不同，它用来获取应用所有可用的环境属性报告。包括：环境变量、JVM属性、应用的配置配置、命令行中的参数。从下面该端点返回的示例片段中，我们可以看到它不仅返回了应用的配置属性，还返回了系统属性、环境变量等丰富的配置信息，其中也包括了应用还没有没有使用的配置。所以它可以帮助我们方便地看到当前应用可以加载的配置信息，并配合@ConfigurationProperties注解将它们引入到我们的应用程序中来进行使用。另外，为了配置属性的安全，对于一些类似密码等敏感信息，该端点都会进行隐私保护，但是我们需要让属性名中包含：password、secret、key这些关键词，这样该端点在返回它们的时候会使用*来替代实际的属性值。`

```
{
    "profiles": [
        "dev"
    ],
    "server.ports": {
        "local.server.port": 8881
    },
    "servletContextInitParams": {
        
    },
    "systemProperties": {
        "idea.version": "2016.1.3",
        "java.runtime.name": "Java(TM) SE Runtime Environment",
        "sun.boot.library.path": "C:\\Program Files\\Java\\jdk1.8.0_91\\jre\\bin",
        "java.vm.version": "25.91-b15",
        "java.vm.vendor": "Oracle Corporation",
        ...
    },
    "systemEnvironment": {
        "configsetroot": "C:\\WINDOWS\\ConfigSetRoot",
        "RABBITMQ_BASE": "E:\\tools\\rabbitmq",
        ...
    },
    "applicationConfig: [classpath:/application-dev.properties]": {
        "server.port": "8881"
    },
    "applicationConfig: [classpath:/application.properties]": {
        "server.port": "8885",
        "spring.profiles.active": "dev",
        "info.app.name": "spring-boot-hello",
        "info.app.version": "v1.0.0",
        "spring.application.name": "hello"
    }
}
```

/mappings：该端点用来返回所有Spring MVC的控制器映射关系报告。从下面的示例片段中，我们可以看该报告的信息与我们在启用Spring MVC的Web应用时输出的日志信息类似，其中bean属性标识了该映射关系的请求处理器，method属性标识了该映射关系的具体处理类和处理函数。

```
{
    "/webjars/**": {
        "bean": "resourceHandlerMapping"
    },
    "/**": {
        "bean": "resourceHandlerMapping"
    },
    "/**/favicon.ico": {
        "bean": "faviconHandlerMapping"
    },
    "{[/hello]}": {
        "bean": "requestMappingHandlerMapping",
        "method": "public java.lang.String com.didispace.web.HelloController.index()"
    },
    "{[/mappings || /mappings.json],methods=[GET],produces=[application/json]}": {
        "bean": "endpointHandlerMapping",
        "method": "public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()"
    },
    ...
}
```

/info：该端点用来返回一些应用自定义的信息。默认情况下，该端点只会返回一个空的json内容。我们可以在application.properties配置文件中通过info前缀来设置一些属性，比如下面这样：

```
/info：该端点用来返回一些应用自定义的信息。默认情况下，该端点只会返回一个空的json内容。我们可以在application.properties配置文件中通过info前缀来设置一些属性，比如下面这样：
```

再访问/info端点，我们可以得到下面的返回报告，其中就包含了上面我们在应用自定义的两个参数。
```
{
    "app": {
        "name": "spring-boot-hello",
        "version": "v1.0.0"
    }
}
```

#### 度量指标类

上面我们所介绍的应用配置类端点所提供的信息报告在应用启动的时候都已经基本确定了其返回内容，可以说是一个静态报告。而度量指标类端点提供的报告内容则是动态变化的，这些端点提供了应用程序在运行过程中的一些快照信息，比如：内存使用情况、HTTP请求统计、外部资源指标等。这些端点对于我们构建微服务架构中的监控系统非常有帮助，由于Spring Boot应用自身实现了这些端点，所以我们可以很方便地利用它们来收集我们想要的信息，以制定出各种自动化策略。下面，我们就来分别看看这些强大的端点功能。

/metrics：该端点用来返回当前应用的各类重要度量指标，比如：内存信息、线程信息、垃圾回收信息等。

我们看到有这些重要的度量值：

系统信息：包括处理器数量processors、运行时间uptime和instance.uptime、系统平均负载systemload.average。
mem.*：内存概要信息，包括分配给应用的总内存数量以及当前空闲的内存数量。这些信息来自java.lang.Runtime。
heap.*：堆内存使用情况。这些信息来自java.lang.management.MemoryMXBean接口中getHeapMemoryUsage方法获取的java.lang.management.MemoryUsage。
nonheap.*：非堆内存使用情况。这些信息来自java.lang.management.MemoryMXBean接口中getNonHeapMemoryUsage方法获取的java.lang.management.MemoryUsage。
threads.*：线程使用情况，包括线程数、守护线程数（daemon）、线程峰值（peak）等，这些数据均来自java.lang.management.ThreadMXBean。
classes.*：应用加载和卸载的类统计。这些数据均来自java.lang.management.ClassLoadingMXBean。
gc.*：垃圾收集器的详细信息，包括垃圾回收次数gc.ps_scavenge.count、垃圾回收消耗时间gc.ps_scavenge.time、标记-清除算法的次数gc.ps_marksweep.count、标记-清除算法的消耗时间gc.ps_marksweep.time。这些数据均来自java.lang.management.GarbageCollectorMXBean。
httpsessions.*：Tomcat容器的会话使用情况。包括最大会话数httpsessions.max和活跃会话数httpsessions.active。该度量指标信息仅在引入了嵌入式Tomcat作为应用容器的时候才会提供。
gauge.*：HTTP请求的性能指标之一，它主要用来反映一个绝对数值。比如上面示例中的gauge.response.hello: 5，它表示上一次hello请求的延迟时间为5毫秒。
counter.*：HTTP请求的性能指标之一，它主要作为计数器来使用，记录了增加量和减少量。如上示例中counter.status.200.hello: 11，它代表了hello请求返回200状态的次数为11。


对于gauge.*和counter.*的统计，这里有一个特殊的内容请求star-star，它代表了对静态资源的访问。这两类度量指标非常有用，我们不仅可以使用它默认的统计指标，还可以在程序中轻松的增加自定义统计值。只需要通过注入org.springframework.boot.actuate.metrics.CounterService和org.springframework.boot.actuate.metrics.GaugeService来实现自定义的统计指标信息。比如：我们可以像下面这样自定义实现对hello接口的访问次数统计。

```java
@RestController
public class HelloController {
    
    @Autowired
    private CounterService counterService;

    @RequestMapping("/hello")
    public String greet() {
        counterService.increment("didispace.hello.count");
        return "";
    }
  
}
```

/metrics端点可以提供应用运行状态的完整度量指标报告，这项功能非常的实用，但是对于监控系统中的各项监控功能，它们的监控内容、数据收集频率都有所不同，如果我们每次都通过全量获取报告的方式来收集，略显粗暴。所以，我们还可以通过/metrics/{name}接口来更细粒度的获取度量信息，比如我们可以通过访问/metrics/mem.free来获取当前可用内存数量。

/health：该端点在一开始的示例中我们已经使用过了，它用来获取应用的各类健康指标信息。在spring-boot-starter-actuator模块中自带实现了一些常用资源的健康指标检测器。这些检测器都通过HealthIndicator接口实现，并且会根据依赖关系的引入实现自动化装配，比如用于检测磁盘的DiskSpaceHealthIndicator、检测DataSource连接是否可用的DataSourceHealthIndicator等。有时候，我们可能还会用到一些Spring Boot的Starter POMs中还没有封装的产品来进行开发，比如：当使用RocketMQ作为消息代理时，由于没有自动化配置的检测器，所以我们需要自己来实现一个用来采集健康信息的检测器。比如，我们可以在Spring Boot的应用中，为org.springframework.boot.actuate.health.HealthIndicator接口实现一个对RocketMQ的检测器类：

```java
@Component
public class RocketMQHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
          return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

  	private int check() {
     	// 对监控对象的检测操作
  	}
}
```

通过重写health()函数来实现健康检查，返回的Heath对象中，共有两项内容，一个是状态信息，除了该示例中的UP与DOWN之外，还有UNKNOWN和OUT_OF_SERVICE，可以根据需要来实现返回；还有一个详细信息，采用Map的方式存储，在这里通过withDetail函数，注入了一个Error Code信息，我们也可以填入一下其他信息，比如，检测对象的IP地址、端口等。重新启动应用，并访问/health接口，我们在返回的JSON字符串中，将会包含了如下信息：

```json
"rocketMQ": {
  "status": "UP"
}
```

/dump：该端点用来暴露程序运行中的线程信息。它使用java.lang.management.ThreadMXBean的dumpAllThreads方法来返回所有含有同步信息的活动线程详情。

/trace：该端点用来返回基本的HTTP跟踪信息。默认情况下，跟踪信息的存储采用org.springframework.boot.actuate.trace.InMemoryTraceRepository实现的内存方式，始终保留最近的100条请求记录。它记录的内容格式如下：
```
[
    {
        "timestamp": 1482570022463,
        "info": {
            "method": "GET",
            "path": "/metrics/mem",
            "headers": {
                "request": {
                    "host": "localhost:8881",
                    "connection": "keep-alive",
                    "cache-control": "no-cache",
                    "user-agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36",
                    "postman-token": "9817ea4d-ad9d-b2fc-7685-9dff1a1bc193",
                    "accept": "*/*",
                    "accept-encoding": "gzip, deflate, sdch",
                    "accept-language": "zh-CN,zh;q=0.8"
                },
                "response": {
                    "X-Application-Context": "hello:dev:8881",
                    "Content-Type": "application/json;charset=UTF-8",
                    "Transfer-Encoding": "chunked",
                    "Date": "Sat, 24 Dec 2016 09:00:22 GMT",
                    "status": "200"
                }
            }
        }
    },
    ...
]
```

#### 操作控制类

仔细的读者可能会发现，我们在“初识Actuator”时运行示例的控制台中输出的所有监控端点，已经在介绍应用配置类端点和度量指标类端点时都讲解完了。那么还有哪些是操作控制类端点呢？实际上，由于之前介绍的所有端点都是用来反映应用自身的属性或是运行中的状态，相对于操作控制类端点没有那么敏感，所以他们默认都是启用的。而操作控制类端点拥有更强大的控制能力，如果要使用它们的话，需要通过属性来配置开启。

在原生端点中，只提供了一个用来关闭应用的端点：/shutdown。我们可以通过如下配置开启它：

`endpoints.shutdown.enabled=true`

在配置了上述属性之后，只需要访问该应用的/shutdown端点就能实现关闭该应用的远程操作。`由于开放关闭应用的操作本身是一件非常危险的事`，所以真正在线上使用的时候，我们需要对其加入一定的保护机制，比如：定制Actuator的端点路径、整合Spring Security进行安全校验等。

### 自定义 - 重点

上面讲了很多都是配置相关，以及自带的一些端点，在实际应用中有时候默认并不能满足我们的要求，比如Spring Boot默认的健康端点就很有可能不能满足

#### 默认装配 HealthIndicators

下列是依赖spring-boot-xxx-starter后相关HealthIndicator的实现（通过management.health.defaults.enabled 属性可以禁用它们），但想要获取一些额外的信息时，自定义的作用就体现出来了…


名称	描述
CassandraHealthIndicator	检查 Cassandra 数据库是否启动。
DiskSpaceHealthIndicator	检查磁盘空间不足。
DataSourceHealthIndicator	检查是否可以获得连接 DataSource。
ElasticsearchHealthIndicator	检查 Elasticsearch 集群是否启动。
InfluxDbHealthIndicator	检查 InfluxDB 服务器是否启动。
JmsHealthIndicator	检查 JMS 代理是否启动。
MailHealthIndicator	检查邮件服务器是否启动。
MongoHealthIndicator	检查 Mongo 数据库是否启动。
Neo4jHealthIndicator	检查 Neo4j 服务器是否启动。
RabbitHealthIndicator	检查 Rabbit 服务器是否启动。
RedisHealthIndicator	检查 Redis 服务器是否启动。
SolrHealthIndicator	检查 Solr 服务器是否已启动。

application.properties相关配置:
```
# 启用基础认证
security.basic.enabled = true

# 如果加入了security依赖，则所有的接口默认都需要被验证，如果只想 /admin路径下的请求进行验证，则需要加入配置
security.basic.path = /admin

# 认证使用的用户名
security.user.name = admin

# 认证使用的密码。 默认情况下，启动时会记录随机密码。
security.user.password = 123456

# 可以访问管理端点的用户角色列表，逗号分隔
management.security.roles = SUPERUSER

# actuator暴露接口使用的端口，为了和api接口使用的端口进行分离
management.port = 8099

# actuator暴露接口的前缀
management.context-path = /admin

# actuator是否需要安全保证
management.security.enabled = true

# actuator的metrics接口是否需要安全保证
endpoints.metrics.sensitive = false

# actuator的metrics接口是否开启
endpoints.metrics.enabled=true

# actuator的health接口是否需要安全保证
endpoints.health.sensitive=false

# actuator的health接口是否开启
endpoints.health.enabled=true
```
