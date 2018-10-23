<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<title>Blog list</title>

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
		<a href="${pageContext.request.contextPath}/servleti/logout">Log Out</a>
			</c:when>
			<c:otherwise>
		Anonymous 
		</c:otherwise>
		</c:choose>
	</h1>
	<h2>List of blog entries from ${author_name} </h2>
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
	<c:if test="${noEntry}">
			<p>No content from this author</p>
		</c:if>
		<c:forEach var="e" items="${entries}">
			<p><a href="${pageContext.request.contextPath}/servleti/author/${author_name}/${e.id}">${e.title}</a></p>
		</c:forEach>
		<br><br>
	<c:if test="${logged}">
		<p><a href="${pageContext.request.contextPath}/servleti/author/${author_name}/new">Add new blog entry</a></p>
	</c:if>
	 <a href="${pageContext.request.contextPath}/servleti/main">Back</a>
</body>
</html>