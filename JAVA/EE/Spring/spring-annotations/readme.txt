Spring Annotations

1. Spring框架实现 并 promotes了"控制反转（IOC）"或"依赖注入（DI）"的原理，实际上是一个IOC容器。
2. 传统上，Spring允许开发人员使用 "基于XML的配置" 来管理bean依赖关系。
3. 还有另一种方法来定义bean及它们的依赖项。 此方法是基于Java的配置。
4. 与XML方法不同，基于Java的配置允许您以"编程方式"管理bean组件。 这就是为什么引入Spring annotations的原因


---------------------------------------------------------------

Spring Annotations List:

1. @Configuration
2. @Bean
3. @PreDestroy & @PreDestroy
4. @ComponentScan
5. @Component
6. @PropertySource
7. @Service
8. @Repository
9. @Autowired

// @Repository：表示带注释的类是“存储库(Repository)”。
// 此注释用作@Component的特化，并且建议与DAO类一起使用(advisable to use with DAO classes.)。

----------------------------------------------------------------------

Spring MVC Annotations:

@Controller
@RequestMapping
@PathVariable
@RequestParam
@ModelAttribute
@RequestBody and @ResponseBody
@RequestHeader and @ResponseHeader

------------------------------------------------------------------------

Spring Transaction Management Annotations

@Transactional是Spring declarative(声明式) 事务管理注释

Spring Security Annotations

@EnableWebSecurity与@Configuration类一起使用以定义Spring Security configuration

Spring Boot Annotations

@SpringBootApplication
@EnableAutoConfiguration

-------------------------------------------------------------------------
