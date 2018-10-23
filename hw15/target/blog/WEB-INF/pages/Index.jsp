<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<title>Main page</title>

<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>
<body>
	<h1>
		<c:choose>
			<c:when
				test="<%=session.getAttribute(\"current.user.id\") instanceof Long%>">
				<%=session.getAttribute("current.user.fn")%>
				<%=session.getAttribute("current.user.ln")%>,
		<a href="${pageContext.request.contextPath}/servleti/logout">Log
					Out</a>
			</c:when>
			<c:otherwise>
		Anonymous 
		</c:otherwise>
		</c:choose>
	</h1>
	<c:if
		test="<%=!(session.getAttribute(\"current.user.id\") instanceof Long)%>">
		<br>
		<h2>Login</h2>
		<form action="${pageContext.request.contextPath}/servleti/main"
			method="post">
			<div>
				<div>
					<span class="formLabel">Nick</span><input type="text" name="nick"
						value="${nick}" size="30">
				</div>
				<c:if test="${invalid_username}">
					<div class="greska">
						<p>Invalid username</p>
					</div>
				</c:if>
			</div>
			<div>
				<div>
					<span class="formLabel">Password</span><input type="password"
						name="password" value="" size="30">
				</div>
				<c:if test="${invalid_password}">
					<div class="greska">
						<p>Invalid password.</p>
					</div>
				</c:if>
			</div>
			<c:if test="${non_exsisting_user}">
				<div class="greska">
					<p>User under this nick doesn't exist.</p>
				</div>
			</c:if>

			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="metoda" value="Login">
			</div>
		</form>
	</c:if>
	<br>
	<p>
		<c:choose>
			<c:when
				test="<%=session.getAttribute(\"current.user.id\") instanceof Long%>">
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/servleti/register">Register</a>
			</c:otherwise>
		</c:choose>
	</p>
	<p>List of registered authors</p>
	<table>
		<c:if test="${users_set}">
			<tr>
				<td>Nick</td>
				<td>First name</td>
				<td>Last name</td>
			</tr>
		</c:if>

		<c:forEach var="user" items="${users}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/servleti/author/${user.nick}">${user.nick}</a></td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>