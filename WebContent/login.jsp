<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>Login</title>
	</head>
	<body>
		<h1>Movie ${movie.title}</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<form class="form-horizontal" role="form" method="post" action="movies">
		  	<div class="form-group">
		    	<label for="username" class="col-sm-2 control-label">Username</label>
		    	<div class="col-sm-10">
		      		<input type="text" class="form-control" name="username" id="username" placeholder="Username">
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="password" class="col-sm-2 control-label">Password</label>
		    	<div class="col-sm-10">
		      		<input type="password" class="form-control" name="password" id="password" placeholder="Password">
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<div class="col-sm-offset-2 col-sm-10">
		      		<button type="submit" class="btn btn-default">Sign in</button>
		    	</div>
		  	</div>
		  	<input type="hidden" name="action" value="login">
		</form>
		<%@include file="javascript.jsp" %>
	</body>
</html>