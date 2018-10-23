<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<h1>Vote results</h1>
	<p>This are the results of voting</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>${pick}</th>
				<th>Votes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="zapis" items="${voteResults}">
				<tr>
					<td>${zapis.optionTitle}</td>
					<td>${zapis.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Pie chart display of results</h2>
	<img alt="Pie-chart" src="glasanje-grafika?id=<%= request.getParameter("id") %>" width="400" height="400" />

	<h2>Results in XLS format</h2>
	<p>
		Results in XLS format available<a href="glasanje-xls?id=<%= request.getParameter("id") %>">here</a>
	</p>

	<h2>Other</h2>
	<p>Poll winners</p>
	<ul>
		<c:forEach var="zapis" items="${maxVotes}">
			<li><a href="${zapis.optionLink}"
				target="_blank">${zapis.optionTitle}</a></li>
		</c:forEach>

	</ul>
	
	<a href="index.html">Back home</a>
</body>
</html>