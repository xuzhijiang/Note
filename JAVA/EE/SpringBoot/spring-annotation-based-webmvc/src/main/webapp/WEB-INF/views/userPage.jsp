<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--  
	添加新的userPage.jsp文件以充当“USER”角色的主页。
-->
<h3>Welcome to JournalDEV Tutorials</h3>
<h3>User Page</h3>

<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<a href="javascript:document.getElementById('logout').submit()">Logout</a>
</c:if>