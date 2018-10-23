<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<body>
	<H1>Oops.... Something went wrong...</H1>
	<br>
	<%=session.getAttribute("error")%>
	<br>
	<a href="${pageContext.request.contextPath}/index.jsp">Back home</a>
</body>
</html>