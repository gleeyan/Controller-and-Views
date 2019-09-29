<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>
	<body>
		<div class="row">
			<div class="col-3"></div>
			<div class="col-6">
				<c:if test="${logoutMessage != null}">
			        <c:out value="${logoutMessage}"></c:out>
			    </c:if>
			    <h1>Login</h1>
			    <c:if test="${errorMessage != null}">
			        <c:out value="${errorMessage}"></c:out>
			    </c:if>
				<form method="post" action="/login">
					<table>
						<tbody>
							<tr>
								<td><label for="username">Email </label></td>
							</tr>
							<tr>
								<td><input type="text" id="username" name="username" class="full-w"/></td>
							</tr>
							<tr>
								<td><label for="password">Password </label></td>
							</tr>
							<tr>	
								<td><input type="password" id="password" name="password" class="full-w"/></td>
							</tr>
							<tr>
								<td><input type="submit" value="Login"/></td>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</tr>
						</tbody>
					</table>
				</form>
				<p><a href="/register">Not a user? Register here!</a>
			</div>
			<div class="col-3"></div>
		</div>
	</body>
</html>