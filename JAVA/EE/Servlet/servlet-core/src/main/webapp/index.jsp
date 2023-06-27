<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.Date"%>

<html>
<head>
  <title>Learn JSP & Servlet</title>
</head>
<body style="text-align:center">
  <h3>时间: <%= new Date()%>></h3>
  <%
    String ctx = request.getContextPath();
  %>
  <div>
    <a href="<%=ctx%>/httpServletRequestMethodTest">request方法测试</a>
  </div>
  <div>
    <a href="<%=ctx%>/responseMethodTest">response方法测试</a>
  </div>
  <div>
    <a href="<%=ctx%>/primitive-servlet-url">直接实现Servlet接口的示例</a>
  </div>
  <div>
    <a href="<%=ctx%>/generic">继承GenericServlet的示例</a>
  </div>
</body>
</html>