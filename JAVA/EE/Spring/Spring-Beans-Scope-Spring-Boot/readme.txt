Spring Bean Scopes允许我们更精细地控制bean实例的创建。 有时我们想要将bean实例创建为单例，
但在其他一些情况下，我们可能希望在每个请求都创建一次  or 一次会话中创建一次。

Spring Bean Scopes

Spring bean范围有五种类型：

1. singleton - 将为spring容器创建一个spring bean实例。 
这是默认的spring bean范围。 使用此范围时，请确保bean没有共享实例变量，否则可能导致数据不一致问题。

2. prototype - 每次从spring容器请求bean时，都会创建一个新实例

3. request - 这与原型范围相同，但它意味着用于Web应用程序。将为每个HTTP请求创建一个新的bean实例。

4. session - 将通过容器为每个HTTP session创建一个新bean。

5. global-session  - 用于为Portlet应用程序创建全局session bean。

--------------------------------------------------------------------------

Spring Bean Request and Session Scope

对于spring bean请求和会话范围示例，我们将创建Spring Boot Web应用程序。 
创建一个spring boot starter project 并选择“web”，以便我们可以将其作为Web应用程序运行。

"ServletInitializer"和"SpringBootMvcApplication"
是自动生成的spring引导类。 我们不需要在那里做任何改变。

------------------------------------------------------------------------------

Spring Bean Request Scope Test

打开浏览器并转到URL: http://localhost:8080/nameRS并检查控制台输出。
 您应该看到DataRequestScope Constructor Called在每个请求上打印。
 
------------------------------------------------------------------------------

Spring Bean Session Scope Test

Go to http://localhost:8080/nameSS and you would get following output:

`Session Scope`

现在转到http：// localhost：8080 / nameSSUpdated，
以便将DataSessionScope name value(名称值)更新为Session Scope Updated:

`Session Scope Updated`

现在再次转到http://localhost:8080/nameSS，您应该看到更新的值：

`Session Scope Updated`

到这时，您应该在控制台输出中仅看到一次: DataSessionScope Constructor Called at XXX

现在等待1分钟，以便我们的session scoped bean(会话范围bean)失效了。 
然后再次转到http：// localhost：8080 / nameSS，您应该看到原始输出（DataSessionScope Constructor Called at XXX）。
 此外，您应该检查控制台消息，以便容器再次创建DataSessionScope。
