<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>
	<body>
		<c:if test="${logoutMessage != null}">
			<c:out value="${logoutMessage}"></c:out>
		</c:if>
		<h1>Login</h1>
		<c:if test="${errorMessage != null}">
			<c:out value="${errorMessage}"></c:out>
		</c:if>
		<form method="post" action="/">
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
		<h1>Register</h1>
	    <form:form method="post" action="/register" modelAttribute="user">
		    <table>
		    	<tbody>
		    		<tr>
		    			<td><form:label path="firstName">First Name <form:errors path="firstName"/></form:label></td>
		    		</tr>
		    		<tr>
		    			<td><form:input path="firstName" class="full"/></td>
		    		</tr>
		    		<tr>
		    			<td><form:label path="lastName">Last Name <form:errors path="lastName"/></form:label></td>
		    		</tr>
		    		<tr>
		    			<td><form:input path="lastName" class="full"/></td>
		    		</tr>
		    		<tr>
		    			<td><form:label path="username">Email <form:errors path="username"/></form:label></td>
		    		</tr>
		    		<tr>
		    			<td><form:input path="username" class="full"/></td>
		    		</tr>
		    		<tr>
		    			<td><form:label path="password">Password <form:errors path="password"/></form:label></td>
		    		</tr>
		    		<tr>
		    			<td><form:password path="password" class="full"/></td>
		    		</tr>
		    		<tr>
		    			<td><form:label path="passwordConfirm">Confirm Password <form:errors path="passwordConfirm"/></form:label></td>
		    		</tr>
		    		<tr>
		    			<td><form:password path="passwordConfirm" class="full"/></td>
		    		</tr>
		    		<tr>
		    			<td><input type="submit" value="Register"/></td>
		    		</tr>
		    	</tbody>
		    </table>
	    </form:form>
	</body>
</html>