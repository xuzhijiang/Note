<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page session="false" %>
<html>
<head>
    <title><spring:message code="label.title"/></title>
</head>
<body>
<%--Spring负责加载适当的资源包消息，并使其可供JSP页面使用。--%>
<form method="post" action="login">
    <table>
        <tr>
            <td><label><strong><spring:message code="label.firstName"/></strong></label></td>
            <td><input name="firstName"/></td>
        </tr>
        <tr>
            <td><label> <strong><spring:message code="label.lastName"/></strong></label></td>
            <td><input name="lastName"/></td>
        </tr>
        <tr>
            <spring:message code="label.submit" var="labelSubmit"></spring:message>
            <td colspan="2"><input type="submit" value="${labelSubmit}"/></td>
        </tr>
    </table>
    <!-- 切换语言 -->
	<p><a href="${pageContext.request.contextPath}/login?locale=en_US">English</a> &nbsp;&nbsp;  |  &nbsp;&nbsp; <a href="${pageContext.request.contextPath}/login?locale=zh_CN">中文</a></p>
</form>
</body>
</html>
