<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>Search for Movies</title>
	</head>
	<body>
		<h1>Search Movies</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<form class="form-inline" role="form" method="POST" action="movies">
			<div class="form-group">
			    <label class="sr-only" for="q">Search</label>
			    <input type="text" class="form-control" name="q" id="q" placeholder="Search">
		  	</div>
		  	<c:if test="${logged}">
		  		<label class="sr-only" for="genre">Genre</label>
		  		<select class="form-control" name="genre" id="genre">
		  			<option value="0">Genre</option>
		    		<c:forEach items="${genres}" var="genre" varStatus="loopCC">
				  		<option value="${genre.id}">${genre.genre}</option>
				  	</c:forEach>
				</select>
		  	</c:if>
		  	<input type="hidden" name="action" value="searchMovie">
		  	<button type="submit" class="btn btn-default">Search</button>
		</form>
		<%@include file="javascript.jsp" %>
	</body>
</html>