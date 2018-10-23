<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<title>Add/edit blog</title>
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
	<br>
	<c:choose>
		<c:when test="${edit_entry}">
			<form
				action="${pageContext.request.contextPath}/servleti/author/${author_name}/edit"
				method="post">
				Blog title <input type="text" name="title" value="${entry_title}"
					required size="30"> <br> Text <br>
				<textarea rows="30" cols="40" name="text" required>${entry_text}</textarea>
				<br> <input type="submit" value="Edit"> <a
					href="${pageContext.request.contextPath}/servleti/author/${author_name}">Back</a>
			</form>
		</c:when>
		<c:otherwise>
			<form
				action="${pageContext.request.contextPath}/servleti/author/${author_name}/new"
				method="post">
				Blog title <input type="text" name="title" required size="30">
				<br> Text <br>
				<textarea rows="30" cols="40" name="text" required></textarea>
				<br> <input type="submit" value="Create"> <a
					href="${pageContext.request.contextPath}/servleti/author/${author_name}">Back</a>
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>
