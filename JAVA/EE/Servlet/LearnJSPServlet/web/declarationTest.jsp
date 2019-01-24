<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    public String getTodaysDate() {
        return new java.util.Date().toString();
    }
%>
<!--  
	可以声明能在JSP页面中使用的变量和方法。 声明以“<%!”开始，
	并以“%>”结束。
-->
<html>
<head>
    <title>Declarations</title>
</head>
<body>
    Today is <%=getTodaysDate()%>
</body>
</html>
