@RestController,RequestMapping以及一些参数绑定的注解：@PathVariable,@ModelAttribute,@RequestParam:

* @RestController：Spring4之后加入的注解，原来在@Controller中返回json需要@ResponseBody来配合，如果直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式。
* @RequestMapping：配置url映射
* @PathVariable:url中的参数可通过@PathVariable绑定到函数的参数中

通过@RestController来处理请求，所以返回的内容为json对象.

@RequestMapping注释用于将请求URI映射到处理程序方法。
我们还可以指定客户端应用程序应该使用的HTTP方法来调用rest方法(to invoke the rest method)。

@ResponseBody注释用于映射response对象中的response体。
一旦handler method返回响应对象，MappingJackson2HttpMessageConverter
就会启动(kicks in)并将其转换为JSON响应。

@RequestBody注释用于将"请求主体JSON数据(request body JSON data)"映射到Employee对象，
这也是由MappingJackson2HttpMessageConverter映射完成的。
again this is done by the MappingJackson2HttpMessageConverter mapping.

@PathVariable注释是从rest URI中提取数据并将其映射到method argument的简单方法。

#### @JsonView的应用场景

1. 在实际开发中，有些数据实体类包容相当多的属性，但在特定的场景中，往往并不需要那么多的属性，只要几个就好了。
2. 通过在数据实体类和控制器中使用@JsonView注解，能够让我们针对特定的场景从数据实体类的属性集合中仅返回那些对于客户端应用来说“有用的”属性数据。
3. Spring将数据转换的结果统称为“View”，生成的Json数据也是“View”。

#### @RequestMapping注解

* ContentType: 用来告诉服务器当前发送的数据是什么格式 
* Accept: 用来告诉服务器，客户端能认识哪些格式,最好返回这些格式中的其中一种 
* @RequestMapping(produces={"application/json","application/xml"}, consumes="text/xml") -- consumes用来限制ContentType,produces 用来限制Accept.

有个用户发了一个请求, 请求头中:

ContentType =application/json 
Accept      =  */*  

但是我的接口中定义了consumes={"application/xml"},我只接收application/xml 格式，也只返回xml格式.很明显，用户调不通这个接口.

>@RequestMapping可以应用于"控制器类"和"方法"。 

#### @Configuration

@PostConstruct: Spring bean对象构造完后调用.

@PreDestroy: Spring bean对象销毁前调用

@Value("${age}")获取配置文件中的属性值

@RestController处理Http请求，返回JSON格式的数据.

使用多个URL访问同一个的方法，可以将URL映射配置为一个集合:@RequestMapping(value = {"/hello","/hi"},method = RequestMethod.GET)

1. @PathVariable获取URL中的数据
2. @RequestParam获取请求参数的值
3. @GetMapping组合注解

@RequestParam注解也可以使用默认参数值，和要求是否必传:public String hello(@RequestParam(value = "id",required = false,defaultValue = "0") Integer id){},

@Transactional标注在某个方法上表示这个方法是要进行事务管理,事务就是多条操作同时成功或者失败.

使用SpringBootApplication注解相当于使用了3个注解，`@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan`

a. 由于@Configuration注释，它会扫描@Bean方法来创建beans
    b. 由于@ComponentScan注释，它执行组件扫描(组件表示使用@Component，@Service，@Repository，@Controller等注释的Bean）。
    c. 由于@EnableAutoConfiguration注释，它会触发Spring Boot Auto-Configuration

比如你的项目的包路径是 com.spring.core，对应的controller和repository包是 com.spring.core.controller和com.spring.core.repository。 那么这个SpringApplication的包路径必须为com.spring.core。 因为SpringBootApplication注解内部是使用ComponentScan注解，这个注解会扫描SpringApplication包所在的路径下的各个bean。

启动指定类的ConfigurationProperties功能:@EnableConfigurationProperties({HttpEncodingProperties.class})

@ConditionalOnClass({CharacterEncodingFilter.class})
//判断当前项目中有没有CharacterEncodingFilter这个类

@ConditionalOnWebApplication(//Spring底层有@conditiona注解，根据不同的条件，如果满足指定的条件才会让配置类中的配置就会生效，判断当前应用是否为web应用。
    type = Type.SERVLET
)
@ConditionalOnClass({CharacterEncodingFilter.class})
//判断当前项目中有没有CharacterEncodingFilter这个类
@ConditionalOnProperty(//判断配置文件中是否存在某个配置spring.http.encoding
    prefix = "spring.http.encoding",
    value = {"enabled"},
    matchIfMissing = true
)

关于 @RestController和@RequestMapping ("/" )，不是SpringBoot 中的内容，是SpringMVC中的内容。

@EnableAutoConfiguration 注解和SpringApplication是 SpringBoot中提供的注解

1)其中@EnableAutoConfiguration 注解的作用是让 SpringBoot来“猜测” 如何配置 Spring。猜测的依据很简单，就是根据依赖的jar包。因为在我们的依赖中，包含了 Tomcat和SpringMvc ，因此SpringBoot的自动配置机制就会认为这是一个web应用。
因此在main方法run后，就启动 tomcat，让用户通过url来访问。

org.springframework.context.annotation.Primary注解(@Primary):声明默认的，首要的bean(在没有具体指明名字的时候使用).

@Import(value = { LoginSecurityConfig.class })