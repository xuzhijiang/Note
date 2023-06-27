1.	<mvc:annotation-driven/>配置在什么时候必须配置？
①	直接配置响应的页面：无需经过控制器来执行结果 ；但会导致其他请求路径失效，需要配置mvc:annotation-driven标签
<mvc:view-controller path="/success" view-name="success"/>
②	RESTful-CRUD操作，删除时，通过jQuery执行delete请求时，找不到静态资源，需要配置mvc:annotation-driven标签   
<mvc:default-servlet-handler/> 将在 SpringMVC 上下文中定义一个
DefaultServletHttpRequestHandler，它会对进入 DispatcherServlet 的请求进行筛查，如果发现是没有经过映射的请求，就将该请求交由 WEB 应用服务器默认的 Servlet 处理，如果不是静态资源的请求，才由 DispatcherServlet 继续处理。
③	配置类型转换器服务时，需要指定转换器服务引用
<mvc:annotation-driven conversion-service=“conversionService”/> 会将自定义的
ConversionService 注册到 Spring MVC 的上下文中
④	后面完成JSR 303数据验证，也需要配置 

# 1.	关于 <mvc:annotation-driven /> 作用

<mvc:annotation-driven /> 会自动注册：
  RequestMappingHandlerMapping 、RequestMappingHandlerAdapter 与
  ExceptionHandlerExceptionResolver  三个bean。
还将提供以下支持：
    支持使用 ConversionService 实例对表单参数进行类型转换
    支持使用 @NumberFormat、@DateTimeFormat 注解完成数据类型的格式化
    支持使用 @Valid 注解对 JavaBean 实例进行 JSR 303 验证
    支持使用 @RequestBody 和 @ResponseBody 注解
    结合源码分析（在bean对象的set方法上设置断点进行调试）
 既没有配置 <mvc:default-servlet-handler/> 也没有配置 <mvc:annotation-driven/>
   
  都没有配置情况下，AnnotationMethodHandlerAdapter是默认出厂设置，干活的(过期)。
  另外：conversionService是null(类型转换器是不起作用的)
  四月 30, 2016 3:52:21 下午 org.springframework.web.servlet.PageNotFound noHandlerFound
  警告: No mapping found for HTTP request with URI [/SpringMVC_03_RESTFul_CRUD/scripts/jquery-1.9.1.min.js] in DispatcherServlet with name 'springDispatcherServlet'
  ②	配置了 <mvc:default-servlet-handler/>  但没有配置 <mvc:annotation-driven/>
   
  AnnotationMethodHandlerAdapter被取消，解决了静态资源查找，但是@RequestMapping不好使了。
  ③	既配置了 <mvc:default-servlet-handler/>  又配置 <mvc:annotation-driven/>【重要】
   
  AnnotationMethodHandlerAdapter被替换成RequestMappingHandlerAdapter来干活了。
  如果没有配置<mvc:annotation-driven/>标签时，conversionService为null.
   
  AnnotationMethodHandlerAdapter已经过时，Spring3.2推荐RequestMappingHandlerAdapter来替代。所以说，默认情况下，没有配置这两个配置时，HelloWorld 程序可以正常运行，但是，涉及到静态资源查找的时候，就必须配置这个<mvc:annotation-driven/>配置了
   