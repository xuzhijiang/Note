## Spring MVC Exception Handling – @ControllerAdvice, @ExceptionHandler, HandlerExceptionResolver

Spring MVC异常处理对于确保您不向客户端发送服务器异常非常重要。
今天我们将使用@ExceptionHandler，@ ControllerAdvice和HandlerExceptionResolver
来研究Spring异常处理。任何Web应用程序都需要良好的异常处理设计，因为
当我们的应用程序抛出任何未处理的异常时，我们不希望提供容器生成的页面。

### Spring异常处理

Spring MVC Framework提供了以下方法来帮助我们实现强大的异常处理：

1. Controller Based  - 我们可以在控制器类(controller classes)中定义异常处理程序方法。
我们所需要的只是使用@ExceptionHandler注释来注释这些方法。此批注将Exception类作为参数。
因此，如果我们为Exception类定义了其中一个，那么我们的请求处理程序方法(request handler method)抛出的所有异常都将被处理。

These exception handler methods are just like other request handler methods
这些异常处理程序方法就像其他请求处理程序方法一样，我们可以构建错误响应 并用不同的错误页面来做出响应(给client)。
我们还可以发送JSON错误响应，稍后我们将在示例中查看。

如果定义了多个异常处理程序方法，(then handler method that is closest to the Exception class is used.)
则使用最接近Exception类的处理程序方法。例如，如果我们为IOException和Exception定义了两个处理程序方法，
并且我们的请求处理程序方法抛出IOException，那么将执行IOException的处理程序方法。

2. Global Exception Handler - 异常处理是一个跨领域的问题，应该对我们的应用程序中的所有切入点进行处理。
我们已经研究过Spring AOP，这就是为什么Spring提供@ControllerAdvice注释，我们可以使用它来定义我们的全局异常处理程序。
Global Controller Advice中的处理程序方法与基于Controller的异常处理程序方法相同，并且在控制器类无法处理异常时使用。

3. HandlerExceptionResolver - 对于一般异常，大多数时候我们都提供静态页面。 
Spring Framework提供了HandlerExceptionResolver接口，我们可以实现它来创建全局异常处理程序。
这种额外定义全局异常处理程序的原因是Spring框架还提供了我们可以在spring bean配置文件中定义的默认实现类，
以获得spring框架异常处理的好处。

SimpleMappingExceptionResolver是默认的实现类，它允许我们配置exceptionMappings，
我们可以在其中指定用于特定异常的资源。我们还可以覆盖它以使用我们的应用程序特定更改创建自己的全
局处理程序，例如记录异常消息。

让我们创建一个Spring MVC项目，我们将在其中研究基于Controller，基于AOP和基于异常解析器的异常和错误处理方法的实现。我们还将编写一个异常处理程序方法，该方法将返回JSON响应。如果您是Spring中的JSON新手，请阅读Spring Restful JSON Tutorial。

我们的最终项目将如下图所示，我们将逐一查看应用程序的所有组件。