#### GenericServlet

GenericServlet实现了Servlet和ServletConfig接口，并
完成以下任务：

* 将init方法中的ServletConfig赋给一个类级变量，以便可以通过调用getServletConfig获取。
* 为Servlet接口中的所有方法提供默认的实现。
* 提供方法，包围ServletConfig中的方法

自定义的Servlet，可以直接派生自GenericServlet，并可选重写其init()方法(注意要选没有参数的那个，以保证ServletConfig字段肯定有值）

GenericServlet是一个实现Servlet，ServletConfig和Serializable接口的抽象类。 
GenericServlet提供了所有Servlet生命周期方法和ServletConfig方法的默认实现，
并且在扩展此类时使我们的生活更轻松，我们只需要覆盖我们想要的方法，其余的我们可以使用默认实现。
此类中定义的大多数方法仅用于轻松访问Servlet和ServletConfig接口中定义的公共方法。

GenericServlet类中一个重要的方法是无参数的init(）方法，如果我们必须在处理来自servlet的任何请
求之前初始化一些资源，我们应该在servlet程序中覆盖这个方法。

```java
public abstract class GenericServlet implements Servlet, ServletConfig{}
```