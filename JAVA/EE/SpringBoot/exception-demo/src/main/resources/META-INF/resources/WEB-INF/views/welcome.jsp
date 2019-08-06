<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<%--一个典型的JSP视图文件，其中用到
了标准标签库中的foreach标签--%>
<h1>Welcome to JSP in spring Boot!</h1>
<h2>当前时间：${now}</h2>

<c:forEach var="i" begin="1" end="5">
    <h${i}>h${i}：标题${i}</h>
</c:forEach>


