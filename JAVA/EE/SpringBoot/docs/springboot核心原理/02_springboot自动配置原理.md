# springboot自动配置原理(非常关键,是springboot的精华)

# springboot的配置文件到底能写什么？怎么写？springboot的自动配置原理是什么?

    1）SpringBoot启动的时候加载主配置类，开启了自动配置功能: @EnableAutoConfiguration
    2）、@EnableAutoConfiguration 作用：
        -  利用EnableAutoConfigurationImportSelector给容器中导入一些组件
        - 可以查看EnableAutoConfigurationImportSelector的selectImports()方法的内容；
        - List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);获取候选的配置

    A. SpringFactoriesLoader.loadFactoryNames(Class<?> factoryClass, @Nullable ClassLoader classLoader): 
    从类路径下load资源.这里的factoryClass就是EnableAutoConfiguration.
    
    B. 具体要做的就是扫描所有jar(注意是扫描所有的jar包)包类路径下  META-INF/spring.factories
    把扫描到的这些文件的内容包装成properties对象
    
    C. 从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后把他们添加在容器中
    
    D. 这里说白了就是将类路径下META-INF/Spring.factories里面所有EnableAutoConfiguration的值加入到容器中

```properties
# 这里只取一小部分
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration
```

    E. 每一个这样的  xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；用他们来做自动配置；

    3）每一个自动配置类进行自动配置功能；
    4）这里以**HttpEncodingAutoConfiguration（Http编码自动配置）为例解释自动配置原理；

```java
//表示这是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
@Configuration   
//启动指定类的ConfigurationProperties功能；将yml/properties配置文件中对应的值和HttpEncodingProperties绑定起来；并把HttpEncodingProperties加入到ioc容器中
@EnableConfigurationProperties(HttpEncodingProperties.class)  
//Spring底层@Conditional注解（Spring注解版），根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效；    
// 判断当前应用是否是web应用，如果是，当前配置类生效
@ConditionalOnWebApplication 
//判断当前项目有没有这个类CharacterEncodingFilter；SpringMVC中进行乱码解决的过滤器；
@ConditionalOnClass(CharacterEncodingFilter.class)  
//判断配置文件中是否存在某个配置,这里具体的是判断 "spring.http.encoding.enabled" 这个属性是否在配置文件中.
// matchIfMissing的意思: 指定如果未设置该属性(spring.http.encoding.enabled)，条件是否应匹配。matchIfMissing在这个注解中的默认
// 值为false,意思就是,如果没有配置该属性,这个条件默认是不匹配的.
// 如果matchIfMissing是true,如同下面这样的话,即使"spring.http.encoding.enabled"没有在配置文件中,这个条件依然是匹配的
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)  
public class HttpEncodingAutoConfiguration {
  
  	//他已经和SpringBoot的配置文件映射了,也就是配置文件中的相关属性已经加载到HttpEncodingProperties,可以直接使用了
  	private final HttpEncodingProperties properties;
  
   //只有一个有参构造器的情况下，参数的值就会从容器中拿
  	public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {
		this.properties = properties;
	}
  
    @Bean   //给容器中添加一个组件，这个组件的某些值需要从properties中获取
	@ConditionalOnMissingBean(CharacterEncodingFilter.class) //判断容器没有这个组件？
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.properties.getCharset().name());
		filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
		filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
		return filter;
	}
```

    根据当前不同的条件判断，决定这个配置类是否生效？
    一但这个配置类生效；这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的；

    5）、所有在配置文件中能配置的属性都是在xxxxProperties类中封装者‘；配置文件能配置什么就可以参照某个功能对应的这个属性类

```java
@ConfigurationProperties(prefix = "spring.http.encoding")  //从配置文件中获取指定的值和bean的属性进行绑定
public class HttpEncodingProperties {
   public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Boolean force; // 比如有这个属性,配置文件中就可以配置spring.http.encoding.force这个属性
}
```

# 精髓

​	**1）、SpringBoot启动会加载大量的自动配置类**,xxxxAutoConfigurartion.class：自动配置类；和 xxxxProperties.class:封装配置文件中相关属性；

​	**2）、我们看我们需要的功能有没有SpringBoot默认写好的自动配置类；**

​	**3）、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）**

​	**4）、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值；**
