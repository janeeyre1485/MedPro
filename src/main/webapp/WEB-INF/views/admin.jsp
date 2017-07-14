<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User administration</title>
<link href="/static/css/bootstrap.min.css" rel="stylesheet"></link>
<link href="/static/css/commons.css" rel="stylesheet"></link>
</head>
<body>
	<div class="container">

		<span class="lead">List of Users </span>
		<table class="table table-hover table-condensed">
			<thead>
				<tr>
					<th>Email</th>
					<th width="100">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.email}</td>
						<td><a href="<c:url value='/admin/users/${user.id}/edit-roles' />"
							class="btn btn-success custom-width">Change roles</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


</body>
</html>