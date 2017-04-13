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

<title>Create an account</title>


<link href="static/css/bootstrap.min.css" rel="stylesheet"></link>
<link href="static/css/commons.css" rel="stylesheet"></link>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">

		<form:form method="POST" modelAttribute="user" class="form-signin">
			<h2 class="form-signin-heading">Create your account</h2>

			<div class="form-group">
				<form:input type="text" path="email" class="form-control"
					placeholder="Email"></form:input>
				<div class="has-error">
					<form:errors path="email" />
				</div>
			</div>

			<div class="form-group">
				<form:input type="password" path="password" class="form-control"
					placeholder="Password"></form:input>
				<div class="has-error">
					<form:errors path="password" />
				</div>
			</div>

			<div class="form-group">
				<form:input type="password" path="passwordConfirm"
					class="form-control" placeholder="Confirm your password"></form:input>
				<div class="has-error">
					<form:errors path="passwordConfirm" />
				</div>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
		</form:form>

	</div>
	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
</body>
</html>