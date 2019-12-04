# thymeleaf

直接打开html页面展现html页面，但是渲染后,是渲染后页面,做到了不破坏HTML自身内容的数据逻辑分离,所以比jsp好.

## 2.SpringBoot引入thymeleaf

1、pom.xml文件中引入thymeleaf

~~~xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
~~~

2、默认使用的thymeleaf版本低，修改版本

~~~xml
<properties>
  <thymeleaf.version>3.0.2.RELEASE</thymeleaf.version>
  <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>
</properties>
~~~

# Thymeleaf

Thymleleaf模板后缀也是html比较特殊，与普通的html，图片， js代表等常规静态资源不一样，需要经过模板引擎的处理之后再传给客户端罢了。

默认情况下， Thymeleaf从classpath:/templates/处加载模板.

Thymeleaf不是SpringBoot默认的模板引擎,需要引入Thymeleaf依赖才可以使用,不像内嵌的tomcat容器,是spring-boot-start-web中默认就自带了tomcat依赖.

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

## 3.Thymeleaf基本使用

~~~java
@ConfigurationProperties(
    prefix = "spring.thymeleaf"
)
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING;
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
    private boolean checkTemplate = true;
    private boolean checkTemplateLocation = true;
    private String prefix = "classpath:/templates/";
    private String suffix = ".html";
  //只要我们将HTML页面存放在classpath:/templates/目录中，thymeleaf就能自动渲染
~~~

注意：thymeleaf能渲染html页面，在Controller使用注解@Controller，不能使用@RestController注解。

使用thymeleaf方法如下：

1. html页面引入thymeleaf域名空间
2. Controller类中收发请求和传递数据

~~~html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>成功页面</title>
</head>
<body>
    <h1>成功页面</h1>
    <!--th:text 将div里面的文本内容设置为指定的值-->
    <div th:text="${hello}"></div>
</body>
</html>
~~~

~~~java
@Controller
public class HelloController {

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","你好");
        return "success";
    }
}
~~~

## 4.Thymeleaf语法

1、th:text:改变当前元素的文本内容的；可以使用th:任意html属性：来替换原生属性的值

~~~html
 <div id="test" class="test" th:id="${hello}" th:class="${hello}" th:text="${hello}"></div>
~~~

~~~html
 <div id="你好" class="你好">你好</div>
~~~

| Order | Feature   | Attributes                               |
| ----- | --------- | ---------------------------------------- |
| 1     | 片段包含      | `th:insert``th:replace`                  |
| 2     | 遍历        | `th:each`                                |
| 3     | 条件判断      | `th:if``th:unless``th:switch``th:case`   |
| 4     | 声明变量      | `th:object``th:with`                     |
| 5     | 任意属性修改    | `th:attr``th:attrprepend``th:attrappend` |
| 6     | 修改指定属性默认值 | `th:value``th:href``th:src``...`         |
| 7     | 修改标签体内容   | `th:text（转义特殊字符）th:utext（不转义）`           |
| 8     | 声明片段      | `th:fragment`                            |
| 9     | 移除片段      | `th:remove`                              |

2、表达式语法（参考thymeleaf官方文档第四章）

~~~properties
Simple expressions:(表达式语法)
  一、Variable Expressions: ${...}:获取变量值，OGNL
    1、获取对象的属性，调用方法
      ${person.father.name}
      ${person['father']['name']}
      ${countriesByCode.ES}
      ${personsByName['Stephen Zucchini'].age}
      ${personsArray[0].name}
      ${person.createCompleteName()}
      ${person.createCompleteNameWithSeparator('-')}
    2、使用内置的对象（使用方法参见官方文档第四章的附录）
      #ctx: the context object.
      #vars: the context variables.
      #locale: the context locale.
      #request: (only in Web Contexts) the HttpServletRequest object.
      #response: (only in Web Contexts) the HttpServletResponse object.
      #session: (only in Web Contexts) the HttpSession object.
      #servletContext: (only in Web Contexts) the ServletContext object.
     3、内置工具对象（使用方法参见官方文档第四章的附录）
      #execInfo: 
        #messages: 
        #uris:
        #conversions:
        #dates: 
        #calendars: 
        #numbers: methods for formatting numeric objects.
        #strings:
        #objects: methods for objects in general.
        #bools: methods for boolean evaluation.
        #arrays: methods for arrays.
        #lists: methods for lists.
        #sets: methods for sets.
        #maps: methods for maps.
        #aggregates: 
        #ids:
 二、 Selection Variable Expressions: *{...}：选择表达式，功能和${}一样
    <div th:object="${session.user}">
      <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
      <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
  配合 th:object="${session.user}"使用，*相当于th:object
 三、Message Expressions: #{...}：获取国际化内容
 四、Link URL Expressions: @{...}：定义URL链接
    @{/order/process(execId=${execId},execType='FAST')}
 五、Fragment Expressions: ~{...}：片段引用
Literals（字面量）
  Text literals: 'one text', 'Another one!',…
  Number literals: 0, 34, 3.0, 12.3,…
  Boolean literals: true, false
  Null literal: null
  Literal tokens: one, sometext, main,…
Text operations:（文本操作）
  String concatenation: +
  Literal substitutions: |The name is ${name}|
Arithmetic operations:（数学运算）
  Binary operators: +, -, *, /, %
  Minus sign (unary operator): -
Boolean operations:（布尔运算）
  Binary operators: and, or
  Boolean negation (unary operator): !, not
Comparisons and equality:（比较运算）
Comparators: >, <, >=, <= (gt, lt, ge, le)
Equality operators: ==, != (eq, ne)
Conditional operators:（条件运算，三元运算符）
  If-then: (if) ? (then)
  If-then-else: (if) ? (then) : (else)
  Default: (value) ?: (defaultvalue)
~~~

## 5.thymeleaf基本使用

controller数据准备如下：

~~~java
@RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","<h1>你好</h1>");
        map.put("list" , Arrays.asList("张三","李四","王五"));
        return "success";
    }
~~~

~~~html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>成功页面</title>
</head>
<body>
    <h1>成功页面</h1>
    <!--th:text 将div里面的文本内容设置为指定的值-->
    <div id="test" class="test" th:id="${hello}" th:class="${hello}" th:text="${hello}"></div>
    <span th:text="${#locale.country}">US</span>
    <hr>
    <div th:text="${hello}"></div><!--会转义输出<h1>你好</h1>-->
    <div th:utext="${hello}"></div><!--不会转义-->
    <hr>
    <!--th:each每次遍历都会生成当前这个标签，三个h4标签-->
    <h4 th:text="${li}" th:each="li:${list}"></h4>
    <hr>
    <h4>
        <!--高级写法：行内写法：[[...]] or [(...)]相当于th:text和th:utext-->
        <span th:each="user:${list}">[[${user}]]</span><!--三个span标签-->
    </h4>
</body>
</html>
~~~
