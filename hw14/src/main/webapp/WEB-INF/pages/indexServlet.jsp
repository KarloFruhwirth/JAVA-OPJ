<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<body bgcolor="white">
	<h1>Here is a list of all of the currently active polls </h1>
	<ul>
	<c:forEach var="zapis" items="${polls}">
	<li>${zapis.title}<a href="glasanje?pollID=${zapis.id}">VOTE</a></li>
	</c:forEach>
	</ul>
</body>
</html>