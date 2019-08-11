
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Country List</title>
</head>
<body style="text-align: center">
We operate in these countries:
<ul>
    <%--在ServletContextListener中初始化了countries集合，从而使得项目中的所有网页都能
    从中提取数据--%>
    <c:forEach items="${countries}" var="country">
        <li>${country.value}</li>
    </c:forEach>
</ul>
</body>
</html>
