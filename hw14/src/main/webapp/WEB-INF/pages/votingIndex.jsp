<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<body>
	<h1>${pollEntry.title}</h1>
	<p>${pollEntry.message}</p>
	<ul>
	<c:forEach var="zapis" items="${pollOptions}">
	<li><a href="glasanje-glasaj?id=${zapis.id}">${zapis.optionTitle}</a></li>
	</c:forEach>
	</ul>
	<br>
	<a href="index.html">Back</a>
</body>
</html>