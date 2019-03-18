## Spring Boot Web页面设计

> 示例项目: webjar_demo

在之前的示例中，我们都是通过@RestController来处理请求，所以返回的内容为json对象。那么如果需要渲染html页面的时候，要如何实现呢？

### 模板引擎

在动态HTML实现上Spring Boot依然可以完美胜任，并且提供了多种模板引擎的默认配置支持,Spring Boot提供了默认配置的模板引擎主要有以下几种：

1. Thymeleaf
2. FreeMarker
3. Velocity
4. Groovy
5. Mustache

>Spring Boot建议使用这些模板引擎，避免使用JSP，若一定要使用JSP将无法实现Spring Boot的多种特性

当你使用上述模板引擎中的任何一个，它们默认的模板配置路径为：src/main/resources/templates。当然也可以修改这个路径，具体如何修改，可在后续各模板引擎的配置属性中查询并修改。

#### Thymeleaf基础概述

在传统的Spring Boot MVC项目中，需要在Server端使用特定的视图模板，基于这些模板“填空”以得到一张完整的HTML网页，再把它传回给浏览器。负责完成这一“填空” 工作的， 是“模板引擎”。

Thymeleaf是Spring Boot Web项目可用的诸多视图引擎中的一种，Spring官方推荐使用它(而不是历史更为悠久的JSP）来编写视图模板。Thymeleaf是一个XML/XHTML/HTML5模板引擎，可用于Web与非Web环境中的应用开发。它是一个开源的Java库.

Thymeleaf提供了一个用于整合Spring MVC的可选模块，在应用开发中，你可以使用Thymeleaf来完全代替JSP或其他模板引擎，如Velocity、FreeMarker等。Thymeleaf的主要目标在于提供一种可被浏览器正确显示的、格式良好的模板创建方式，因此也可以用作静态建模。你可以使用它创建经过验证的XML与HTML模板。相对于编写逻辑或代码，开发者只需将标签属性添加到模板中即可。接下来，这些标签属性就会在DOM(文档对象模型）上执行预先制定好的逻辑。

示例模板：

```html
<table>
  <thead>
    <tr>
      <th th:text="#{msgs.headers.name}">Name</td>
      <th th:text="#{msgs.headers.price}">Price</td>
    </tr>
  </thead>
  <tbody>
    <tr th:each="prod : ${allProducts}">
      <td th:text="${prod.name}">Oranges</td>
      <td th:text="${#numbers.formatDecimal(prod.price,1,2)}">0.99</td>
    </tr>
  </tbody>
</table>
```

>可以看到Thymeleaf主要以属性的方式加入到html标签中，浏览器在解析html时，当检查到没有的属性时候会忽略，所以Thymeleaf的模板可以通过浏览器直接打开展现，这样非常有利于前后端的分离。

Thymeleaf提供了诸如循环、条件判断、样式处理等手段，可以方便地控制HTML代码的生成过程，既可以用于在Server端生成全部HTML页面的传统Web应用，也可用于开发“单页面应用(SPA： Single PageApplication）”类型的现代Web应用。

#### Thymeleaf的默认参数配置

如有需要修改默认配置的时候，只需复制下面要修改的属性到application.properties中，并修改成需要的值，如修改模板文件的扩展名，修改默认的模板路径等:

```
# Enable template caching.
spring.thymeleaf.cache=true 
# Check that the templates location exists.
spring.thymeleaf.check-template-location=true 
# Content-Type value.
spring.thymeleaf.content-type=text/html 
# Enable MVC Thymeleaf view resolution.
spring.thymeleaf.enabled=true 
# Template encoding.
spring.thymeleaf.encoding=UTF-8 
# Comma-separated list of view names that should be excluded from resolution.
spring.thymeleaf.excluded-view-names= 
# Template mode to be applied to templates. See also StandardTemplateModeHandlers.
spring.thymeleaf.mode=HTML5 
# Prefix that gets prepended to view names when building a URL.
spring.thymeleaf.prefix=classpath:/templates/ 
# Suffix that gets appended to view names when building a URL.
spring.thymeleaf.suffix=.html  spring.thymeleaf.template-resolver-order= # Order of the template resolver in the chain. spring.thymeleaf.view-names= # Comma-separated list of view names that can be resolved.
```

#### Spring Boot MVC支持的视图引擎

Spring Boot MVC支持多种视图模板引擎，能自动检测并配置好几种引擎。比较常用的(官方推荐）是Thymeleaf。

> 注意：在Spring早期Web项目中比较常见的JSP，Spring官方己经不再推荐在Spring
Boot项目中使用。

#### 控制器与模板之间的信息交换方式

      Model对象          生成
控制器 ------> 视图模板 -------> HTML网页

> Thymeleaf模板标签以“th:”打头，包容条件、循环等常用功能，请查询官网了解其语
法及技术细节。

#### 补充知识： Spring Boot Web项目中的静态资源

在我们开发Web应用的时候，需要引用大量的js、css、图片等静态资源。

Spring Boot默认提供静态资源目录位置需置于classpath下，目录名需符合如下规则：

* /static
* /public
* /resources
* /META-INF/resources

我们可以在src/main/resources/目录下创建static，在该位置放置一个图片文件。启动程序后，尝试访问http://localhost:808/D.jpg。如能显示图片，配置成功。

Thymleleaf的模板文件被视为静态资源，只不过它比较特殊，与普通的html，图片， js代表等常规静态资源不一样，需要经过模板引擎的处理之后再传给客户端罢了。

Spring Boot将放置在以下文件夹中的文件视为静态资源文件，并将其映射为一个唯一的URL：

* /static
* /public
* /resources
* /META-INF/resources

默认情况下， Thymeleaf从classpath:/templates/处加载模板

### WebJars基础

现代网页设计，离不开各种各样的Web前端UI库。各种UI库通常都会包容多个文件(比如.css， .js，字体，图标等）。

Spring Boot支持一种被称为webjar的技术，将一些前端框架(比如Bootstrap）打包为单个文件， 从而在Thymeleaf模板中可以作为一个整体进行引用。

使用WebJars打包的资源，其URL以“/webjars/”打头，也就是说，所有指向/webjars/的HTTP请求，都会被导向这个包中所包容的静态资源文件。

#### 获取webjar的maven依赖(以Bootstrap为例）

要想在Spring Boot Web项目中使用诸如Bootstrap等前端框架，需要访问webjars或
MavenRepository网站去搜索其Maven依赖声明。

添加完依赖之后，可以看到相关的js文件、 css样式表和字体等静态
资源，都被放到了jar包中，并且准备好了在视图模板文件中访问。

>http://www.webjars.org/, https://mvnrepository.com

#### 如何引用WebJars包？

特别注意其包名：`resources.webjars.jquery.1.11.1，`这实际上就是文件夹名，你可以这样看：所有jquery文件位于resources文件夹下的
/webjars/jquery/1.11.1下。

现在，就可以在网页中使用以下URL引用到它们：
```html
<script src="/webjars/jquery/1.11.1/jquery.min.js">
</script>
```

## 1.SpringBoot静态资源映射规则

~~~java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
                CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
                if (!registry.hasMappingForPattern("/webjars/**")) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
                }

                String staticPathPattern = this.mvcProperties.getStaticPathPattern();
                if (!registry.hasMappingForPattern(staticPathPattern)) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
                }

            }
        }

~~~

1、所有的/webjars/**,都去classpath:/META-INF/resources/webjars/找资源

webjars:以jar包的方式引入资源，网页搜索webjars,选择maven的方式引入，例如引入jquery

~~~xml
<dependency>
  <groupId>org.webjars</groupId>
  <artifactId>jquery</artifactId>
  <version>3.3.1</version>
</dependency>
~~~

访问jQuery.js的路径为：localhost:8080/webjars/jquery/3.3.1/jquery.js

2.“/**”访问当前项目的任何资源，（静态资源的文件夹）

~~~java
"classpath:/META-INF/resources/",
"classpath:/resources/",
"classpath:/static/",
"classpath:/public/"
"/":当前项目的根路径
~~~
