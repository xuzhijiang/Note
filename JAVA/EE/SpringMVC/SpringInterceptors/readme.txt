## Spring MVC Interceptor HandlerInterceptorAdapter, HandlerInterceptor Example

Spring Interceptor用于拦截客户端请求并处理它们。
有时我们想拦截HTTP请求 并在 将其交给 "控制器处理程序方法之前" 进行一些处理。
这就是Spring MVC拦截器派上用场的地方。

### Spring Interceptor

就像我们有Struts2拦截器一样，我们可以通过实现org.springframework.web.servlet.HandlerInterceptor接口
或覆盖抽象类org.springframework.web.servlet.handler.HandlerInterceptorAdapter
来创建我们自己的Spring拦截器，它提供了HandlerInterceptor接口的基本实现。

#### Spring Interceptor  -  HandlerInterceptor

Spring HandlerInterceptor根据我们想在哪里拦截HTTP请求声明三种方法：

1. boolean preHandle（HttpServletRequest request，HttpServletResponse response，Object handler）:
此方法用于在请求移交给处理程序方法之前拦截请求。这个方法应该返回'true'让Spring知道通过另一个Spring拦截器处理请求，或者如果没有其他弹簧拦截器则将它发送到处理程序方法。
如果此方法返回'false'，则Spring框架假定请求已由spring拦截器本身处理，并且不需要进一步处理。在这种情况下，我们应该使用响应对象来发送对客户端请求的响应。

对象处理程序是处理请求的选定处理程序对象。此方法也可以抛出异常，在这种情况下，Spring MVC异常处理对于将错误页面作为响应发送应该很有用。

void postHandle（HttpServletRequest请求，HttpServletResponse响应，Object处理程序，ModelAndView modelAndView）：当HandlerAdapter调用处理程序但DispatcherServlet尚未呈现视图时，将调用此HandlerInterceptor拦截器方法。此方法可用于向要在视图页面中使用的ModelAndView对象添加其他属性。我们可以使用这个spring拦截器方法来确定处理程序方法处理客户端请求所花费的时间。
void afterCompletion（HttpServletRequest请求，HttpServletResponse响应，对象处理程序，Exception ex）：这是一个HandlerInterceptor回调方法，在执行处理程序并呈现视图时调用该方法。
如果配置了多个弹簧拦截器，则按配置顺序执行preHandle（）方法，而以相反顺序调用postHandle（）和afterCompletion（）方法。

让我们创建一个简单的Spring MVC应用程序，我们将配置一个Spring Interceptor来记录控制器处理程序方法的时序。

我们的最终Spring Interceptor示例项目将如下图所示，我们将研究我们感兴趣的组件。