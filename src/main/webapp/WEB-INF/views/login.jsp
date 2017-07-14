<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Login</title>


<link href="/static/css/bootstrap.min.css" rel="stylesheet"></link>
<link href="/static/css/commons.css" rel="stylesheet"></link>





<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<div class="container">

		<form:form method="POST" action="/login" class="form-signin">
			<h2 class="form-signin-heading">Login with your account</h2>

			<c:if test="${not empty error}">
				<div class="alert alert-danger">${error}</div>
			</c:if>
			<c:if test="${not empty message}">
				<div class="alert alert-info">${message}</div>
			</c:if>
			<div class="form-group">
				<input type="text" name="username" class="form-control" placeholder="Email">
			</div>

			<div class="form-group">
				<input type="password" name="password" class="form-control" placeholder="Password">
			</div>

			
			<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
		</form:form>

	</div>
	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
</body>
</html>