<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/static/css/bootstrap.min.css" rel="stylesheet"></link>
<link href="/static/css/commons.css" rel="stylesheet"></link>
<title>Change user roles</title>
</head>
<body>



	<div class="container">

		<div class="col-md-4"></div>
		<div class="col-md-4">
		<span class="lead">${user.email}</span>

		<form:form method="POST" modelAttribute="user">
			<div class="form-group">
				<form:select path="userRoles">
					<form:options items="${roles}" itemValue="id" itemLabel="name" ></form:options>
				</form:select>
				<div class="has-error">
					<form:errors path="userRoles" />
				</div>
			</div>
			<button class="btn btn-lg btn-primary" type="submit">Save</button>
		</form:form>
		</div>
		<div class="col-md-4"></div>

	</div>

</body>
</html>