<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
</head>
<body>
	<!--  
		Spring Security会处理CSRF攻击，因此当我们提交注销表单时，我们会将CSRF令牌发送回服务器以将其删除。 

		Spring Security组件设置的CSRF对象是_csrf，我们使用它的属性名称和token value在注销请求中传递。
	-->
<h3>Home Page</h3>

	<p>
      Hello <b><c:out value="${pageContext.request.remoteUser}"/></b><br>
      Roles: <b><sec:authentication property="principal.authorities" /></b>
    </p>
    
    <form action="logout" method="post">
      <input type="submit" value="Logout" />
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</body>
</html>