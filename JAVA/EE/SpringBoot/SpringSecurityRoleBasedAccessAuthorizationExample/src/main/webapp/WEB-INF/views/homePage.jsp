<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--  
	我们在顶部框架添加了三个类似菜单的选项

	新的两个链接是：

	JD User: Accessible by both “USER” and “ADMIN” Roles
	JD Admin: Accessible only by both “ADMIN” Roles
	
	注意： - 在实时应用程序中，我们将仅显示链接到“USER”角色的“JD User”，并隐藏“JD Admin”链接。 
	要测试它是否可以被“USER”角色访问，并且还要查看确切的错误消息，我们还没有隐藏此链接。
-->
<a href="${pageContext.request.contextPath}/userPage">JD User</a> | <a href="${pageContext.request.contextPath}/adminPage">JD Admin</a> | <a href="javascript:document.getElementById('logout').submit()">Logout</a>

<h3>Welcome to JournalDEV Tutorials</h3>
<ul>
   <li>Java 8 tutorial</li>
   <li>Spring tutorial</li>
   <li>Gradle tutorial</li>
   <li>BigData tutorial</li>
</ul>

<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>