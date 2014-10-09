<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>User Data</title>
	</head>
	<body>
		<h1><c:if test="${!empty title}">${title}</c:if></h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<form class="form-horizontal" role="form" method="POST" action="movies">
			<div class="form-group">
			    <label for="username" class="col-sm-2 control-label">Username</label>
			    <div class="col-sm-10">
			    	<c:choose>
			    		<c:when test="${logged}">
			    			<input type="text" class="form-control" name="username" id="username" placeholder="Username" disabled="disabled" value="${user.username}">
			    		</c:when>
			    		<c:otherwise>
			    			<input type="text" class="form-control" name="username" id="username" placeholder="Username">
			    		</c:otherwise>
			    	</c:choose>
			    </div>
		  	</div>
		  	<div class="form-group">
			    <label for="password" class="col-sm-2 control-label">Password</label>
			    <div class="col-sm-10">
			      	<input type="password" class="form-control" name="password" id="password" placeholder="Password">
			    </div>
		  	</div>
		  	<div class="form-group">
			    <label for="email" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-10">
			    	<c:choose>
			    		<c:when test="${logged}">
			    			<input type="email" class="form-control" name="email" id="email" placeholder="Email" value="${user.email }">
			    		</c:when>
			    		<c:otherwise>
				      		<input type="email" class="form-control" name="email" id="email" placeholder="Email">
				      	</c:otherwise>
			    	</c:choose>
			    </div>
		  	</div>
		  	<div class="form-group">
			    <label for="nickname" class="col-sm-2 control-label">Nickname</label>
			    <div class="col-sm-10">
			    	<c:choose>
			    		<c:when test="${not empty user.nickname}">
			    			<input type="text" class="form-control" name="nickname" id="nickname" placeholder="Nickname" value="${user.nickname}">
			    		</c:when>
			    		<c:otherwise>
			    			<input type="text" class="form-control" name="nickname" id="nickname" placeholder="Nickname">
			    		</c:otherwise>
			    	</c:choose>
			    </div>
		  	</div>
		  	<div class="form-group">
			    <label for="firstname" class="col-sm-2 control-label">First Name</label>
			    <div class="col-sm-10">
			    	<c:choose>
			    		<c:when test="${not empty user.firstName}">
			    			<input type="text" class="form-control" name="firstname" id="firstname" placeholder="First Name" value="${user.firstName}">
			    		</c:when>
			    		<c:otherwise>
			    			<input type="text" class="form-control" name="firstname" id="firstname" placeholder="First Name">
			    		</c:otherwise>
			    	</c:choose>
			    </div>
		  	</div>
		  	<div class="form-group">
			    <label for="lastname" class="col-sm-2 control-label">Last Name</label>
			    <div class="col-sm-10">
			    	<c:choose>
			    		<c:when test="${not empty user.firstName}">
			    			<input type="text" class="form-control" name="lastname" id="lastname" placeholder="Last Name" value="${user.lastName}">
			    		</c:when>
			    		<c:otherwise>
			    			<input type="text" class="form-control" name="lastname" id="lastname" placeholder="Last Name">
			    		</c:otherwise>
			    	</c:choose>
			    </div>
		  	</div>
			<input type="hidden" name="action" value="addUser">
			<c:choose>
	    		<c:when test="${logged}">
					<input type="hidden" name="action-method" value="update">
					<button type="submit" class="btn btn-default">Update</button>
				</c:when>
			    <c:otherwise>
			    	<input type="hidden" name="action-method" value="save">
			    	<button type="submit" class="btn btn-default">Create</button>
		    	</c:otherwise>
	    	</c:choose>
		</form>
		<%@include file="javascript.jsp" %>
	</body>
</html>