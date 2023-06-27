<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>id</th>
                <th>lastName</th>
                <th>email</th>
                <th>gender</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${allUsers}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.gender}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
