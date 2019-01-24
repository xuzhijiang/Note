<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>getProperty and setProperty</title>
</head>
<body>
    <jsp:useBean id="employee" class="cn.edu.bit.cs.model.Employee"/>
    <%--setProperty动作可对一个Java对象设置属性--%>
    <jsp:setProperty name="employee" property="firstName" value="Abigail"/>
    <%--getProperty则会输出Java对象的一个属性--%>
    First Name: <jsp:getProperty name="employee" property="firstName"/>
</body>
</html>

