<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<title>Register</title>

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
		Anonymous
	</h1>
	<c:if
		test="<%=!(session.getAttribute(\"current.user.id\") instanceof Long)%>">
		<br>
		<h2>Registration form</h2>
		<form action="${pageContext.request.contextPath}/servleti/register" method="post">
			<div>
				<div>
					<span class="formLabel">First name</span><input type="text" name="fn"value = "${zapis.firstName}" size="30">
				</div>
				<c:if
					test="${firstNameError}">
					<div class="greska">
						<c:out value="Provide first name" />
					</div>
				</c:if>
			</div>
			<div>
				<div>
					<span class="formLabel">Last name</span><input type="text" name="ln"value = "${zapis.lastName}" size="30">
					<c:if
					test="${lastNameError==true}">
					<div class="greska">
						<c:out value="Provide last name" />
					</div>
				</c:if>
				</div>
			</div>
			<div>
				<div>
					<span class="formLabel">Email</span><input type="text" name="email"value = "${zapis.email}" size="30">
				</div>
				<c:if
					test="${emailError==true}">
					<div class="greska">
						<c:out value="Provide valid email" />
					</div>
				</c:if>
			</div>
			<div>
				<div>
					<span class="formLabel">Nick</span><input type="text" name="nick"value = "${zapis.nick}" size="30">
				</div>
				<c:if
					test="${registration_nick==true}">
					<div class="greska">
						<c:out value="Provide nick" />
					</div>
				</c:if>
				<c:if
					test="${registration_nick_exsist==true}">
					<div class="greska">
						<c:out value="Nick exsists..." />
					</div>
				</c:if>
			</div>
			<div>
				<div>
					<span class="formLabel">Password</span><input type="password" name="password"value = "${zapis.passwordHash}" size="30">
				</div>
				<c:if
					test="${registration_password==true}">
					<div class="greska">
						<c:out value="Provide password" />
					</div>
				</c:if>
				<c:if
					test="${registration_password_len==true}">
					<div class="greska">
						<c:out value="Provide a stronger password" />
					</div>
				</c:if>
			</div>
			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="metoda" value="Register">
					<input type="submit"
					name="metoda" value="Cancle">
			</div>
		</form>
	</c:if>
	
</body>
</html>