# Thymeleaf使用

    导入thymeleaf的名称空间: <html lang="en" xmlns:th="http://www.thymeleaf.org">

# 语法规则

    1）th:text；改变当前元素里面的文本内容；
    
    th：任意html属性；来替换原生属性的值,比如:
    <div id="div01" th:id="${hello}"></div> ,如果${hello}有值,会用这个值替换div01.

![](../pics/thmeleaf属性和jsp的对比.png)

    2）表达式有几类？ 

```properties
Simple expressions:（表达式语法）
        Variable Expressions: ${...}：获取变量值；OGNL；
        1）、获取对象的属性、调用方法
        2）、使用内置的基本对象：
            #ctx : the context object.
            #vars: the context variables.
            #locale : the context locale.
            #request : (only in Web Contexts) the HttpServletRequest object.
            #response : (only in Web Contexts) the HttpServletResponse object.
            #session : (only in Web Contexts) the HttpSession object.
            #servletContext : (only in Web Contexts) the ServletContext object.
            ${session.foo}
    
        3）、内置的一些工具对象：
            #execInfo : information about the template being processed.
            #messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
            #uris : methods for escaping parts of URLs/URIs
            #conversions : methods for executing the configured conversion service (if any).
            #dates : methods for java.util.Date objects: formatting, component extraction, etc.
            #calendars : analogous to #dates , but for java.util.Calendar objects.
            #numbers : methods for formatting numeric objects.
            #strings : methods for String objects: contains, startsWith, prepending/appending, etc.
            #objects : methods for objects in general.
            #bools : methods for boolean evaluation.
            #arrays : methods for arrays.
            #lists : methods for lists.
            #sets : methods for sets.
            #maps : methods for maps.
            #aggregates : methods for creating aggregates on arrays or collections.
            #ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).

    Selection Variable Expressions: *{...}：选择表达式：和${}在功能上是一样；
    	补充：配合 th:object="${session.user}：
   <div th:object="${session.user}">
    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div>
    
    Message Expressions: #{...}：获取国际化内容
    Link URL Expressions: @{...}：定义URL；
    		@{/order/process(execId=${execId},execType='FAST')}
    Fragment Expressions: ~{...}：片段引用表达式
    		<div th:insert="~{commons :: main}">...</div>
    		
Literals（字面量）
      Text literals: 'one text' , 'Another one!' ,…
      Number literals: 0 , 34 , 3.0 , 12.3 ,…
      Boolean literals: true , false
      Null literal: null
      Literal tokens: one , sometext , main ,…
Text operations:（文本操作）
    String concatenation: +
    Literal substitutions: |The name is ${name}|
Arithmetic operations:（数学运算）
    Binary operators: + , - , * , / , %
    Minus sign (unary operator): -
Boolean operations:（布尔运算）
    Binary operators: and , or
    Boolean negation (unary operator): ! , not
Comparisons and equality:（比较运算）
    Comparators: > , < , >= , <= ( gt , lt , ge , le )
    Equality operators: == , != ( eq , ne )
Conditional operators:条件运算（三元运算符）
    If-then: (if) ? (then)
    If-then-else: (if) ? (then) : (else)
    Default: (value) ?: (defaultvalue)
Special tokens:
    No-Operation: _ 
```

# thymeleaf公共页面元素抽取

```html
<!--抽取公共片段,命名为copy-->
<div th:fragment="copy">&copy; 2011 The Good Thymes Virtual Grocery</div>

<!--引入名字为copy的公共片段,其中公共片段所在的页面为footer.html-->
<div th:insert="~{footer::copy}"></div>
<!-- 或者 -->
<div th:insert="footer::copy"></div>
<!--如果使用th:insert等属性进行引入，可以不用写~{}：-->
```

    ~{templatename::selector}：模板名::选择器
    ~{templatename::fragmentname}:模板名::片段名
    
    行内写法可以加上：[[~{}]];[(~{})]；

# 三种引入公共片段的th属性

    th:insert:将公共片段整个插入到声明引入的元素中
    th:replace：将声明引入的元素替换为公共片段
    th:include：将被引入的片段的内容包含进这个标签中

```html
<span th:fragment="copy">&copy; 2011 The Good Thymes Virtual Grocery</span>

<!--引入方式-->
<div th:insert="footer::copy"></div>
<div th:replace="footer::copy"></div>
<div th:include="footer::copy"></div>

<!--效果分别是:-->
<div><span>&copy; 2011 The Good Thymes Virtual Grocery</span></div>

<span>&copy; 2011 The Good Thymes Virtual Grocery</span>

<div>&copy; 2011 The Good Thymes Virtual Grocery</div>
```
