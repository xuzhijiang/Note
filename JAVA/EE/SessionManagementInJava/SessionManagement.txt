Java Servlet Web应用程序中的(Session Management)会话管理是一个非常有趣的主题。
Java Servlet中的会话(Session)通过不同的方式进行管理，例如Cookies，HttpSession API，URL重写等。

What is a Session?

HTTP协议和Web服务器是无状态的，这意味着对于Web服务器而言，每个请求都是一个新的要去处理的请求，并且无法识别它是否来自之前已发送请求的客户端。

但有时在Web应用程序中，我们应该知道客户端是谁并相应地处理请求。 例如，购物车应用程序应该知道谁正在发送添加项目的请求以及在哪个购物车中添加项目或者谁正在发送结账请求，以便它可以向正确的客户收取金额。

由于HTTP和Web Server都是无状态的，因此维持会话的唯一方法是：在服务器和客户端之间的每个请求和响应中传递关于当前session（session ID）的唯一信息。

我们可以通过以下方式在请求和响应中提供唯一标识符(unique identifier)：

URL Rewriting: 我们可以为每个请求和响应附加会话标识符参数，以跟踪会话。这非常繁琐，因为我们需要在每个响应中跟踪此参数，并确保它不与其他参数冲突。

Cookie: Cookie是Web server在response header中发送并存储在浏览器Cookie中的一小段信息。当客户端做进一步请求时，它会将cookie添加到request header中，我们可以利用它来跟踪会话。我们可以使用cookie维持会话，但如果客户端禁用cookie，则它将无效。

Session Management API: 会话管理API建立在上述会话跟踪方法之上。 上述所有方法的一些主要缺点是：
1. 大多数情况下，我们不仅希望跟踪会话，我们必须将一些数据存储到会话中,以便我们可以在将来的请求中使用。如果我们尝试实现这一点，这将需要很多努力。

Session Management in Java – Cookies

Cookie会在Web应用程序中大量使用，以基于您的选择或跟踪会话，
以提供个性化响应。

在继续使用Servlet Session Management API之前，我想通过一个小型Web应用程序展示：如何通过cookie跟踪会话，。
I would like to show how can we keep track of session with cookies through a small web application.

Session in Java Servlet – HttpSession

Servlet API通过HttpSession接口提供Session management. 
我们可以使用以下方法从HttpServletRequest对象获取session。
HttpSession允许我们将对象设置为可在将来的请求中检索的属性。

HttpSession getSession（） - 此方法始终返回HttpSession对象。它返回随请求附加的会话对象，如果请求没有附加会话，则它创建一个新会话并返回它。

HttpSession getSession（boolean flag） - 如果请求有session，则此方法返回HttpSession对象，否则返回null。

HttpSession的一些重要方法是：

    1. String getId（） - 返回包含分配给此会话的唯一标识符的字符串。

    2. Object getAttribute（String name） - 返回在此会话中使用指定名称绑定的对象，如果名称下没有绑定任何对象，则返回null。使用Session属性的其他一些方法是getAttributeNames（），removeAttribute（String name）和setAttribute（String name，Object value）。

    3. long getCreationTime（） - 返回创建此会话的时间，以格林威治标准时间1970年1月1日午夜以来的毫秒数为单位。我们可以使用getLastAccessedTime（）方法获取上次访问的时间。

    4. setMaxInactiveInterval（int interval） - 指定servlet容器使此会话失效之前的客户端请求之间的时间（以秒为单位）。我们可以从getMaxInactiveInterval（）方法获取会话超时值。

    5. ServletContext getServletContext（） - 返回应用程序的ServletContext对象。

    6. boolean isNew（） - 如果客户端还不知道会话或客户端选择不加入会话，则返回true。

    7. void invalidate（） - 使此会话无效，然后取消绑定绑定到它的任何对象。

Session Management in Java Servlet – URL Rewriting

正如我们在上一节中看到的那样，我们可以使用HttpSession管理会话，但是如果我们在浏览器中禁用cookie，它将无法工作，因为服务器不会从客户端接收JSESSIONID的cookie。 Servlet API为URL重写提供支持，在这种情况下我们可以使用URL重写来管理会话。

最好的部分是从编码的角度来看，它非常容易使用并涉及一步 - 编码URL。 Servlet URL编码的另一个好处是它是一种后备方法，只有在禁用浏览器cookie时它才能启动。
 Another good thing with Servlet URL Encoding is that it’s a fallback approach and it kicks in only if browser cookies are disabled.

我们将创建一个类似于上面的项目，除了我们将使用URL重写方法确保会话管理工作正常，即使在浏览器中禁用了cookie。

