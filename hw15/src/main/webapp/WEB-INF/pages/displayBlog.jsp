<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<title>${entry_title}</title>

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
	<h2>${entry_title}</h2>
	<p>${entry_text}</p>
	<hr>
	<c:if test="${logged}">
		<p>
			<a
				href="${pageContext.request.contextPath}/servleti/author/${author_name}/edit?id=${enrty_id}">
				Edit blog entry</a>
		</p>
	</c:if>
	<c:forEach var="comm" items="${comments}">
		<p>${comm.usersEMail}
			<br> ${comm.message} <br> ${comm.postedOn}
		</p>
		<hr>
	</c:forEach>
	<br>
	<form
		action="${pageContext.request.contextPath}/servleti/author/${author_name}/comment"
		method="post">
		<c:if
			test="${not inputedEmail}">Input your email or some ID<br><input
				type="text" name="e-mail" required>
			<br>
		</c:if>
		Comment <br>
		<textarea rows="10" cols="30" name="comment" required></textarea>
		<input type="submit" value="Comment">
	</form>
	<a href="${pageContext.request.contextPath}/servleti/author/${author_name}">Back</a>
</body>
</html>